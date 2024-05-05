/**
 * National College Ireland - NCI
 * Terminal Based Assignment Assessment - TABA
 * HD Computer Science - International
 *              ---
 *        Distributed System
 *      Lecturer: Mark Cudden
 *              ---
 * Author: Sergio Vinicio da Silva Oliveira
 * x23170981@student.ncirl.ie
 *              ---
 * Question 4:By using the MQTT protocol
 * implement in Java the Publisher - Subscriber
 *
 * RabbitMQ tutorial:
 *  *  https://www.rabbitmq.com/tutorials/tutorial-one-java
 */

package org.taba.question4;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * This Subscriber listens for messages:
 * 4.b) strictly messages related to a floor/room/temperature
 */
public class SubscriberStrictly
{
    /** RabbitMQ tutorial: https://www.rabbitmq.com/tutorials/tutorial-one-java */

    /**
     * Setting up is the same as the publisher;
     * we open a connection and a channel,
     * and declare the queue from which we're going to consume.
     * Note this matches up with the queue that send publishes to.
     */
    private final static String AIR_TEMPERATURE = "firstFloor/kitchen/temperature";
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");   //RabbitMQ server host -> http://localhost:15672/#/
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /**
         * Note that we declare the queue here, as well.
         * Because we might start the consumer before the publisher,
         * we want to make sure the queue exists before we try to consume messages from it.
         */

        channel.queueDeclare(AIR_TEMPERATURE, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //Create a consumer and bind it to the queue
        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        //Start consuming messages from the queue
        channel.basicConsume(AIR_TEMPERATURE, true, consumer);

        //Wait indefinitely until notified to exit
        latch.await();
    }
}
