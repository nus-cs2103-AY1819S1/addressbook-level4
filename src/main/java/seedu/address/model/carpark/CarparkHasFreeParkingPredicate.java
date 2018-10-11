package seedu.address.model.carpark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests that a {@code Carpark} has free parking.
 */
public class CarparkHasFreeParkingPredicate implements Predicate<Carpark> {
    private final String day;
    private final String startTime;
    private final String endTime;

    public CarparkHasFreeParkingPredicate(String day, String startTime, String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean test(Carpark carpark) {

        // To Be Implemented: Parsing of timing
        String timePeriod = carpark.getFreeParking().value;
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

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
        //|| (other instanceof CarparkHasNightParkingPredicate // instanceof handles nulls
        //&& keyword.equals(((CarparkHasNightParkingPredicate) other).keyword)); // state check
    }

}


