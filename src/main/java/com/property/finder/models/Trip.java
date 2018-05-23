package com.property.finder.models;

/**
 * Class represents a travel from source A to destiny B
 */
abstract public class Trip {

    private String source;
    private String destiny;

    /**
     * Constructs a trip from source to destiny
     * @param source
     * @param destiny
     */
    public Trip(String source, String destiny){
        this.source = source;
        this.destiny = destiny;
    }

    public String getSource() {
        return source;
    }

    public String getDestiny() {
        return destiny;
    }

    /**
     * @return String representation of the trip.
     */
    abstract public String tripToString();

}
