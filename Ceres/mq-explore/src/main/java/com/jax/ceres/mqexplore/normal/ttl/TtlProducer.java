package com.jax.ceres.mqexplore.normal.ttl;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.core.ExchangeTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class TtlProducer {
  private static final String EXCHANGE_NAME = "ttl_exchange";
  private static final String ROUTING_KEY = "ttl_routing_key";
  private static final String QUEUE_NAME = "ttl_queue";

  public static void main(String[] args) throws Exception {
    Connection connection = ConnectionBuilder.getConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT, true, false, null);
    createTTLMsg(channel);
    publishTTLMsg(channel);
    createTTLQueue(channel);
    channel.close();
    connection.close();
  }

  public static void createTTLMsg(Channel channel) throws Exception {
    String queue = QUEUE_NAME + 1;
    String routingKey = ROUTING_KEY + 1;
    Map<String, Object> args = new HashMap<>();
    args.put("x-message-ttl", 6000);
    channel.queueDeclare(queue, true, false, false, args);
    channel.queueBind(queue, EXCHANGE_NAME, routingKey);
    channel.basicPublish(EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "ttl-msg-1".getBytes());
    Thread.sleep(3000);
    channel.basicPublish(EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "ttl-msg-2".getBytes());
  }

  public static void publishTTLMsg(Channel channel) throws Exception {
    String queue = QUEUE_NAME + 2;
    String routingKey = ROUTING_KEY + 2;
    channel.queueDeclare(queue, true, false, false, null);
    channel.queueBind(queue, EXCHANGE_NAME, routingKey);
    AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("60000").build();
    channel.basicPublish(EXCHANGE_NAME, routingKey, properties, "ttl-msg-3".getBytes());
    Thread.sleep(3000);
    channel.basicPublish(EXCHANGE_NAME, routingKey, properties, "ttl-msg-4".getBytes());
  }

  public static void createTTLQueue(Channel channel) throws Exception {
    String queue = QUEUE_NAME + 3;
    String routingKey = ROUTING_KEY + 3;
    Map<String, Object> args = new HashMap<>();
    args.put("x-expires", 60000);
    channel.queueDeclare(queue, false, false, false, args);
    channel.queueBind(queue, EXCHANGE_NAME, routingKey);
    channel.basicPublish(EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "ttl-msg-5".getBytes());
  }
}
