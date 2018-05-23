package com.property.finder;

import com.property.finder.models.Trip;
import com.property.finder.models.trips.TripFactory;
import com.property.finder.services.TripBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        List<Trip> trips = new ArrayList<Trip>();
        TripFactory factory = new TripFactory();
        trips.add(factory.buildTrip(
                "FLIGHT", "Gerona Airport", "Stockholm",
                new HashMap<String, String>(){{
                    put("flightNumber", "SK455");
                    put("gate", "45B");
                    put("seat", "3A");
                    put("extraInfo", "Baggage drop at ticket counter 344"); }}));
        trips.add(factory.buildTrip(
                "AIRPORT_BUS", "Barcelona", "Gerona Airport",
                new HashMap<String, String>()));
        trips.add(factory.buildTrip(
                "FLIGHT", "Stockholm", "New York JFK",
                new HashMap<String, String>(){{
                    put("flightNumber", "SK22");
                    put("gate", "22");
                    put("seat", "87");
                    put("extraInfo", "Baggage will be automatically transferred from your last leg"); }}));
        trips.add(factory.buildTrip(
                "TRAIN", "Madrid", "Barcelona",
                new HashMap<String, String>(){{ put("name","78");put("seat","45B");}}));
        TripBuilder builder = new TripBuilder();
        trips = builder.buildTrip(trips);
        trips.stream().map(Trip::tripToString).forEach(line -> System.out.println(line));
        System.out.println("You have arrived at your final destination.");
    }
}
