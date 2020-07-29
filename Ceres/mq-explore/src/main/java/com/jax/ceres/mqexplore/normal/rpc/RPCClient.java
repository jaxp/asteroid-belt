package com.jax.ceres.mqexplore.normal.rpc;

import com.jax.ceres.mqexplore.builder.ConnectionBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @Author Jax
 * @Date 2020/6/15 16:41
 * @Version 1.0
 */
public class RPCClient {
  private Connection connection;
  private Channel channel;
  private String requestQueueName = "rpc_queue";
  private String replyQueueName;

  public RPCClient() throws IOException, TimeoutException {
    connection = ConnectionBuilder.getConnection();
    channel = connection.createChannel();
    replyQueueName = channel.queueDeclare().getQueue();
  }

  public String call(String msg) throws IOException, InterruptedException {
    String corrId = UUID.randomUUID().toString();
    AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
        .correlationId(corrId)
        .replyTo(replyQueueName)
        .build();
    channel.basicPublish("", requestQueueName, props, msg.getBytes());
    final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
    channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        if (properties.getCorrelationId().equals(corrId)) {
          response.offer(new String(body));
        }
      }
    });
    return response.take();
  }

  public void close() throws IOException, TimeoutException {
    channel.close();
    connection.close();
  }

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    RPCClient rpcClient = new RPCClient();
    System.out.println("requesting fib(30)");
    String response = rpcClient.call("30");
    System.out.println("got " + response);
    rpcClient.close();
  }
}
