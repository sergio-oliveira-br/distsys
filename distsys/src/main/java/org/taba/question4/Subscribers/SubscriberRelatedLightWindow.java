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
 * 4.d) messages that are related to the ALL FLOORS for both light and window
 */
public class SubscriberRelatedLightWindow
{
    /** Instance Variables */
    //From Publisher 2, Lights:
    private static final String QUEUE_LIGHTS_KITCHEN = "1ºFloor/Lights:Kitchen/ID:1122";
    private static final String QUEUE_LIGHTS_ROOM = "2ºFloor/Light:Room/ID:2254";
    private static final String QUEUE_LIGHTS_OFFICE = "2ºFloor/Light:Office/ID:2260";
    private static final String QUEUE_LIGHTS_LOUNGE = "1ºFloor/Light:Lounge/ID:1197";

    //From Publisher 2, Window:
    private static final String QUEUE_WINDOW_KITCHEN = "1ºFloor/Window/Kitchen";
    private static final String QUEUE_WINDOW_ROOM = "2ºFloor/Window/Room";
    private static final String QUEUE_WINDOW_OFFICE = "2ºFloor/Window/Office";
    private static final String QUEUE_WINDOW_LOUNGE = "1ºFloor/Window/Lounge";


    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");   //RabbitMQ server host -> http://localhost:15672/#/

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /** Declare the Queues*/
        //From Publisher 2, Lights:
        channel.queueDeclare(QUEUE_LIGHTS_KITCHEN, true, false, false, null);
        channel.queueDeclare(QUEUE_LIGHTS_ROOM, true, false, false, null);
        channel.queueDeclare(QUEUE_LIGHTS_OFFICE, true, false, false, null);
        channel.queueDeclare(QUEUE_LIGHTS_LOUNGE, true, false, false, null);
        //b:true, is durable queue / b1:false, is not exclusive / b2:false, it won't be deleted automatically

        //From Publisher 2, Window:
        channel.queueDeclare(QUEUE_WINDOW_KITCHEN, false, false, false, null);
        channel.queueDeclare(QUEUE_WINDOW_ROOM, false, false, false, null);
        channel.queueDeclare(QUEUE_WINDOW_OFFICE, false, false, false, null);
        channel.queueDeclare(QUEUE_WINDOW_LOUNGE, false, false, false, null);

        System.out.println("--- \nTABA - Distributed System\nby Sergio Oliveira - x23170981@student.ncirl.ie\n---");

        System.out.println("This Subscriber listens for messages:\n" +
                "4.d) Any messages that are related to the ALL FLOORS for both light and window\n");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //Create a consumer and bind it to the queues
        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        /**Start consuming messages*/
        //From Publisher 2, Lights:
        channel.basicConsume(QUEUE_LIGHTS_KITCHEN, true, consumer);
        channel.basicConsume(QUEUE_LIGHTS_ROOM, true, consumer);
        channel.basicConsume(QUEUE_LIGHTS_OFFICE, true, consumer);
        channel.basicConsume(QUEUE_LIGHTS_LOUNGE, true, consumer);

        //From Publisher 2, Window:
        channel.basicConsume(QUEUE_WINDOW_KITCHEN, true, consumer);
        channel.basicConsume(QUEUE_WINDOW_ROOM, true, consumer);
        channel.basicConsume(QUEUE_WINDOW_OFFICE, true, consumer);
        channel.basicConsume(QUEUE_WINDOW_LOUNGE, true, consumer);

        /**Wait indefinitely until notified to exit */
        latch.await();
    }
}
