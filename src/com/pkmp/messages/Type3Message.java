/**
 * Class for Type3 messages, e.g. "add|subtract|multiply 10p apples".
 */
package com.pkmp.messages;

import com.pkmp.logging.Log;

public class Type3Message extends EncodedMessage {

    private String adjustment;

    public Type3Message(int type, String[] tokens) {

        super(type, tokens[2].substring(0,tokens[2].length() -1),Integer.parseInt(tokens[1].substring(0,tokens[1].length() -1)));
        this.adjustment = tokens[0];

    }

    //Performs necessary logistics and logs data before the instantiated object gets discarded.
    public void consume(){

        Log log = Log.getInstance();
        log.logEncodedMessage(getItem(),getPrice(), adjustment);

    }
}
