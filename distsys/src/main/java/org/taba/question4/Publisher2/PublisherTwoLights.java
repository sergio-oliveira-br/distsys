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
    private static final String QUEUE_LIGHTS_KITCHEN = "1ºFloor/Lights:Kitchen/ID:1122";
    private static final String QUEUE_LIGHTS_ROOM = "2ºFloor/Light:Room/ID:2254";
    private static final String QUEUE_LIGHTS_OFFICE = "2ºFloor/Light:Office/ID:2260";
    private static final String QUEUE_LIGHTS_LOUNGE = "1ºFloor/Light:Lounge/ID:1197";

    public void run()
    {
        //Create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //RabbitMQ server host -> http://localhost:15672/#/

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();)
        {
            //Declaring the Queue
            channel.queueDeclare(QUEUE_LIGHTS_KITCHEN, true, false, false, null);
            channel.queueDeclare(QUEUE_LIGHTS_ROOM, true, false, false, null);
            channel.queueDeclare(QUEUE_LIGHTS_OFFICE, true, false, false, null);
            channel.queueDeclare(QUEUE_LIGHTS_LOUNGE, true, false, false, null);
            //b:true, is durable queue / b1:false, is not exclusive / b2:false, it won't be deleted automatically

            for (int i = 0; i < 10; i++)
            {
                //Publishing the Messages (ON/OFF)
                String msgON = "Lights ON";
                String msgOFF = "Lights OFF";

                channel.basicPublish("", QUEUE_LIGHTS_KITCHEN, null, msgON.getBytes() );
                System.out.println(" [x] Publisher Two -> Sent '" + "'");

                channel.basicPublish("", QUEUE_LIGHTS_ROOM, null, msgOFF.getBytes() );
                System.out.println(" [x] Publisher Two -> Sent '" + "'");

                channel.basicPublish("", QUEUE_LIGHTS_OFFICE, null, msgOFF.getBytes() );
                System.out.println(" [x] Publisher Two -> Sent '" + "'");

                channel.basicPublish("", QUEUE_LIGHTS_LOUNGE, null, msgON.getBytes() );
                System.out.println(" [x] Publisher Two -> Sent '" + "'");

                Thread.sleep(500); //waiting 1/2 sec
            }
        }

        catch (Exception e)
        {
            System.out.println("Oops! Something went wrong... =[   \nLights()");
            e.printStackTrace();
        }
    }

}
