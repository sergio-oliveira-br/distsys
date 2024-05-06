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
 *  *  https://www.rabbitmq.com/tutorials/tutorial-one-java
 */

package org.taba.question4.Publisher1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Random;

/** This is a client (program) that publishes messages every second about the room;
 *  like the floor/room/humidity (e.g. 25%)
 */
public class PublisherOneHumidity extends Thread
{
    //Set up the class and name the queue
    private static final String QUEUE_AIR_HUMIDITY_KITCHEN = "FirstFloor/Kitchen/Humidity";
    private static final String QUEUE_AIR_HUMIDITY_ROOM = "SecondFloor/Room/Humidity";
    private static final String QUEUE_AIR_HUMIDITY_OFFICE = "SecondFloor/Office/Humidity";
    private static final String QUEUE_AIR_HUMIDITY_LOUNGE = "FirstFloor/Lounge/Humidity";


    // create instance of Random class
    static Random myRandom = new Random();

    public void run()
    {
        //Create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //RabbitMQ server host -> http://localhost:15672/#/

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel())
        {
            channel.queueDeclare(QUEUE_AIR_HUMIDITY_KITCHEN, false, false, false, null);
            channel.queueDeclare(QUEUE_AIR_HUMIDITY_ROOM, false, false, false, null);
            channel.queueDeclare(QUEUE_AIR_HUMIDITY_OFFICE, false, false, false, null);
            channel.queueDeclare(QUEUE_AIR_HUMIDITY_LOUNGE, false, false, false, null);

            //Publish every random number multiple times with a delay.
            for (int i = 0; i < 1000; i++)
            {
                //Generating a random number
                double humidityKitchen = myRandom.nextDouble(0.5) + 85;
                double humidityRoom = myRandom.nextDouble(0.5) + 86;
                double humidityOffice = myRandom.nextDouble(0.5) + 87;
                double humidityLounge = myRandom.nextDouble(0.5) + 88;

                //Format the temperature with two decimal places
                String myHumidityKitchen = String.format("%.2fº", humidityKitchen) + "% (H20) Kitchen";
                String myHumidityRoom = String.format("%.2f", humidityRoom) + "% (H20) Room";
                String myHumidityOffice = String.format("%.2f", humidityOffice) + "%  (H20) Office";
                String myHumidityLounge = String.format("%.2f", humidityLounge) + "% (H20) Lounge";

                channel.basicPublish("", QUEUE_AIR_HUMIDITY_KITCHEN, null, myHumidityKitchen.getBytes());
                System.out.println(" [x] Publisher One -> Sent '" + QUEUE_AIR_HUMIDITY_KITCHEN + "'");

                channel.basicPublish("", QUEUE_AIR_HUMIDITY_ROOM, null, myHumidityRoom.getBytes());
                System.out.println(" [x] Publisher One -> Sent '" + QUEUE_AIR_HUMIDITY_ROOM + "'");

                channel.basicPublish("", QUEUE_AIR_HUMIDITY_OFFICE, null, myHumidityOffice.getBytes());
                System.out.println(" [x] Publisher One -> Sent '" + QUEUE_AIR_HUMIDITY_OFFICE + "'");

                channel.basicPublish("", QUEUE_AIR_HUMIDITY_LOUNGE, null, myHumidityLounge.getBytes());
                System.out.println(" [x] Publisher One -> Sent '" + QUEUE_AIR_HUMIDITY_LOUNGE + "'");

                Thread.sleep(1000); //waiting 1 sec
            }
        }
        catch (Exception e)
        {
            System.out.println("Oops! Something went wrong... =[   \nsetAirHumidity()");
        }
    }
}
