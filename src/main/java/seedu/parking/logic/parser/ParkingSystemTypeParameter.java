package seedu.parking.logic.parser;

/**
 * Packages the argument of the Parking System Type flag into an object.
 */
public class ParkingSystemTypeParameter {

    private final String parkingSystemType;

    public ParkingSystemTypeParameter(String parkingSystemType) {
        this.parkingSystemType = parkingSystemType;
    }

    public String getParkingSystemType() {
        return parkingSystemType;
    }
}

