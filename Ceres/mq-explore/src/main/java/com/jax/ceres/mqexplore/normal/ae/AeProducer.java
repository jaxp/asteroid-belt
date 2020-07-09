package com.jax.ceres.mqexplore.normal.ae;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class AeProducer {
  private static final String EXCHANGE_NAME = "normalExchange";
  private static final String AE_EXCHANGE_NAME = "myAe";
  private static final String ROUTING_KEY = "normalKey";
  private static final String QUEUE_NAME = "normalQueue";
  private static final String UNROUTED_QUEUE_NAME = "unroutedQueue";

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection = ConnectionBuilder.getConnection();
    Channel channel = connection.createChannel();
    Map<String, Object> exchangeArgs = new HashMap<>();
    exchangeArgs.put("alternate-exchange", AE_EXCHANGE_NAME);
    channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, exchangeArgs);
    channel.exchangeDeclare(AE_EXCHANGE_NAME, "fanout", true, false, null);
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
    channel.queueDeclare(UNROUTED_QUEUE_NAME, true, false, false, null);
    channel.queueBind(UNROUTED_QUEUE_NAME, AE_EXCHANGE_NAME, "");
    String message = "Hello World!";
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    channel.basicPublish(EXCHANGE_NAME, "errorKey", MessageProperties.PERSISTENT_TEXT_PLAIN, "errorMsg".getBytes());
    channel.close();
    connection.close();
  }
}
