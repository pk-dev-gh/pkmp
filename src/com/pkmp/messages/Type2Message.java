/**
 * Class for Type2 messages, e.g. "10 apples at 10p each".
 */
package com.pkmp.messages;

import com.pkmp.logging.Log;

public class Type2Message extends EncodedMessage {

    private int quantity;

    public Type2Message(int type, String[] tokens) {

        super(type, tokens[1].substring(0,tokens[1].length() -1),Integer.parseInt(tokens[3].substring(0,tokens[3].length() -1)));
        this.quantity = Integer.parseInt(tokens[0]);

    }

    //Performs necessary logistics and logs data before the instantiated object gets discarded.
    public void consume(){

        Log log = Log.getInstance();
        log.logEncodedMessage(getItem(),getPrice(),quantity);

    }

}
