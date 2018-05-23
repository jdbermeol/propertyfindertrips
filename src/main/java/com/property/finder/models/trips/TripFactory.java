package com.property.finder.models.trips;

import com.property.finder.exceptions.MissingTripType;
import com.property.finder.models.Trip;

import java.util.Map;
import java.util.Optional;

/**
 * Factory for the different types of trips
 */
public class TripFactory {

    /**
     * Create a trip based on the given data
     * @param type
     * @param source
     * @param destiny
     * @param data map including information required for each of the trip types.
     * @return
     * @throws MissingTripType
     */
    public Trip buildTrip(String type, String source, String destiny, Map<String, String> data) throws MissingTripType {
        if(type == "AIRPORT_BUS"){
            return new AirportBusTrip(
                    data.containsKey("seat")? Optional.of(data.get("seat")) : Optional.empty(),
                    source, destiny
            );
        }
        if(type == "TRAIN"){
            return new TrainTrip(
                    data.get("trainName"),
                    data.containsKey("seat")? Optional.of(data.get("seat")) : Optional.empty(),
                    source, destiny);
        }
        if(type == "FLIGHT"){
            return new FlightTrip(
                    data.get("flightNumber"),
                    data.containsKey("gate")? Optional.of(data.get("gate")) : Optional.empty(),
                    data.containsKey("seat")? Optional.of(data.get("seat")) : Optional.empty(),
                    data.containsKey("extraInfo")? Optional.of(data.get("extraInfo")) : Optional.empty(),
                    source, destiny);
        }
        throw new MissingTripType();
    }
}
