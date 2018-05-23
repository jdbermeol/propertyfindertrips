package com.property.finder.services;

import com.property.finder.exceptions.IncorrectTripList;
import com.property.finder.models.Trip;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to sort a list of trips in order to have a complete itinerary.
 */
public class TripBuilder {
    private Map<String, LinkedTrips> sourceIdx;
    private Map<String, LinkedTrips> destinyIdx;

    private class TripNode {
        public Trip trip;
        public Optional<TripNode> nextTrip;

        public TripNode(Trip trip){
            this.trip = trip;
            this.nextTrip = Optional.empty();
        }
    }

    private class LinkedTrips {
        public TripNode first;
        public TripNode last;
        public LinkedTrips(TripNode first, TripNode last){
            this.first = first;
            this.last = last;
        }
    }

    private void includeTrip(Trip trip) {
        TripNode node = new TripNode(trip);
        TripNode firstTrip = node;
        TripNode lastTrip = node;
        if(this.sourceIdx.containsKey(trip.getDestiny())){
            node.nextTrip = Optional.of(this.sourceIdx.get(trip.getDestiny()).first);
            lastTrip = this.sourceIdx.get(trip.getDestiny()).last;
            this.sourceIdx.remove(trip.getDestiny());

        }
        if(this.destinyIdx.containsKey(trip.getSource())){
            this.destinyIdx.get(trip.getSource()).last.nextTrip = Optional.of(node);
            firstTrip = this.destinyIdx.get(trip.getSource()).first;
            this.destinyIdx.remove(trip.getSource());
        }
        LinkedTrips linkedTrips = new LinkedTrips(firstTrip, lastTrip);
        this.sourceIdx.put(firstTrip.trip.getSource(), linkedTrips);
        this.destinyIdx.put(lastTrip.trip.getDestiny(), linkedTrips);

    }

    private boolean allUniques(List<String> names){
        Set<String> uniques = new HashSet<String>(names);
        return uniques.size() == names.size();
    }

    public List<Trip> buildTrip(List<Trip> trips) throws IncorrectTripList {
        if(trips.isEmpty()){
            return List.of();
        }
        List<String> sources = trips.stream().map(Trip::getSource).collect(Collectors.toList());
        if(!this.allUniques(sources)){
            throw new IncorrectTripList("Multiple sources in list.");
        }

        List<String> destinies = trips.stream().map(Trip::getDestiny).collect(Collectors.toList());
        if(!this.allUniques(destinies)){
            throw new IncorrectTripList("Multiple destinies in list.");
        }

        this.sourceIdx = new HashMap<>();
        this.destinyIdx = new HashMap<>();
        trips.forEach((trip) -> this.includeTrip(trip));
        if (this.sourceIdx.size() != 1){
            throw new IncorrectTripList("There are disconnected trips");
        }

        TripNode node = this.sourceIdx.values().stream().findFirst().get().first;
        List<Trip> resultList = new ArrayList<Trip>(trips.size());
        while(node.nextTrip.isPresent()){
            resultList.add(node.trip);
            node = node.nextTrip.get();
        }
        resultList.add(node.trip);
        return resultList;
    }
}
