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
 */
package org.taba.question4.Subscribers;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * This Subscriber listens for messages:
 * 4.c) any messages that are related to room (including its subtopics)
 */
public class SubscriberAnyRelatedRoom
{
    /** Instance Variables */
    //From Publisher 1 (Temperature/Humidity)
    private static final String QUEUE_AIR_TEMP_ROOM = "SecondFloor/Room/Temperature";
    private static final String QUEUE_AIR_HUMIDITY_ROOM = "SecondFloor/Room/Humidity";

    //From Publisher 2 (Lights/Window):
    private static final String QUEUE_LIGHTS_ROOM = "2ºFloor/Light:Room/ID:2254";
    private static final String QUEUE_WINDOW_ROOM = "2ºFloor/Window/Room";

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");   //RabbitMQ server host -> http://localhost:15672/#/

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /** Declare the Queues*/
        //From Publisher 1 (Temperature/Humidity)
        channel.queueDeclare(QUEUE_AIR_TEMP_ROOM, false, false, false, null);
        channel.queueDeclare(QUEUE_AIR_HUMIDITY_ROOM, false, false, false, null);

        //From Publisher 2 (Lights/Window):
        channel.queueDeclare(QUEUE_LIGHTS_ROOM, true, false, false, null);
        channel.queueDeclare(QUEUE_WINDOW_ROOM, false, false, false, null);

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

        //Start consuming messages from the queues
        channel.basicConsume(QUEUE_AIR_TEMP_ROOM, true, consumer);
        channel.basicConsume(QUEUE_AIR_HUMIDITY_ROOM, true, consumer);
        channel.basicConsume(QUEUE_LIGHTS_ROOM, true, consumer);
        channel.basicConsume(QUEUE_WINDOW_ROOM, true, consumer);

        //Wait indefinitely until notified to exit
        latch.await();
    }
}
