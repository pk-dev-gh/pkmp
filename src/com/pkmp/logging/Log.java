/**
 * This class calculates and keeps track of all the logistics involved in reporting.
 */
package com.pkmp.logging;

import com.pkmp.messages.Message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.IntBinaryOperator;

public class Log {

    private LinkedList<Message> messageList = new LinkedList<Message>();
    private HashMap<String,LinkedList<Integer[]>> sales = new HashMap<String,LinkedList<Integer[]>>();
    private LinkedList<String> adjustments = new LinkedList<String>();


    private Log(){
    }

    private static Log log = new Log();

    public static Log getInstance() {

        return log;

    }

    //Formats price for output.
    private String formatPrice(int unformattedPrice){
        if(unformattedPrice > 100 ){
            double priceInPounds = (double)unformattedPrice /100;
            return Double.toString(priceInPounds)+"£";
        }
        else{
            return Integer.toString(unformattedPrice)+"p";
        }
    }

    //Records message that has been processed (consumed).
    public void recordMessage(Message message, int noOfMessagesProcessed){

        messageList.add(message);

    }

    //Logs the data of  consumed message of Type 1 that must be used for reporting.
    public void logEncodedMessage(String item, int price){

        if(!sales.containsKey(item)){
            sales.put(item, new LinkedList<Integer[]>());
        }
        LinkedList<Integer[]> values = sales.get(item);
        values.addLast(new Integer[]{1,price});

    }

    //Logs the data of consumed message of Type 2 that must be used for reporting.
    public void logEncodedMessage(String item, int price, int quantity){

        if(!sales.containsKey(item)){
            sales.put(item, new LinkedList<Integer[]>());
        }
        LinkedList<Integer[]> values = sales.get(item);
        values.addLast(new Integer[]{quantity,price});

    }

    //Logs the data of consumed message of Type 3 that must be used for reporting, after updating all that need to
    // be updated.
    public void logEncodedMessage(String item, int price, String adjustment){

        HashMap<String, IntBinaryOperator> adjustmentOps = new HashMap<>();
        adjustmentOps.put("add", (a, b) -> a + b);
        adjustmentOps.put("subtract", (a, b) -> a - b);
        adjustmentOps.put("multiply", (a, b) -> a * b);
        String adjustmentMessage = "";
        int salesAffected = 0;

        if(sales.containsKey(item)) {
            LinkedList<Integer[]> adjustedItem = sales.get(item);
            Iterator<Integer[]> iterator = adjustedItem.listIterator();
            while (iterator.hasNext()) {
                Integer[] sale = iterator.next();
                adjustmentOps.get(adjustment).applyAsInt(sale[1], price);
                salesAffected += sale[0];
            }
        }

        switch (adjustment) {
            case "add":
                adjustmentMessage += "Added to the price of "+item+"s "+formatPrice(price);
                break;
            case "subtract":
                adjustmentMessage += "Subtracted from the price of "+item+"s "+formatPrice(price);
                break;
            case "multiply":
                adjustmentMessage += "Multiplied the price of "+item+"s by a factor of "+price;
                break;
        }

        adjustmentMessage += ". This affected "+salesAffected+" sales of "+item+"s in total that had been made thus far.";
        adjustments.add(adjustmentMessage);

    }

    //Returns the initial (pre-processed) message list.
    public LinkedList<Message> getMessageList(){

        return messageList;
    }

    //Print the required report after every 10 messages.
    public void printIntermediateReport(int reportId){

        System.out.println("Starting Intermediate Report no. "+reportId+" (messages "+(reportId*10-10+1)+" to "+reportId*10+")");
        sales.forEach((key,list) -> {
            int noOfSales = 0;
            int totalValue = 0;
            Iterator<Integer[]> iterator = list.listIterator();
            while (iterator.hasNext()){
                Integer[] sale = iterator.next();
                noOfSales += sale[0];
                totalValue += sale[0]*sale[1];
            }
            System.out.println("Item: "+key+"(s) Total sales: "+noOfSales+" Total Value: "+formatPrice(totalValue));

        });

        System.out.println("End of Intermediate Report no. "+reportId+"\n");

    }

    //Prints the required report when the processor reaches it's processing limit (currently 50 messages).
    public void printFinalReport(){

        System.out.println("System  is now pausing and will no longer accept messages...\n");
        System.out.println("Printing Final Report.");
        adjustments.forEach((adj) -> {System.out.println(adj);});
        System.out.println("End of  Final Report.\n");
        System.out.println("System Paused.");

    }
}
