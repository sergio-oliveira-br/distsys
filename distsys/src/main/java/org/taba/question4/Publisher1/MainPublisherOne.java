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
package org.taba.question4.Publisher1;

import org.taba.question4.Publisher2.PublisherTwoLights;

public class MainPublisherOne
{
    public static void main(String[] args)
    {
        //Temperature
        PublisherOneTemp myPublisherOneTemp = new PublisherOneTemp();

        //Humidity
        PublisherOneHumidity myPublisherOneHumidity = new PublisherOneHumidity();

        myPublisherOneTemp.start();
        myPublisherOneHumidity.start();
    }
}
