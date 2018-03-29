/**
 * Class that is used to transform (encode) input messages to objects the processor can consume.
 */
package com.pkmp.core;

import com.pkmp.messages.*;

/**
 *
 */
public class Consumer {

    private EncodedMessage message;

    public Consumer(Message message){

        this.message = encode(message);
        if(this.message != null) this.message.consume();

    }

    //Encodes an incoming message object in a form the processor can process (consume).
    private EncodedMessage encode(Message message){

        String[] messageTokens = message.getMessage().trim().split(" +");
        if(messageTokens[messageTokens.length -1].equals("each")){
            return new Type2Message(2, messageTokens);
        }
        else if(messageTokens[messageTokens.length -1].substring(messageTokens[messageTokens.length -1].length() -1 ).equals("p")){
            return new Type1Message(1, messageTokens);
        }
        else if(messageTokens[0].matches("add|subtract|multiply")){
            return new Type3Message(3, messageTokens);
        }
        else{
            return null;
        }

    }

    //Return the encoded message.
    public EncodedMessage getMessage(){

        return message;

    }

}
