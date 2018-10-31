package seedu.parking.model.carpark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import seedu.parking.commons.util.StringUtil;

/**
 * Tests that a {@code Carpark} met all the filtering criteria.
 */
public class CarparkFilteringPredicate implements Predicate<Carpark> {

    private final List<String> locationKeywords;
    private final List<String> flagList;

    public CarparkFilteringPredicate(List<String> locationKeywords, List<String> flagList) {
        this.locationKeywords = locationKeywords;
        this.flagList = flagList;
    }

    // Todo: Javadoc Comment
    /**
     * Checks if the car park has free parking from the given starting to ending time on the specified day.
     */
    private boolean checkFreeParking(String day, String startTime, String endTime, String timePeriod) {
        boolean hasFreeParkingTiming = !timePeriod.equals("NO");
        boolean hasDay = false;
        boolean afterStart = false;
        boolean beforeEnd = false;

        try {
            if (hasFreeParkingTiming) {

                // Check if timePeriod contains day
                hasDay = timePeriod.contains(day);

                // Split time period using spacing
                String[] timePeriodArray = timePeriod.split("\\s+");
                // Get the last element of the array
                String time = timePeriodArray[timePeriodArray.length - 1];
                // Split the string into half by hyphen
                String[] startAndEndTime = time.split("-");

                SimpleDateFormat dateFormat1 = new SimpleDateFormat("hhaa");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh.mmaa");

                Date start = dateFormat1.parse(startAndEndTime[0]);
                Date end = dateFormat2.parse(startAndEndTime[1]);

                // Input time of user can only be of dateFormat2
                Date inputStart = dateFormat2.parse(startTime);
                Date inputEnd = dateFormat2.parse(endTime);
                afterStart = inputStart.after(start) || inputStart.equals(start);
                beforeEnd = inputEnd.before(end) || inputEnd.equals(end);
            }
        } catch (ParseException e) {
            System.out.println("parse exception");
        }

        return hasFreeParkingTiming && hasDay && afterStart && beforeEnd;
    }

    /**
     * Checks if the car park is of the specified car park type.
     */
    private boolean checkCarParkType(String selectedCarparkType, String carparkType) {
        switch (selectedCarparkType) {
        case "SURFACE":
            return carparkType.contains("SURFACE");

        case "MULTISTOREY":
            return carparkType.contains("MULTI-STOREY");

        case "BASEMENT":
            return carparkType.contains("BASEMENT");

        case "MECHANISED":
            return carparkType.contains("MECHANISED");

        case "COVERED":
            return carparkType.contains("COVERED");

        default:
            // should catch invalid input instead (to be implemented)
            return carparkType.contains("CAR PARK");
        }
    }

    @Override
    public boolean test(Carpark carpark) {

        boolean correctLocation = locationKeywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(carpark.getCarparkNumber().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(carpark.getAddress().toString(), keyword)
                                || StringUtil.containsPartialWordIgnoreCase(carpark.getCarparkNumber().toString(), keyword)
                                || StringUtil.containsPartialWordIgnoreCase(carpark.getAddress().toString(), keyword)
                );

        boolean collective = true;

        if (flagList.contains("n/")) {
            boolean hasNightParking = carpark.getNightParking().toString().equals("YES");

            collective = hasNightParking;
        }
        if (flagList.contains("f/")) {
            int index = flagList.indexOf("f/");

            // Can accept small letters too
            String day = flagList.get(index + 1).toUpperCase();
            String startTime = flagList.get(index + 2);
            String endTime = flagList.get(index + 3);

            String timePeriod = carpark.getFreeParking().toString();
            boolean hasFreeParking = checkFreeParking(day, startTime, endTime, timePeriod);

            collective = collective && hasFreeParking;
        }
        if (flagList.contains("ct/")) {
            int index2 = flagList.indexOf("ct/");

            String selectedCarparkType = flagList.get(index2 + 1).toUpperCase();

            String carparkType = carpark.getCarparkType().toString();
            boolean isCorrectType = checkCarParkType(selectedCarparkType, carparkType);

            collective = collective && isCorrectType;
        }

        return correctLocation && collective;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CarparkFilteringPredicate // instanceof handles nulls
                && locationKeywords.equals(((CarparkFilteringPredicate) other).locationKeywords)); // state check
    }

}
