package seedu.parking.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Container class to hold Car park JSON data.
 */
public class CarparkJson {
    //CHECKSTYLE.OFF: MemberNameCheck
    public final String short_term_parking;
    public final String y_coord;
    public final String car_park_type;
    public final String x_coord;
    public final String free_parking;
    public final String night_parking;
    public final String address;
    public final String car_park_no;
    public final String type_of_parking_system;
    //CHECKSTYLE.ON: MemberNameCheck

    private List<String> jsonData;

    private CarparkJson(String... data) {
        short_term_parking = data[0];
        car_park_type = data[1];
        y_coord = data[2];
        x_coord = data[3];
        free_parking = data[4];
        night_parking = data[5];
        address = data[6];
        car_park_no = data[7];
        type_of_parking_system = data[8];
    }

    /**
     * Adds the JSON data into a list
     * @param data Contains total lots and lots availability numbers.
     */
    public void addOn(String... data) {
        jsonData = new ArrayList<>();

        jsonData.add(address);
        jsonData.add(car_park_no);
        jsonData.add(car_park_type);
        jsonData.add(x_coord + ", " + y_coord);
        jsonData.add(free_parking);
        jsonData.add(data[1]);
        jsonData.add(night_parking);
        jsonData.add(short_term_parking);
        jsonData.add(data[0]);
        jsonData.add(type_of_parking_system);
    }

    public String getNumber() {
        return car_park_no;
    }

    public List<String> getJsonData() {
        return jsonData;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CarparkJson)) {
            return false;
        }

        CarparkJson otherCarparkJson = (CarparkJson) other;
        return short_term_parking.equals(otherCarparkJson.short_term_parking)
                && y_coord.equals(otherCarparkJson.y_coord)
                && car_park_type.equals(otherCarparkJson.car_park_type)
                && x_coord.equals(otherCarparkJson.x_coord)
                && free_parking.equals(otherCarparkJson.free_parking)
                && night_parking.equals(otherCarparkJson.night_parking)
                && address.equals(otherCarparkJson.address)
                && car_park_no.equals(otherCarparkJson.car_park_no)
                && type_of_parking_system.equals(otherCarparkJson.type_of_parking_system);
    }

    @Override
    public int hashCode() {
        return Objects.hash(short_term_parking, y_coord, car_park_type, x_coord, free_parking, night_parking,
                address, car_park_no, type_of_parking_system);
    }
}
