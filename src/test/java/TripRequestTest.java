import models.Station;
import models.TripRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TripRequestTest {

    @Test
    public void testTripRequestCreation() {
        Station departure = new Station(48, "İSTANBUL(PENDİK)", "Istanbul-Pendik");
        Station arrival = new Station(98, "ANKARA GAR", "Ankara");
        TripRequest request = new TripRequest(departure, arrival, "25-12", 2);

        assertEquals(departure, request.getDeparture());
        assertEquals(arrival, request.getArrival());
        assertEquals("25-12", request.getDepartureDate());
        assertEquals(2, request.getPassengerCount());
    }
}