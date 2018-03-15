package com.chaos.converter.exc;

/**
 * Created by ChaosFire on 01-Mar-18
 *
 * Signifies invalid implementation of a parser class.
 */
public class InvalidParserException extends RuntimeException {

    public InvalidParserException(String message, Throwable cause) {
        super(message, cause);
    }
}