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
 *              ---
 * RabbitMQ tutorial:
 * https://www.rabbitmq.com/tutorials/tutorial-one-java
 */

package org.taba.question4.Subscribers;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * This Subscriber listens for messages:
 * 4.b) strictly messages related to a floor/room/temperature
 */
public class SubscriberStrictlyTempRoom
{
    /** RabbitMQ tutorial: https://www.rabbitmq.com/tutorials/tutorial-one-java */

    /**
     * Setting up is the same as the publisher;
     * we open a connection and a channel,
     * and declare the queue from which we're going to consume.
     * Note this matches up with the queue that send publishes to.
     */

    //From Publisher One Temp
    private static final String QUEUE_AIR_TEMP_KITCHEN = "FirstFloor/Kitchen/Temperature";
    private static final String QUEUE_AIR_TEMP_ROOM = "SecondFloor/Room/Temperature";
    private static final String QUEUE_AIR_TEMP_OFFICE = "SecondFloor/Office/Temperature";
    private static final String QUEUE_AIR_TEMP_LOUNGE = "FirstFloor/Lounge/Temperature";

    //From Publisher One Humidity
    private static final String QUEUE_AIR_HUMIDITY_KITCHEN = "FirstFloor/Kitchen/Humidity";
    private static final String QUEUE_AIR_HUMIDITY_ROOM = "SecondFloor/Room/Humidity";
    private static final String QUEUE_AIR_HUMIDITY_OFFICE = "SecondFloor/Office/Humidity";
    private static final String QUEUE_AIR_HUMIDITY_LOUNGE = "FirstFloor/Lounge/Humidity";

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

        //Testing... Temperature
        //channel.queueDeclare(QUEUE_AIR_TEMP_KITCHEN, false, false, false, null);              //USED TO TEST
        channel.queueDeclare(QUEUE_AIR_TEMP_ROOM, false, false, false, null);   //Strictly messages related to a floor/room/temperature
        //channel.queueDeclare(QUEUE_AIR_TEMP_OFFICE, false, false, false, null);               //USED TO TEST
        //channel.queueDeclare(QUEUE_AIR_TEMP_LOUNGE, false, false, false, null);               //USED TO TEST

        //Testing... Humidity
        //channel.queueDeclare(QUEUE_AIR_HUMIDITY_KITCHEN, false, false, false, null);          //USED TO TEST
        //channel.queueDeclare(QUEUE_AIR_HUMIDITY_ROOM, false, false, false, null);             //USED TO TEST
        //channel.queueDeclare(QUEUE_AIR_HUMIDITY_OFFICE, false, false, false, null);           //USED TO TEST
        //channel.queueDeclare(QUEUE_AIR_HUMIDITY_LOUNGE, false, false, false, null);           //USED TO TEST

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

        //Start consuming messages from the queue (Publisher One Temp)
        //channel.basicConsume(QUEUE_AIR_TEMP_KITCHEN, true, consumer);     //USED TO TEST
        channel.basicConsume(QUEUE_AIR_TEMP_ROOM, true, consumer);       //Strictly messages related to a floor/room/temperature
        //channel.basicConsume(QUEUE_AIR_TEMP_OFFICE, true, consumer);      //USED TO TEST
        //channel.basicConsume(QUEUE_AIR_TEMP_LOUNGE, true, consumer);      //USED TO TEST

        //Start consuming messages from the queue (Publisher One Humidity)
        //channel.basicConsume(QUEUE_AIR_HUMIDITY_KITCHEN, true, consumer); //USED TO TEST
        //channel.basicConsume(QUEUE_AIR_HUMIDITY_ROOM, true, consumer);    //USED TO TEST
        //channel.basicConsume(QUEUE_AIR_HUMIDITY_OFFICE, true, consumer);  //USED TO TEST
        //channel.basicConsume(QUEUE_AIR_HUMIDITY_LOUNGE, true, consumer);  //USED TO TEST

        //Wait indefinitely until notified to exit
        latch.await();
    }
}
