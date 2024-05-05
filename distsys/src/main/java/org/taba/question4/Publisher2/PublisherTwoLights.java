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

package org.taba.question4.Publisher2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/** This is a client (program) that publishes messages
 *  about the floor every half second like; the floor/light/ID (e.g ON).
 */
public class PublisherTwoLights extends Thread
{
    //Set up the class and name the queue
    private static String LIGHTS = "floor/light/ID";

    public void run()
    {
        //Create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //RabbitMQ server host -> http://localhost:15672/#/

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();)
        {
            channel.queueDeclare(LIGHTS, false, false, false, null);
            String msg = "Lights ON";

            channel.basicPublish("", LIGHTS, null, msg.getBytes() );
            System.out.println(" [x] Lights -> Sent '" + "'");

            Thread.sleep(1000); //waiting 1 sec
        }

        catch (Exception e)
        {
            System.out.println("Oops! Something went wrong... =[   \nLights()");
            e.printStackTrace();
        }
    }

}
