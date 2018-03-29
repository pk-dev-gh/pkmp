/**
 * Main class that spawns all necessary actions for running the app. First it gets the processor instance and processes
 * input if any. For this app's purposes, given the single thread restriction, the input was modelled to resemble a
 * real time multithreaded queue, as much as possible. Therefore, while the app hasn't reached its processing limit
 * it reads messages from a process that creates a random number of messages (smaller than or equal to the app's capacity).
 * The messages' structure is pre-defined (3 different types). Nevertheless, the messages' type, the number of messages
 * of each type, as well as the content of each message is also random and different between different runs.
 **/
package com.pkmp;

import com.pkmp.logging.Log;
import com.pkmp.messages.Message;
import com.pkmp.core.Processor;
import com.pkmp.gateway.Input;

import java.util.Queue;

public class MessageApp {

    public static void main(String[] args) {
        //Get message processor
        Processor processor = Processor.getInstance();
        while( !processor.isPaused() ){
            //Create input that does not exceed the systems capacity, although this is not critical. The app still
            //behaves as expected, even in runs that the maximum capacity is reached during immediately. In cases
            //as such it just pauses after one message production/processing cycle.
            Input input = new Input(processor.getMaximumProcessingCapacity());
            Queue<Message> messages = input.getInput();
            processor.process(messages);
        }
        Log.getInstance().printFinalReport();
    }
}
