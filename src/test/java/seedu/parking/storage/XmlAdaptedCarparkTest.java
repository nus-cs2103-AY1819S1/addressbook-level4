package seedu.parking.storage;

import static org.junit.Assert.assertEquals;
import static seedu.parking.storage.XmlAdaptedCarpark.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.parking.testutil.TypicalCarparks.BRAVO;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.parking.commons.exceptions.IllegalValueException;
import seedu.parking.model.carpark.Address;
import seedu.parking.model.carpark.CarparkNumber;
import seedu.parking.model.carpark.CarparkType;
import seedu.parking.model.carpark.Coordinate;
import seedu.parking.model.carpark.FreeParking;
import seedu.parking.model.carpark.LotsAvailable;
import seedu.parking.model.carpark.NightParking;
import seedu.parking.model.carpark.PostalCode;
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;
import seedu.parking.testutil.Assert;

public class XmlAdaptedCarparkTest {
    private static final String INVALID_CARPARK_NO = "SK@39";
    private static final String INVALID_CARPARK_TYPE = " ";
    private static final String INVALID_COORDINATE = "asd123";
    private static final String INVALID_FREE_PARKING = " ";
    private static final String INVALID_LOTS_AVAILABLE = "asd";
    private static final String INVALID_NIGHT_PARKING = " ";
    private static final String INVALID_POSTAL_CODE = "1234567";
    private static final String INVALID_SHORT_TERM = " ";
    private static final String INVALID_TOTAL_LOTS = "asd";
    private static final String INVALID_TYPE_OF_PARKING = " ";
    private static final String INVALID_ADDRESS = " ";

    private static final String VALID_CARPARK_NO = BRAVO.getCarparkNumber().toString();
    private static final String VALID_CARPARK_TYPE = BRAVO.getCarparkType().toString();
    private static final String VALID_COORDINATE = BRAVO.getCoordinate().toString();
    private static final String VALID_FREE_PARKING = BRAVO.getFreeParking().toString();
    private static final String VALID_LOTS_AVAILABLE = BRAVO.getLotsAvailable().toString();
    private static final String VALID_NIGHT_PARKING = BRAVO.getNightParking().toString();
    private static final String VALID_POSTAL_CODE = BRAVO.getPostalCode().toString();
    private static final String VALID_SHORT_TERM = BRAVO.getShortTerm().toString();
    private static final String VALID_TOTAL_LOTS = BRAVO.getTotalLots().toString();
    private static final String VALID_TYPE_OF_PARKING = BRAVO.getTypeOfParking().toString();
    private static final String VALID_ADDRESS = BRAVO.getAddress().toString();

    private static final List<XmlAdaptedTag> VALID_TAGS = BRAVO.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(BRAVO);
        assertEquals(BRAVO, carpark.toModelType());
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(null, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, INVALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullCarparkNo_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, null, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CarparkNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullCarparkType_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, null,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CarparkType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullCoordinate_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                null, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Coordinate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullFreeParking_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, null, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, FreeParking.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullLotsAvailable_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, null, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LotsAvailable.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullNightParking_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, null, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NightParking.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullShortTerm_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, null,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ShortTerm.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullTotalLots_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                null, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TotalLots.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullTypeOfParking_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, null, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TypeOfParking.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_nullPostalCode_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PostalCode.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(INVALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidCarparkNo_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, INVALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = CarparkNumber.MESSAGE_CAR_NUM_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidCarparkType_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, INVALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = CarparkType.MESSAGE_CAR_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidCoordinate_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                INVALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = Coordinate.MESSAGE_COORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidFreeParking_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, INVALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = FreeParking.MESSAGE_FREE_PARK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidLotsAvailable_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, INVALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = LotsAvailable.MESSAGE_LOTS_AVAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidNightParking_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, INVALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = NightParking.MESSAGE_NIGHT_PARK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidShortTerm_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, INVALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = ShortTerm.MESSAGE_SHORT_TERM_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidTotalLots_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                INVALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = TotalLots.MESSAGE_TOTAL_LOTS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidTypeOfParking_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, INVALID_TYPE_OF_PARKING, VALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = TypeOfParking.MESSAGE_TYPE_PARK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }

    @Test
    public void toModelType_invalidPostalCode_throwsIllegalValueException() {
        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_ADDRESS, VALID_CARPARK_NO, VALID_CARPARK_TYPE,
                VALID_COORDINATE, VALID_FREE_PARKING, VALID_LOTS_AVAILABLE, VALID_NIGHT_PARKING, VALID_SHORT_TERM,
                VALID_TOTAL_LOTS, VALID_TYPE_OF_PARKING, INVALID_POSTAL_CODE, VALID_TAGS);
        String expectedMessage = PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
    }
}
