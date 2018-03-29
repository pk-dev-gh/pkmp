/**
 * Class for pre-processing plaintext messages, as they arrive, in order to be processed. Pre-processing
 * is used in order to create a common (base) object for all incoming messages. Although the app consumes
 * certain object types (Type1|2|3Message), these get discarded after processing. Objects of this class
 * get recorded and could be re-used or passed to another processor (if the app was to be extended), if
 * necessary.
 */
package com.pkmp.messages;

public class Message {

    private String message;

    public Message( String message){

        this.message = message;

    }

    public String getMessage(){

        return message;

    }
}
