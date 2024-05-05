package org.taba.question4.Publisher1;

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
