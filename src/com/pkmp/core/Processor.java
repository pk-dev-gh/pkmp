/**
 *Class that processes all messages available in the message input queue.
 */
package com.pkmp.core;

import com.pkmp.logging.Log;
import com.pkmp.messages.Message;

import java.util.Queue;


public class Processor {

    private final int TRHESHOLD = 50;
    private int noOfMessagesProcessed = 0;
    private boolean paused = false;

    private Processor(){
    }

    private static Processor processor = new Processor();

    public static Processor getInstance() {

        return processor;

    }

    //Processes the input queue. Queue is empty at the end of process.
    public void process(Queue queue){

        while(queue.size() > 0 && noOfMessagesProcessed++ < TRHESHOLD){
            process((Message) queue.remove());
        }
        if(noOfMessagesProcessed >= TRHESHOLD) paused = true;

    }

    //Process a single message.
    private void process(Message message){

        Consumer consumer = new Consumer(message);
        Log log = Log.getInstance();
        log.recordMessage(message,noOfMessagesProcessed);
        if(noOfMessagesProcessed % 10 == 0)log.printIntermediateReport( noOfMessagesProcessed / 10);

    }

    //Returns processor (paused/active) status.
    public boolean isPaused(){

        return paused;
    }

    //Report the maximum number of messages the processor will process.
    public int getMaximumProcessingCapacity(){

        return TRHESHOLD;

    }

}
