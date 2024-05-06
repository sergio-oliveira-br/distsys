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

package org.taba.question4.Publisher1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.ArrayList;
import java.util.Random;


/** This is a client (program) that publishes messages every second about the room;
 *  like the floor/room/temperature (e.g 17ºC)
 */
public class PublisherOneTemp extends Thread
{
    //Set up the class and name the queue
    private static final String QUEUE_AIR_TEMP_KITCHEN = "FirstFloor/Kitchen/Temperature";
    private static final String QUEUE_AIR_TEMP_ROOM = "SecondFloor/Room/Temperature";
    private static final String QUEUE_AIR_TEMP_OFFICE = "SecondFloor/Office/Temperature";
    private static final String QUEUE_AIR_TEMP_LOUNGE = "FirstFloor/Lounge/Temperature"; // lounge

    // create instance of Random class
    static Random myRandom = new Random();

    /** RabbitMQ tutorial: https://www.rabbitmq.com/tutorials/tutorial-one-java */
    /** This will generate the temperature */
    public void run()
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
             Channel channel = connection.createChannel())
        {
            channel.queueDeclare(QUEUE_AIR_TEMP_KITCHEN, false, false, false, null);
            channel.queueDeclare(QUEUE_AIR_TEMP_ROOM, false, false, false, null);
            channel.queueDeclare(QUEUE_AIR_TEMP_OFFICE, false, false, false, null);
            channel.queueDeclare(QUEUE_AIR_TEMP_LOUNGE, false, false, false, null);

            //Publish every random number multiple times with a delay.
            for (int i = 0; i < 1000; i++)
            {
                //Generating a random num
                double tempKitchen = myRandom.nextDouble(0.6) + 19;
                double tempRoom = myRandom.nextDouble(0.7) + 18;
                double tempOffice = myRandom.nextDouble(0.8) + 17;
                double tempLounge = myRandom.nextDouble(0.9) + 18;

                //Format the temperature with two decimal places
                String myTempKitchen = String.format("%.2fºC", tempKitchen) + " Kitchen";
                String myTempRoom = String.format("%.2fºC", tempRoom) + " Room";
                String myTempOffice = String.format("%.2fºC", tempOffice) + " Office";
                String myTempLounge = String.format("%.2fºC", tempLounge) + " Lounge";

                //Sending...
                channel.basicPublish("", QUEUE_AIR_TEMP_KITCHEN, null, myTempKitchen.getBytes());
                System.out.println(" [x] -> Sent '" + QUEUE_AIR_TEMP_KITCHEN  + "'");

                channel.basicPublish("", QUEUE_AIR_TEMP_ROOM, null, myTempRoom.getBytes());
                System.out.println(" [x] -> Sent '" + QUEUE_AIR_TEMP_ROOM + " '");

                channel.basicPublish("", QUEUE_AIR_TEMP_OFFICE, null, myTempOffice.getBytes());
                System.out.println(" [x] -> Sent '" + QUEUE_AIR_TEMP_OFFICE + "'");

                channel.basicPublish("", QUEUE_AIR_TEMP_LOUNGE, null, myTempLounge.getBytes());
                System.out.println(" [x] -> Sent '" + QUEUE_AIR_TEMP_LOUNGE + "'");

                Thread.sleep(1000); //waiting 1 sec
            }
        }
        catch (Exception e)
        {
            System.out.println("Oops! Something went wrong... =[   \nsetAirTemperature()");
        }
    }
}
