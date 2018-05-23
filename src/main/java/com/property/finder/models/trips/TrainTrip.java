package com.property.finder.models.trips;

import com.property.finder.models.Trip;

import java.util.Optional;

/**
 * Train trip
 */
public class TrainTrip extends Trip {

    private String trainName;
    private Optional<String> seat;

    /**
     * Constructs a train trip from source to destiny
     * @param trainName
     * @param seat
     * @param source
     * @param destiny
     */
    public TrainTrip(String trainName, Optional<String> seat, String source, String destiny){
        super(source, destiny);
        this.trainName = trainName;
        this.seat = seat;

    }

    private String seatMessage(){
        return this.seat
                .map(s ->  String.format("Sit in seat %s", s))
                .orElse("No seat assignment");
    }

    /**
     * @return String representation of the trip.
     */
    @Override
    public String tripToString() {
        return String.format(
                "Take train %s from %s to %s. %s.\n",
                this.trainName, this.getSource(), this.getDestiny(), this.seatMessage()
        );

    }
}
