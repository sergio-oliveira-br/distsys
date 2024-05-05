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

import java.util.Random;

public class PublisherOne
{
    //Set up the class and name the queue
    private static String AIR_TEMPERATURE = "firstFloor/kitchen/temperature";
    private static String AIR_HUMIDITY = "firstFloor/kitchen/humidity";

    // create instance of Random class
    static Random myRandom = new Random();


    /** RabbitMQ tutorial: https://www.rabbitmq.com/tutorials/tutorial-one-java */
    /** This will generate the temperature */
    public static void setAirTemperature()
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

            // Publish each transaction in the ArrayList to the queue with a delay
            for (int i = 0; i < 1000; i++)
            {
                double temperature = myRandom.nextDouble(0.8) + 18;

                //Format the temperature with two decimal places
                StringBuilder myTemp = new StringBuilder();
                myTemp.append("Temperature: ");
                myTemp.append(String.format("%.2f" , temperature));
                myTemp.append(" ºC");

                channel.basicPublish("", AIR_TEMPERATURE, null, String.valueOf(myTemp).getBytes());
                System.out.println(" [x] Temperature -> Sent '" + i + "'");

                Thread.sleep(1000); //waiting 1 sec
            }
        }
        catch (Exception e)
        {
            System.out.println("Oops! Something went wrong... =[   \nsetAirTemperature()");
        }
    }

    public static void setAirHumidity()
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

    public static void main(String[] args)
    {
        setAirTemperature();
        setAirHumidity();
    }
}
