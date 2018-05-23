package com.property.finder.models.trips;

import com.property.finder.models.Trip;

import java.util.Optional;

/**
 * Flight trip
 */
public class FlightTrip extends Trip {

    private String flightNumber;
    private Optional<String> gate;
    private Optional<String> seat;
    private Optional<String> extraInfo;

    /**
     *  Constructs a flight trip from source to destiny
     * @param flightNumber
     * @param gate
     * @param seat
     * @param extraInfo information about flight status, baggage, etc.
     * @param source
     * @param destiny
     */
    public FlightTrip(String flightNumber, Optional<String> gate, Optional<String> seat, Optional<String> extraInfo, String source, String destiny){
        super(source, destiny);
        this.flightNumber = flightNumber;
        this.gate = gate;
        this.seat = seat;
        this.extraInfo = extraInfo;

    }

    private String gateMessage(){
        return this.gate
                .map(s ->  String.format("Gate %s", s))
                .orElse("No gate assignment");
    }

    private String seatMessage(){
        return this.seat
                .map(s ->  String.format("Sit in seat %s", s))
                .orElse("No seat assignment");
    }

    private String extraInfoMessage(){
        return this.extraInfo
                .map(s ->  String.format("%s.", s))
                .orElse("");
    }

    /**
     * @return String representation of the trip.
     */
    @Override
    public String tripToString() {
        return String.format(
                "From %s, take flight %s to %s. %s, %s. %s\n",
                this.getSource(),
                this.flightNumber,
                this.getDestiny(),
                this.gateMessage(),
                this.seatMessage(),
                this.extraInfoMessage()
        );

    }
}
