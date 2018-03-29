/**
 * Base class for messages the app can consume.
 */
package com.pkmp.messages;

public abstract class EncodedMessage {

    private final int type;
    private String item;
    private int price;

    EncodedMessage(int type, String item, int price){

        this.type = type;
        this.item = item;
        this.price = price;

    }

    //Get message type
    public int getType(){

        return type;

    }

    //Get sale item
    public String getItem(){

        return item;

    }

    //Get item/adjustment price
    public int getPrice(){

        return price;
    }

    public abstract void consume();

}
