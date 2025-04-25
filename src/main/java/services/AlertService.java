package services;

import io.restassured.response.ResponseBody;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static helpers.EpochTimeConverter.convertEpochToDateTime;

@Service
public class AlertService {
    private List<String> alarmList = new ArrayList<>();
    private boolean logAllTrains;

    public AlertService() {
        this(true);
    }

    public AlertService(boolean logAllTrains) {
        this.logAllTrains = logAllTrains;
    }

    public List<String> checkAndAlertForAvailability(ResponseBody allTrips, String minThreshold, String maxThreshold, boolean alarmsEnabled) {
        List<LinkedHashMap<String, Object>> trainAvailabilities = allTrips.jsonPath().getList("trainLegs[0].trainAvailabilities");
        return processTrainAvailabilities(trainAvailabilities, minThreshold, maxThreshold, alarmsEnabled);
    }

    public List<String> processTrainAvailabilities(List<LinkedHashMap<String, Object>> trainAvailabilities, String minThreshold, String maxThreshold, boolean alarmsEnabled) {
        alarmList.clear();
        System.out.println("Seats checked. Time : " + LocalDateTime.now());

        for (LinkedHashMap<String, Object> trips : trainAvailabilities) {
            int availableSeats = 0;
            List<LinkedHashMap<String, Object>> trainList = (List<LinkedHashMap<String, Object>>) trips.get("trains");

            if (!trainList.get(0).get("type").equals("YHT")) {
                continue;
            }

            ArrayList<LinkedHashMap<String, Object>> vagonList = (ArrayList<LinkedHashMap<String, Object>>) trainList.get(0).get("cars");

            for (LinkedHashMap<String, Object> vagon : vagonList) {
                List<LinkedHashMap<String, Object>> availabilityList = (List<LinkedHashMap<String, Object>>) vagon.get("availabilities");

                if (availabilityList.isEmpty()) {
                    continue;
                }

                if(Objects.isNull(availabilityList.get(0).get("cabinClass"))){
                    continue;
                }

                LinkedHashMap<String, Object> currentCarSpecs = (LinkedHashMap<String, Object>) availabilityList.get(0).get("cabinClass");

                if (!currentCarSpecs.get("name").toString().toLowerCase().contains("tekerlekli")) {
                    availableSeats += (int) availabilityList.get(0).get("availability");
                }
            }

            List<LinkedHashMap<String, Object>> segmentList = (List<LinkedHashMap<String, Object>>) trainList.get(0).get("segments");

            String tripTimeInEpoch = segmentList.get(0).get("departureTime").toString();
            String tripTime = convertEpochToDateTime(Long.parseLong(tripTimeInEpoch));

            // Log all trains, even those that do not meet the criteria
            if (availableSeats >= 1 && logAllTrains) {
                System.out.println("Available seats (Time unspecific)");
                System.out.println("Train info : " + tripTime);
                System.out.println("Count : " + availableSeats);
            }

            // Only fire an alert if the trip time is after the minimum and before (or equal to) the maximum threshold
            if (isAfterThresholdTime(tripTime, minThreshold) && isBeforeThresholdTime(tripTime, maxThreshold) && availableSeats >= 1) {
                alarmList.add(tripTime);
                System.out.println("//////////////////////////////////////////////");
                System.out.println("Desired Seat Found");
                System.out.println("Train info : " + tripTime);
                System.out.println("Count : " + availableSeats);
                System.out.println("https://ebilet.tcddtasimacilik.gov.tr/sefer-listesi");
                System.out.println("//////////////////////////////////////////////");

            }
        }

        return alarmList;
    }

    private boolean isAfterThresholdTime(String tripTime, String threshold) {
        String tripTimeStr = tripTime.split(" ")[4];
        int tripTimeHour = Integer.parseInt(tripTimeStr.split(":")[0]);
        int tripTimeMinute = Integer.parseInt(tripTimeStr.split(":")[1]);

        int hourOfThreshold = 0;
        int minuteOfThreshold = 0;

        if (threshold.contains(":")) {
            hourOfThreshold = Integer.parseInt(threshold.split(":")[0]);
            minuteOfThreshold = Integer.parseInt(threshold.split(":")[1]);
        } else {
            hourOfThreshold = Integer.parseInt(threshold);
        }

        return tripTimeHour > hourOfThreshold ||
               (tripTimeHour == hourOfThreshold && tripTimeMinute >= minuteOfThreshold);
    }

    private boolean isBeforeThresholdTime(String tripTime, String threshold) {
        String tripTimeStr = tripTime.split(" ")[4];
        int tripTimeHour = Integer.parseInt(tripTimeStr.split(":")[0]);
        int tripTimeMinute = Integer.parseInt(tripTimeStr.split(":")[1]);

        int hourOfThreshold = 0;
        int minuteOfThreshold = 0;

        if (threshold.contains(":")) {
            hourOfThreshold = Integer.parseInt(threshold.split(":")[0]);
            minuteOfThreshold = Integer.parseInt(threshold.split(":")[1]);
        } else {
            hourOfThreshold = Integer.parseInt(threshold);
        }

        return tripTimeHour < hourOfThreshold ||
               (tripTimeHour == hourOfThreshold && tripTimeMinute <= minuteOfThreshold);
    }

}
