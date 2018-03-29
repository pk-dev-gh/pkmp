/**
 * Class that creates (pseudo) real time input. Would have been more realistic should this app could spawn more than
 * one thread.
 */
package com.pkmp.gateway;

import com.pkmp.messages.Message;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Input {

    private Queue<Message> messages;

    public Input(int maxMessageNumber){

        produce(maxMessageNumber);

    }

    public Queue<Message> getInput(){

         return this.messages;
    }

    //Starts mesasge production
    private void produce(int maxMessageNumber) {

        int randomInt = getRandomInt(1,maxMessageNumber);
        messages = new LinkedList<Message>();
        for (int i=0;i<randomInt;i++){
            messages.add(createRandomMessage(getRandomInt(1,3)));
        }

    }

    //Picks a random message type (out of the three pre-defined types)
    private Message createRandomMessage(int type){

        String message;
        switch (type){
            case 1: return new Message(createMessageType1());
            case 2: return new Message(createMessageType2());
            case 3: return new Message(createMessageType3());
            default: return new Message(null);
        }

    }

    //Creates a random message of type 1
    private String createMessageType1(){
        return randomProductType()+" at "+randomProductPrice()+"p";
    }

    //Creates a random message of type 2
    private String createMessageType2(){
        return getRandomInt(2,100)+" "+randomProductType()+"s at "+randomProductPrice()+"p each";
    }

    //Creates a random message of type 3
    private String createMessageType3(){

        int type = getRandomInt(1,3);
        String adj;
        switch (type){
            case 1: adj = "add";
                break;
            case 2: adj = "subtract";
                break;
            case 3: adj = "multiply";
                break;
            default: adj= "add";
        }

        return adj+" "+randomAdjPrice()+"p "+randomProductType()+"s";

    }

    //Picks a random product (item) type. For testing purposes this was set to the items in the items[] array
    //below. Nevertheless, there no theoretical limit to the potential number of different products.
    private String randomProductType(){

        String items[] = new String[] {"apple","orange","banana","apricot","pineapple"};
        return items[getRandomInt(0,items.length - 1)];

    }

    //Pick a random product type
    private String randomProductPrice(){

        return String.valueOf(getRandomInt(1, 100));

    }

    //Pick a random adjustment action.
    private String randomAdjPrice(){

        return String.valueOf(getRandomInt(1, 5));

    }

    //Return a random int from min (inclusive) to max(inclusive).
    private int getRandomInt(int min, int max){

        Random random = new Random();
        return random.nextInt(max-min +1) + min;
    }

}
