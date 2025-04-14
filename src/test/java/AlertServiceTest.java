import org.junit.jupiter.api.Test;
import services.AlertService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AlertServiceTest {

    @Test
    public void testCheckAndAlertForAvailability() {
        AlertService alertService = new AlertService(true);

        // Create test data
        List<LinkedHashMap<String, Object>> trainAvailabilities = new ArrayList<>();
        LinkedHashMap<String, Object> trip = new LinkedHashMap<>();

        // Create trains list
        List<LinkedHashMap<String, Object>> trainList = new ArrayList<>();
        LinkedHashMap<String, Object> train = new LinkedHashMap<>();
        train.put("type", "YHT");

        // Create cars with availabilities
        ArrayList<LinkedHashMap<String, Object>> cars = new ArrayList<>();
        LinkedHashMap<String, Object> car = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> availabilities = new ArrayList<>();
        LinkedHashMap<String, Object> availability = new LinkedHashMap<>();
        availability.put("availability", 2);
        LinkedHashMap<String, Object> cabinClass = new LinkedHashMap<>();
        cabinClass.put("name", "Economy");
        availability.put("cabinClass", cabinClass);
        availabilities.add(availability);
        car.put("availabilities", availabilities);
        cars.add(car);
        train.put("cars", cars);

        // Create segments
        List<LinkedHashMap<String, Object>> segments = new ArrayList<>();
        LinkedHashMap<String, Object> segment = new LinkedHashMap<>();
        segment.put("departureTime", "1714392000000");  // Example epoch time
        segments.add(segment);
        train.put("segments", segments);

        trainList.add(train);
        trip.put("trains", trainList);
        trainAvailabilities.add(trip);

        // Test the processTrainAvailabilities method directly
        String minThreshold = "06:00";
        String maxThreshold = "23:59";
        List<String> alerts = alertService.processTrainAvailabilities(trainAvailabilities, minThreshold, maxThreshold, true);

        assertNotNull(alerts);
    }
}
