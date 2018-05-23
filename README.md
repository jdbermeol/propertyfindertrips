# Property finder trips

You are given a stack of boarding cards for various transportations that will take you from a point A to
point B via several stops on the way. All of the boarding cards are out of order and you don't know
where your journey starts, nor where it ends. Each boarding card contains information about seat
assignment, and means of transportation (such as flight number, bus number etc).

## Assumptions

1. As we don't have a time when a trip is going to happen it is impossible to distinguish between 2 trips with the same source or destination, as we don't know which one is going to be first. Then we are going to assume there are not duplicate sources or destinies, therefore there are no cycles formed by the trips.

2. We are going to assume that boarding cards fit in memory.

## Implementation details

### Boarding cards construction

Currently I'm using a base class [Trip](https://github.com/jdbermeol/propertyfindertrips/blob/master/src/main/java/com/property/finder/models/Trip.java) to reprecent a boarding card. And a [factory service](https://github.com/jdbermeol/propertyfindertrips/blob/master/src/main/java/com/property/finder/models/trips/TripFactory.java) to build different type of trips.

Factory can be evolved or extended to build any trip from different initial representations like json objects, csv files, depending on the requirements.

### Sorting

Sorting is done by the service [TripBuilder](https://github.com/jdbermeol/propertyfindertrips/blob/master/src/main/java/com/property/finder/services/TripBuilder.java)

I'm using a linked lists and 2 indexes(one for sources and one for destinies) to easyly build the compose trip. Here are the steps:

1. First code checks if there are duplicate sources. Then the list of trips is invalid.
2. Then, the code checks if there are duplicate destinies. Then the list of trips is invalid.
3. Ones we have checked there are no cycles in our list of trips. We are going to start indexing each of our trips.
4. We are going to use an index from source to a linked list of already sorted trips(compose trips) and an index for destinies to a linked list of already sorted trips.
5. For a given trip A:
  5.1 We are going to check in the source index has a compose trip indexed by A destiny. If we found one, we are going to add A as the new initial trip.
  5.2 We are going to check in the destiny index has a compose trip indexed by A source. If we found one, we are going to add A as the last trip.
  5.3 Then we add the new compose trip to the source and destiny index.
 6. Ones we a have just one compose trip, we loop thought the linked fist to create the final static list.
  
 ## Testing
 
 `mvn test`
 
 ## Run example
 
 `mvn package`
 
 `mvn exec:java -Dexec.mainClass="com.property.finder.App"`
 
 ## Javadocs
 
 `mvn javadoc:javadoc`
 
 Currently there is an issue with java 10 and maven-jadocs-plugins(https://issues.apache.org/jira/browse/MJAVADOC-517) I did not have time to implement the solution.
 
 ## Space complexity
 
 Data structures used where a several linked list that in total had atmost the number of trips, O(n), being n the number of trips(boarding cards to sort). Also 2 indexes each had at most n different keys, then space required for a hash table is O(n). Then full space complexity is O(n).
 
 ## Time complexity
 
 Code loops thought the list of trips, for each of then search in each index wich is a constant time, O(1). Then it concats the trip with the already indexed compose trip, as we are using linked list with pointers to the end and front, concatenation takes O(1). Then final time complexity is O(n). 
  

