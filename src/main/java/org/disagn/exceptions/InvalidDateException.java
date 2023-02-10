package org.disagn.exceptions;

import java.text.ParseException;

public class InvalidDateException extends ParseException {

    public InvalidDateException() {
        super("bad date", 0);
    }

    /**
     * Constructs a ParseException with the specified detail message and
     * offset.
     * A detail message is a String that describes this particular exception.
     *
     * @param s           the detail message
     * @param errorOffset the position where the error is found while parsing.
     */


    public InvalidDateException(String s, int errorOffset) {
        super(s, errorOffset);
    }

    public InvalidDateException(String s) {
        super(s, 0);
    }
}
