package services;

import data.ApiClient;
import io.restassured.response.Response;
import models.Station;
import models.TripRequest;

public class TripService {
    private static final Station ISTANBUL = new Station(48, "İSTANBUL(PENDİK)", "Istanbul-Pendik");
    private static final Station ANKARA = new Station(98, "ANKARA GAR", "Ankara");

    private ApiClient apiClient;

    public TripService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response getIstanbulToAnkaraTrips(String date, int passengerCount) {
        TripRequest request = new TripRequest(ISTANBUL, ANKARA, date, passengerCount);
        return apiClient.searchTrips(request);
    }

    public Response getAnkaraToIstanbulTrips(String date, int passengerCount) {
        TripRequest request = new TripRequest(ANKARA, ISTANBUL, date, passengerCount);
        return apiClient.searchTrips(request);
    }

    public Response getTrips(String date, boolean isOutbound, int passengerCount) {
        if (isOutbound) {
            return getIstanbulToAnkaraTrips(date, passengerCount);
        } else {
            return getAnkaraToIstanbulTrips(date, passengerCount);
        }
    }
}