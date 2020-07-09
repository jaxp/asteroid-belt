package com.jax.ceres.mqexplore.normal.basic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class RabbitConsumer {
  private static final String QUEUE_NAME = "queue_demo";
  private static final String IP_ADDRESS = "10.106.0.185";
  private static final int PORT = 5671;

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    Address[] addresses = new Address[]{
        new Address(IP_ADDRESS, PORT)
    };
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("root");
    factory.setPassword("root");
    Connection connection = factory.newConnection(addresses);
    final Channel channel = connection.createChannel();
    // 客户端最多接收未被ack的消息个数
    channel.basicQos(64);
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("recv msg:" + new String(body));
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        channel.basicAck(envelope.getDeliveryTag(), false);
      }
    };
    channel.basicConsume(QUEUE_NAME, consumer);
    TimeUnit.SECONDS.sleep(5);
    channel.close();
    connection.close();
  }
}
