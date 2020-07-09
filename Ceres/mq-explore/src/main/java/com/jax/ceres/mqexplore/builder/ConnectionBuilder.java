package com.jax.ceres.mqexplore.builder;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class ConnectionBuilder {

  private static final String IP_ADDRESS = "10.106.0.185";
  private static final int PORT = 5671;

  public static final Connection getConnection() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(IP_ADDRESS);
    factory.setPort(PORT);
    factory.setUsername("root");
    factory.setPassword("root");
    Connection connection = factory.newConnection();
    return connection;
  }
}
