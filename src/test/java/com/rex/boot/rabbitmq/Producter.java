package com.rex.boot.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class Producter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void product() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection("生成者");
            channel = connection.createChannel();
            String queueName = "rex";

            // 队列名称，是否持久化，排他性（是否独占独立），是否自动删除（最后一个消息消费完后，是否删除队列），携带附属参数
            channel.queueDeclare(queueName, false, false, false, null);
            String message = "Hello World";

            // 交换机，队列 路由key, 消息的状态控制，消息主题
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println("消息发送成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    void productSimple() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection("生成者");
            channel = connection.createChannel();
            String queueName = "rex";

            // 队列名称，是否持久化，排他性（是否独占独立），是否自动删除（最后一个消息消费完后，是否删除队列），携带附属参数
            channel.queueDeclare(queueName, false, false, false, null);
            String message = "Hello World from java";

            String exchangeName = "topic-exchange";
            String routeKey = "com.rex";
            String type = "fanout";

            // 交换机，队列 路由key, 消息的状态控制，消息主题
            channel.basicPublish(exchangeName, routeKey, null, message.getBytes());
            System.out.println("消息发送成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    void testBoot(){
        rabbitTemplate.convertAndSend("fanout-exchange","","hello world java aaa");


    }


}
