import data.ApiClient;
import io.restassured.response.Response;
import models.TripRequest;
import org.junit.jupiter.api.Test;
import services.TripService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class TripServiceTest {

    @Test
    public void testGetIstanbulToAnkaraTrips() {
        ApiClient apiClient = mock(ApiClient.class);
        TripService tripService = new TripService(apiClient);

        // Use argument matchers instead of specific objects
        when(apiClient.searchTrips(any(TripRequest.class))).thenReturn(mock(Response.class));

        Response response = tripService.getIstanbulToAnkaraTrips("25-12", 2);
        assertNotNull(response);
    }

    @Test
    public void testGetAnkaraToIstanbulTrips() {
        ApiClient apiClient = mock(ApiClient.class);
        TripService tripService = new TripService(apiClient);

        // Use argument matchers instead of specific objects
        when(apiClient.searchTrips(any(TripRequest.class))).thenReturn(mock(Response.class));

        Response response = tripService.getAnkaraToIstanbulTrips("25-12", 2);
        assertNotNull(response);
    }
}