package com.property.finder.exceptions;

/**
 * Exception for in incorrect list of trips.
 */
public class IncorrectTripList extends Exception{

    /**
     * Constructs a new exception with the specified detail message.
     * @param msg the detail message.
     */
    public IncorrectTripList(String msg){
        super(msg);
    }
}
