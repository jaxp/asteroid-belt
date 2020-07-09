package com.jax.ceres.mqexplore.normal.rpc;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class RPCServer {
  private static final String RPC_QUEUE_NAME = "rpc_queue";

  public static void main(String[] args) throws Exception{
    Connection connection = ConnectionBuilder.getConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(RPC_QUEUE_NAME, false, false , false, null);
    channel.basicQos(1);
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
            .correlationId(properties.getCorrelationId())
            .build();
        String response = "";
        try {
          String msg = new String(body, "UTF-8");
          int n = Integer.parseInt(msg);
          System.out.println(" [.] fib(" + msg + ")");
          response += fib(n);
        } catch (RuntimeException e) {
          System.out.println(e.toString());
        } finally {
          channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
          channel.basicAck(envelope.getDeliveryTag(), false);
        }
      }
    };
    channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
  }

  private static int fib(int n) {
    if (n == 0) {
      return 0;
    }
    if (n == 1) {
      return 1;
    }
    return fib(n - 1) + fib(n - 2);
  }
}
