package seedu.parking.logic.parser;

/**
 * Packages the argument of the Car park Type flag into an object.
 */
public class CarparkTypeParameter {

    private final String carparkType;

    public CarparkTypeParameter(String carparkType) {
        this.carparkType = carparkType;
    }

    public String getCarparkType() {
        return carparkType;
    }
}
