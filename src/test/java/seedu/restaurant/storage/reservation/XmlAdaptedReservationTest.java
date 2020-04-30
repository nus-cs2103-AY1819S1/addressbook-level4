package seedu.restaurant.storage.reservation;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.storage.elements.XmlAdaptedReservation.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.restaurant.testutil.reservation.TypicalReservations.BILLY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.storage.XmlAdaptedTag;
import seedu.restaurant.storage.elements.XmlAdaptedReservation;
import seedu.restaurant.testutil.Assert;

//@@author m4dkip
public class XmlAdaptedReservationTest {
    private static final String INVALID_NAME = "B@ller";
    private static final String INVALID_PAX = "-6";
    private static final String INVALID_DATE = "hi im a date";
    private static final String INVALID_TIME = "hi im a time";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BILLY.getName().toString();
    private static final String VALID_PAX = BILLY.getPax().toString();
    private static final String VALID_DATE = BILLY.getDate().toString();
    private static final String VALID_TIME = BILLY.getTime().toString();
    private static final String VALID_REMARK = BILLY.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BILLY.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validReservationDetails_returnsReservation() throws Exception {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(BILLY);
        assertEquals(BILLY, reservation.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(INVALID_NAME, VALID_PAX, VALID_DATE, VALID_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(null, VALID_PAX, VALID_DATE, VALID_TIME,
                VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidPax_throwsIllegalValueException() {
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(VALID_NAME, INVALID_PAX, VALID_DATE, VALID_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Pax.MESSAGE_PAX_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullPax_throwsIllegalValueException() {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(VALID_NAME, null, VALID_DATE, VALID_TIME,
                VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Pax.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(VALID_NAME, VALID_PAX, INVALID_DATE, VALID_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(VALID_NAME, VALID_PAX, null, VALID_TIME,
                VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(VALID_NAME, VALID_PAX, VALID_DATE, INVALID_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(VALID_NAME, VALID_PAX, VALID_DATE, null,
                VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(VALID_NAME, VALID_PAX, VALID_DATE, VALID_TIME, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, reservation::toModelType);
    }

}
