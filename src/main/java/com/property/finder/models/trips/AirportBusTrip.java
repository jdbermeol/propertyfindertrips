package com.property.finder.models.trips;

import com.property.finder.models.Trip;

import java.util.Optional;

/**
 * Airport bus trip
 */
public class AirportBusTrip extends Trip {

    private Optional<String> seat;

    /**
     * Constructs a airport bus trip from source to destiny
     * @param seat bus seat.
     * @param source
     * @param destiny
     */
    public AirportBusTrip(Optional<String> seat, String source, String destiny){
        super(source, destiny);
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
                "Take the airport bus from %s to %s. %s.\n",
                this.getSource(), this.getDestiny(), this.seatMessage()
        );

    }
}
