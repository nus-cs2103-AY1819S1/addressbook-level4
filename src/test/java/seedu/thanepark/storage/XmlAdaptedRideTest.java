package seedu.thanepark.storage;

import static org.junit.Assert.assertEquals;
import static seedu.thanepark.storage.XmlAdaptedRide.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.thanepark.testutil.TypicalRides.BIG;
import static seedu.thanepark.testutil.TypicalRides.RACHEL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.thanepark.commons.exceptions.IllegalValueException;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.testutil.Assert;

public class XmlAdaptedRideTest {
    private static final String INVALID_NAME = "!@#$%^&*()";
    private static final String INVALID_MAINTENANCE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_WAIT_TIME = "-123";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME_WITH_SYMBOLS = "R@chel";
    private static final String VALID_NAME = BIG.getName().toString();
    private static final String VALID_MAINTENANCE = String.valueOf(BIG.getDaysSinceMaintenance().getValue());
    private static final String VALID_WAIT_TIME = String.valueOf(BIG.getWaitingTime().getValue());
    private static final String VALID_ADDRESS = BIG.getZone().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BIG.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRideDetails_returnsRide() throws Exception {
        XmlAdaptedRide ride = new XmlAdaptedRide(BIG);
        assertEquals(BIG, ride.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedRide ride =
                new XmlAdaptedRide(INVALID_NAME, VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_validNameWithSymbols() throws Exception {
        XmlAdaptedRide ride = new XmlAdaptedRide(VALID_NAME_WITH_SYMBOLS, VALID_MAINTENANCE, VALID_WAIT_TIME,
                VALID_ADDRESS, VALID_TAGS);
        assertEquals(RACHEL, ride.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRide ride = new XmlAdaptedRide(null,
                VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_invalidMaintenance_throwsIllegalValueException() {
        XmlAdaptedRide ride =
                new XmlAdaptedRide(VALID_NAME, INVALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_nullMaintenance_throwsIllegalValueException() {
        XmlAdaptedRide ride = new XmlAdaptedRide(VALID_NAME, null, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Maintenance.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_invalidWaitingTime_throwsIllegalValueException() {
        XmlAdaptedRide ride =
                new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, INVALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_nullWaitingTime_throwsIllegalValueException() {
        XmlAdaptedRide ride = new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WaitTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedRide ride =
                new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, VALID_WAIT_TIME, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Zone.MESSAGE_ZONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedRide ride = new XmlAdaptedRide(VALID_NAME,
                VALID_MAINTENANCE, VALID_WAIT_TIME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Zone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ride::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRide ride =
                new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, ride::toModelType);
    }

}
