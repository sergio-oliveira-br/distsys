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

        //Lights
        PublisherTwoLights myPublisherTwoLights = new PublisherTwoLights();

        //myPublisherOneTemp.start();
        //myPublisherOneHumidity.start();
        myPublisherTwoLights.start();
    }
}
