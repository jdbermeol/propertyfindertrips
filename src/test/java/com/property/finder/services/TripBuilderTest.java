package com.property.finder.services;

import com.property.finder.exceptions.IncorrectTripList;
import com.property.finder.models.Trip;
import com.property.finder.models.trips.TripFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TripBuilderTest {

    @Test
    public void checkEmptyList() throws Exception
    {
        TripBuilder builder = new TripBuilder();
        List<Trip> trips = builder.buildTrip(new ArrayList<Trip>());
        assertTrue( trips.isEmpty());
    }

    @Test(expected = IncorrectTripList.class)
    public void testMultipleSources() throws Exception
    {
        List<Trip> trips = new ArrayList<Trip>();
        TripFactory factory = new TripFactory();
        trips.add(factory.buildTrip(
                "AIRPORT_BUS", "A", "B",
                new HashMap<String, String>(){{ put("seat","B1");}}));
        trips.add(factory.buildTrip(
                "AIRPORT_BUS", "A", "C",
                new HashMap<String, String>(){{ put("seat","C1");}}));
        TripBuilder builder = new TripBuilder();
        builder.buildTrip(trips);
    }

    @Test(expected = IncorrectTripList.class)
    public void testMultipleDestinies() throws Exception
    {
        List<Trip> trips = new ArrayList<Trip>();
        TripFactory factory = new TripFactory();
        trips.add(factory.buildTrip(
                "AIRPORT_BUS", "B", "A",
                new HashMap<String, String>(){{ put("seat","B1");}}));
        trips.add(factory.buildTrip(
                "AIRPORT_BUS", "C", "A",
                new HashMap<String, String>(){{ put("seat","C1");}}));
        TripBuilder builder = new TripBuilder();
        builder.buildTrip(trips);
    }

    @Test
    public void correctOrder() throws Exception
    {
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
        assertTrue(trips.size() == 4);
        assertTrue(trips.get(0).getSource() == "Madrid");
        assertTrue(trips.get(0).getDestiny() == "Barcelona");
        assertTrue(trips.get(1).getSource() == "Barcelona");
        assertTrue(trips.get(1).getDestiny() == "Gerona Airport");
        assertTrue(trips.get(2).getSource() == "Gerona Airport");
        assertTrue(trips.get(2).getDestiny() == "Stockholm");
        assertTrue(trips.get(3).getSource() == "Stockholm");
        assertTrue(trips.get(3).getDestiny() == "New York JFK");
    }

    @Test(expected = IncorrectTripList.class)
    public void disconnectedTrips() throws Exception
    {
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
        builder.buildTrip(trips);
    }

}