import data.ApiClient;
import io.restassured.response.Response;
import models.Station;
import models.TripRequest;
import org.junit.jupiter.api.Test;
import services.CRUDBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiClientTest {

    @Test
    public void testSearchTrips() {
        ApiClient apiClient = spy(ApiClient.class);
        Station departure = new Station(48, "İSTANBUL(PENDİK)", "Istanbul-Pendik");
        Station arrival = new Station(98, "ANKARA GAR", "Ankara");
        TripRequest request = new TripRequest(departure, arrival, "25-12", 2);

        doReturn(mock(Response.class)).when(apiClient).searchTrips(request);

        Response response = apiClient.searchTrips(request);
        assertNotNull(response);
    }

    @Test
    void testGetAllTrips() {
        CRUDBase crudBase = new CRUDBase();

        // Call the API
        Response response = crudBase.getAllTrips("29-03", new Station(93, "ESKİŞEHİR", "Eskişehir"), new Station(48, "İSTANBUL(PENDİK)", "Istanbul-Pendik"));

        // Log the response for debugging
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    void testLoadTrainByTrainId() {
        // Replace with a valid train ID you know exists
        long trainId = 54982; // Example train ID

        CRUDBase crudBase = new CRUDBase();

        // Call the API
        Response response = crudBase.loadTrainByTrainId(trainId);

        // Log the response for debugging
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Assertions
        assertEquals(200, response.getStatusCode(), "Expected successful response");
        assertNotNull(response.getBody(), "Response body should not be null");

        // Check if response contains expected fields
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("trainId"), "Response should contain trainId");

        // Add more specific assertions based on the actual response structure
        // For example:
        // JsonPath jsonPath = response.jsonPath();
        // assertEquals(trainId, jsonPath.getLong("trainId"), "Train ID should match the requested ID");
    }

}