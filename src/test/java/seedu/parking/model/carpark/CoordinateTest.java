package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class CoordinateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Coordinate(null));
    }

    @Test
    public void constructor_invalidCoordinate_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Coordinate(invalidEmail));
    }

    @Test
    public void isValidCoordinate() {
        // null coordinate
        Assert.assertThrows(NullPointerException.class, () -> Coordinate.isValidCoordinate(null));

        // blank coordinate
        assertFalse(Coordinate.isValidCoordinate("")); // empty string
        assertFalse(Coordinate.isValidCoordinate(" ")); // spaces only

        // missing parts
        assertFalse(Coordinate.isValidCoordinate("81695.6404")); // missing y-coordinate
        assertFalse(Coordinate.isValidCoordinate("81695.6404 19937.3833")); // missing ',' symbol
        assertFalse(Coordinate.isValidCoordinate("19937.3833")); // missing x-coordinate

        // invalid parts
        assertFalse(Coordinate.isValidCoordinate("81695@-")); // invalid coordinate
        assertFalse(Coordinate.isValidCoordinate("81695.6404_19937.3833")); // underscore in coordinate
        assertFalse(Coordinate.isValidCoordinate("81695 .6404, 19937.3833")); // spaces in x-coordinate
        assertFalse(Coordinate.isValidCoordinate("81695.6404,19937.3833")); // no spaces in x-coordinate
        assertFalse(Coordinate.isValidCoordinate(" 81695.6404,19937.3833")); // leading space
        assertFalse(Coordinate.isValidCoordinate("81695.6404,19937.3833 ")); // trailing space
        assertFalse(Coordinate.isValidCoordinate("81695.6404,,19937.3833")); // double ',' symbol
        assertFalse(Coordinate.isValidCoordinate("816,95.6404,19937.3833")); // ',' symbol in x-coordinate
        assertFalse(Coordinate.isValidCoordinate("81695.6404,19937.38,33")); // ',' symbol in y-coordinate
        assertFalse(Coordinate.isValidCoordinate("81695.6404,.19937.3833")); // y-coordinate starts with a period
        assertFalse(Coordinate.isValidCoordinate("81695.6404,19937.3833.")); // y-coordinate ends with a period
        assertFalse(Coordinate.isValidCoordinate("81695.6404,-19937.3833")); // y-coordinate starts with a hyphen
        assertFalse(Coordinate.isValidCoordinate("81695.6404,19937.3833-")); // y-coordinate ends with a hyphen
        assertFalse(Coordinate.isValidCoordinate("!#$%&'*+/=?`{|}~^.-, 19937.3833")); // special characters x-coordinate
        assertFalse(Coordinate.isValidCoordinate("81695.6404, !#$%&'*+/=?`{|}~^.-")); // special characters y-coordinate
        assertFalse(Coordinate.isValidCoordinate("abcdef.ghjk, qwerty.uiop")); // alphabets
        assertFalse(Coordinate.isValidCoordinate("81+9e!, 1993e.3833")); // mix of alphanumeric and special characters

        // valid coordinate
        assertTrue(Coordinate.isValidCoordinate("81695.6404, 19937.3833"));
        assertTrue(Coordinate.isValidCoordinate("816, 199")); // minimal
        assertTrue(Coordinate.isValidCoordinate("0.6404, 0.3833")); // decimal only
        assertTrue(Coordinate.isValidCoordinate("8169541231.64044124123, 199371231251.38331231241")); // long all
        assertTrue(Coordinate.isValidCoordinate("8169541231.6404, 199371231251.3833")); // long number
    }
}
