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

import java.util.Random;

/** This is a client (program) that publishes messages every second about the room;
 *  like the floor/room/humidity (e.g. 25%)
 */
public class PublisherOneHumidity extends Thread
{
    //Set up the class and name the queue
    private static String AIR_HUMIDITY = "firstFloor/kitchen/humidity";

    // create instance of Random class
    static Random myRandom = new Random();

    public void run()
    {
        //Create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //RabbitMQ server host -> http://localhost:15672/#/

        try (Connection connection1 = factory.newConnection();
             Channel channel1 = connection1.createChannel();)
        {
            channel1.queueDeclare(AIR_HUMIDITY, false, false, false, null);

            for (int i = 0; i < 1000; i++)
            {
                double humidity = myRandom.nextDouble(0.5) + 83;

                //Format the temperature with two decimal places
                StringBuilder myHumidity = new StringBuilder();
                myHumidity.append("Humidity: ");
                myHumidity.append(String.format("%.2f" , humidity));
                myHumidity.append(" %");

                channel1.basicPublish("", AIR_HUMIDITY, null, String.valueOf(myHumidity).getBytes());
                System.out.println(" [x] Humidity -> Sent '" + i + "'");

                Thread.sleep(1000); //waiting 1 sec
            }
        }
        catch (Exception e)
        {
            System.out.println("Oops! Something went wrong... =[   \nsetAirHumidity()");
        }
    }
}
