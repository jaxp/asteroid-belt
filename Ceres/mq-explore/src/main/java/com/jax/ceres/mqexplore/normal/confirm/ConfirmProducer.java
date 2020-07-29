package com.jax.ceres.mqexplore.normal.confirm;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class ConfirmProducer {

  private static final int LOOP_TIMES = 100;
  private static final int BATCH_COUNT = 10;
  private static final String EXCHANGE_NAME = "confirm_exchange";
  private static final String QUEUE_NAME = "confirm_queue";
  private static final String ROUTING_KEY = "routing_key";

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection = ConnectionBuilder.getConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT, true, false, null);
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
    txProduce(channel);
    confirmProduce(channel);
  }

  public static final void txProduce(Channel channel) throws IOException {
    channel.txSelect();
    for (int i = 0; i < LOOP_TIMES; i++) {
      try {
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, ("msg" + i).getBytes());
        channel.txCommit();
      } catch (Exception e) {
        channel.txRollback();
      }
    }
  }

  public static final void confirmProduce(Channel channel) throws IOException {
    channel.confirmSelect();
    for (int i = 0; i < LOOP_TIMES; i++) {
      try {
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, ("msg" + i).getBytes());
        if (!channel.waitForConfirms()) {
          System.out.println("send msg failed");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static final void confirmBatchProduce(Channel channel) throws IOException {
    channel.confirmSelect();
    int msgCount = 0;
    int totalCount = 0;
    List<String> msgs = new ArrayList<>();
    while(true) {
      String msg = "msg" + totalCount++;
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
      msgs.add(msg);
      if (++msgCount >= BATCH_COUNT) {
        msgCount = 0;
        try {
          if (channel.waitForConfirms()) {
            msgs.clear();
          }
          System.out.println("resend");
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println("resend");
        }
      }
    }
  }

}
