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

import org.taba.question4.Publisher1.PublisherOneHumidity;
import org.taba.question4.Publisher1.PublisherOneTemp;

public class MainPublisherTwo
{
    public static void main(String[] args)
    {
        //Lights
        PublisherTwoLights myPublisherTwoLights = new PublisherTwoLights();

        //Window

        myPublisherTwoLights.start();

    }
}
