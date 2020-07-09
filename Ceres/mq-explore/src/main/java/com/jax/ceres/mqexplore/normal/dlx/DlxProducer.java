package com.jax.ceres.mqexplore.normal.dlx;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
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
public class DlxProducer {

  private static final String EXCHANGE_NAME = "dlx_exchange.normal";
  private static final String DLX_EXCHANGE_NAME = "dlx_exchange.dlx";
  private static final String ROUTING_KEY = "dlx_routing_key";
  private static final String QUEUE_NAME = "dlx_queue.normal";
  private static final String DLX_QUEUE_NAME = "dlx_queue.dlx";

  public static void main(String[] args) throws Exception {
    Connection connection = ConnectionBuilder.getConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT, true, false, null);
    channel.exchangeDeclare(DLX_EXCHANGE_NAME, ExchangeTypes.FANOUT, true, false, null);
    Map<String, Object> params = new HashMap<>();
    params.put("x-message-ttl", 10000);
    params.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
    params.put("x-dead-letter-routing-key", ROUTING_KEY);
    channel.queueDeclare(QUEUE_NAME, true, false, false, params);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "rk");
    channel.queueDeclare(DLX_QUEUE_NAME, true, false, false, null);
    channel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, ROUTING_KEY);
    channel.basicPublish(EXCHANGE_NAME, "rk", MessageProperties.PERSISTENT_TEXT_PLAIN, "dlx".getBytes());
    channel.close();
    connection.close();
  }

}
