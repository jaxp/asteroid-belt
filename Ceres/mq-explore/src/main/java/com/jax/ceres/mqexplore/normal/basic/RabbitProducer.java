package com.jax.ceres.mqexplore.normal.basic;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class RabbitProducer {

  private static final String EXCHANGE_NAME = "exchange_demo";
  private static final String ROUTING_KEY = "routingkey_demo";
  private static final String QUEUE_NAME = "queue_demo";

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection = ConnectionBuilder.getConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT, true, false, null);
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
    String message = "Hello World!";
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    channel.close();
    connection.close();
  }
}
