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

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/** This is a client (program) that publishes messages
 *  about the floor every half second like floor/window/location (e.g. OPEN/CLOSE).
 */
public class PublisherTwoWindow extends Thread
{
    //Set up the class and name the queue
    private static final String QUEUE_WINDOW_KITCHEN = "1ºFloor/Window/Kitchen";
    private static final String QUEUE_WINDOW_ROOM = "2ºFloor/Window/Room";
    private static final String QUEUE_WINDOW_OFFICE = "2ºFloor/Window/Office";
    private static final String QUEUE_WINDOW_LOUNGE = "1ºFloor/Window/Lounge";

    public void run()
    {
        //Create a connection to the server:
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //RabbitMQ server host -> http://localhost:15672/#/

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();)
        {
            //Declaring the Queue
            channel.queueDeclare(QUEUE_WINDOW_KITCHEN, false, false, false, null);
            channel.queueDeclare(QUEUE_WINDOW_ROOM, false, false, false, null);
            channel.queueDeclare(QUEUE_WINDOW_OFFICE, false, false, false, null);
            channel.queueDeclare(QUEUE_WINDOW_LOUNGE, false, false, false, null);

            //Publish every random number multiple times with a delay.
            for (int i = 0; i < 50; i++)
            {
                //Publishing the Messages (ON/OFF)
                String msgOpen = " -> Window [OPEN]";
                String msgClose = " -> Window [CLOSE]";

                //Personalize the msg
                String myKitchen = QUEUE_WINDOW_KITCHEN + msgOpen;
                String myRoom = QUEUE_WINDOW_ROOM + msgClose;
                String myOffice = QUEUE_WINDOW_OFFICE + msgClose;
                String myLounge = QUEUE_WINDOW_LOUNGE + msgClose;

                //Publishing...
                channel.basicPublish("", QUEUE_WINDOW_KITCHEN, null, myKitchen.getBytes());
                System.out.println(" [x] Publisher Two -> Sent Lights Status: '" + QUEUE_WINDOW_KITCHEN + "'");

                channel.basicPublish("", QUEUE_WINDOW_ROOM, null, myRoom.getBytes());
                System.out.println(" [x] Publisher Two -> Sent Lights Status: '" + QUEUE_WINDOW_ROOM + "'");

                channel.basicPublish("", QUEUE_WINDOW_OFFICE, null, myOffice.getBytes());
                System.out.println(" [x] Publisher Two -> Sent Lights Status: '" + QUEUE_WINDOW_OFFICE + "'");

                channel.basicPublish("", QUEUE_WINDOW_LOUNGE, null, myLounge.getBytes());
                System.out.println(" [x] Publisher Two -> Sent Lights Status: '" + QUEUE_WINDOW_LOUNGE + "'");

                Thread.sleep(500); //waiting 1/2 sec
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
