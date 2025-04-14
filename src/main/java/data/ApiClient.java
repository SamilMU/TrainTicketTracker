package data;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import models.TripRequest;

import static helpers.DateFormat.updateDateFormat;
import static helpers.HeaderBuilder.getHeaders;

public class ApiClient {
    private static final String BASE_URL = "https://web-api-prod-ytp.tcddtasimacilik.gov.tr/tms/train/train-availability?environment=dev&userId=1";

    public Response searchTrips(TripRequest request) {
        Headers headers = getHeaders(251);
        String formattedDate = updateDateFormat(request.getDepartureDate());

        return RestAssured.given()
                .headers(headers)
                .body(buildRequestBody(request, formattedDate))
                .post(BASE_URL)
                .andReturn();
    }

    private String buildRequestBody(TripRequest request, String formattedDate) {
        return "{\n" +
                "  \"searchRoutes\": [\n" +
                "    {\n" +
                "      \"departureStationId\": " + request.getDeparture().getId() + ",\n" +
                "      \"departureStationName\": \"" + request.getDeparture().getName() + "\",\n" +
                "      \"arrivalStationId\": " + request.getArrival().getId() + ",\n" +
                "      \"arrivalStationName\": \"" + request.getArrival().getName() + "\",\n" +
                "      \"departureDate\": \"" + formattedDate + "-2025 21:00:00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"passengerTypeCounts\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"count\": " + request.getPassengerCount() + "\n" +
                "    }\n" +
                "  ],\n" +
                "  \"searchReservation\": false\n" +
                "}";
    }
}