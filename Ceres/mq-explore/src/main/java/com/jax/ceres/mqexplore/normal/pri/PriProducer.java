package com.jax.ceres.mqexplore.normal.pri;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.core.ExchangeTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class PriProducer {

  private static final String EXCHANGE_NAME = "pri_exchange";
  private static final String ROUTING_KEY = "pri_routing_key";
  private static final String QUEUE_NAME = "pri_queue";

  public static void main(String[] args) throws Exception {
    Connection connection = ConnectionBuilder.getConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT, true, false, null);
    Map<String, Object> params = new HashMap<>();
    params.put("x-max-priority", 10);
    channel.queueDeclare(QUEUE_NAME, true, false, false, params);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
        .priority(5)
        .build();
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, "message".getBytes());
    channel.close();
    connection.close();
  }

}
