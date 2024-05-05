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

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.ArrayList;

public class PublisherOne
{
    //Set up the class and name the queue
    private static String AIR_TEMPERATURE = "firstFloor/kitchen/temperature";
    private static int temperature;

    /** RabbitMQ tutorial: https://www.rabbitmq.com/tutorials/tutorial-one-java */
    /** This will generate the temperature */
    public static void myTemperature()
    {
        /**
         * The connection abstracts the socket connection,
         * and takes care of protocol version negotiation
         * and authentication and so on for us.
         * Here we connect to a RabbitMQ node on the local machine - hence the localhost.
         * If we wanted to connect to a node on a different machine
         * we'd simply specify its hostname or IP address here.
         */

        //Create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //RabbitMQ server host -> http://localhost:15672/#/

        /**
         * Next we create a channel, which is where most of the API
         * for getting things done resides.
         * Note we can use a try-with-resources statement
         * because both Connection and Channel implement java.lang.AutoCloseable.
         * This way we don't need to close them explicitly in our code.
         */

        /**
         * To send, we must declare a queue for us to send to;
         * then we can publish a message to the queue,
         * all of this in the try-with-resources statement:
         */

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();)
        {
            channel.queueDeclare(AIR_TEMPERATURE, false, false, false, null);

            String message = "TESTING... Hello World ...TESTING";

            channel.basicPublish("", AIR_TEMPERATURE, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }
        catch (Exception e)
        {
            System.out.println("Oops! Something went wrong... =[");
        }

    }
}
