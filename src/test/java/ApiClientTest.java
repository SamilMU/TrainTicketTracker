import io.restassured.response.Response;
import models.Station;
import org.junit.jupiter.api.Test;
import services.CRUDBase;

import static org.junit.jupiter.api.Assertions.*;

public class ApiClientTest {

    @Test
    void testGetAllTrips() {
        CRUDBase crudBase = new CRUDBase();

        Response response = crudBase.getAllTrips("29-03", new Station(93, "ESKİŞEHİR", "Eskişehir"), new Station(48, "İSTANBUL(PENDİK)", "Istanbul-Pendik"));

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    void testLoadTrainByTrainId() {
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

    }

}