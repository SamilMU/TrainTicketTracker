import models.Station;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StationTest {

    @Test
    public void testStationCreation() {
        Station station = new Station(48, "İSTANBUL(PENDİK)", "Istanbul-Pendik");
        assertEquals(48, station.getId());
        assertEquals("İSTANBUL(PENDİK)", station.getName());
    }
}