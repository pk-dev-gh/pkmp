/**
 * Class for Type1 messages, e.g. "Apple at 10p"
 */
package com.pkmp.messages;

import com.pkmp.logging.Log;

public class Type1Message extends EncodedMessage{

    public Type1Message(int type, String[] tokens) {

        super(type, tokens[0],Integer.parseInt(tokens[2].substring(0,tokens[2].length() -1)));

    }

    //Performs necessary logistics and logs data before the instantiated object gets discarded.
    public void consume(){

        Log log = Log.getInstance();
        log.logEncodedMessage(getItem(),getPrice());

    }

}
