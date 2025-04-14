package models;

public class TripRequest {
    private Station departure;
    private Station arrival;
    private String departureDate;
    private int passengerCount;

    public TripRequest(Station departure, Station arrival, String departureDate, int passengerCount) {
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;
        this.passengerCount = passengerCount;
    }

    // Getters
    public Station getDeparture() { return departure; }
    public Station getArrival() { return arrival; }
    public String getDepartureDate() { return departureDate; }
    public int getPassengerCount() { return passengerCount; }
}