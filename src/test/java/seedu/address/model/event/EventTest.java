package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPETITION;
import static seedu.address.testutil.TypicalEvents.BLOOD;
import static seedu.address.testutil.TypicalEvents.YOUTH;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        event.getTags().remove(0);
    }

    @Test
    public void isValidStartAndEndDate() {
        // BLOOD startDate - 02-10-2018
        // BLOOD endDate - 05-10-2018

        // startDate before endDate -> returns true
        assertTrue(BLOOD.isValidStartAndEndDate(BLOOD.getStartDate(), BLOOD.getEndDate()));

        // startDate same as endDate -> returns true
        Event editedBlood = new EventBuilder(BLOOD).withStartDate(BLOOD.getEndDate().toString()).build();
        assertTrue(editedBlood.isValidStartAndEndDate(editedBlood.getStartDate(), editedBlood.getEndDate()));

        // startDate more than endDate -> returns IllegalArgumentException
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventBuilder(BLOOD)
                                                                        .withStartDate("06-10-2018").build());
    }

    @Test
    public void isValidStartAndEndTime() {
        // BLOOD startTime - 11:30
        // BLOOD endTime - 17:30

        // startTime before endTime -> returns true
        assertTrue(BLOOD.isValidStartAndEndTime(BLOOD.getStartTime(), BLOOD.getEndTime()));

        // startTime same as endTime -> returns true
        Event editedBlood = new EventBuilder(BLOOD).withStartTime(BLOOD.getEndTime().toString()).build();
        assertTrue(editedBlood.isValidStartAndEndTime(editedBlood.getStartTime(), editedBlood.getEndTime()));

        // startTime more than endTime -> returns IllegalArgumentException
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventBuilder(BLOOD)
                .withStartTime("18:30").build());
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(BLOOD.isSameEvent(BLOOD));

        // null -> returns false
        assertFalse(BLOOD.isSameEvent(null));

        // different location, start date and end date -> returns false
        Event editedBlood = new EventBuilder(BLOOD).withLocation(VALID_LOCATION_YOUTH)
                .withStartDate(VALID_START_DATE_YOUTH).withEndDate(VALID_END_DATE_YOUTH).build();
        assertFalse(BLOOD.isSameEvent(editedBlood));

        // different name -> returns false
        editedBlood = new EventBuilder(BLOOD).withName(VALID_NAME_YOUTH).build();
        assertFalse(BLOOD.isSameEvent(editedBlood));

        // same name, same location, same start and end date, different attributes -> returns true
        editedBlood = new EventBuilder(BLOOD).withDescription(VALID_DESCRIPTION_YOUTH)
                .withStartTime(VALID_START_TIME_YOUTH).withEndTime(VALID_END_TIME_YOUTH)
                .withTags(VALID_TAG_COMPETITION).build();
        assertTrue(BLOOD.isSameEvent(editedBlood));

        // same name, same email, different attributes -> returns true
        editedBlood = new EventBuilder(BLOOD).withLocation(VALID_LOCATION_YOUTH)
                .withDescription(VALID_DESCRIPTION_YOUTH).withTags(VALID_TAG_COMPETITION).build();
        assertTrue(BLOOD.isSameEvent(editedBlood));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event bloodCopy = new EventBuilder(BLOOD).build();
        assertTrue(BLOOD.equals(bloodCopy));

        // same object -> returns true
        assertTrue(BLOOD.equals(BLOOD));

        // null -> returns false
        assertFalse(BLOOD.equals(null));

        // different type -> returns false
        assertFalse(BLOOD.equals(5));

        // different event -> returns false
        assertFalse(BLOOD.equals(YOUTH));

        // different name -> returns false
        Event editedBlood = new EventBuilder(BLOOD).withName(VALID_NAME_YOUTH).build();
        assertFalse(BLOOD.equals(editedBlood));

        // different location -> returns false
        editedBlood = new EventBuilder(BLOOD).withLocation(VALID_LOCATION_YOUTH).build();
        assertFalse(BLOOD.equals(editedBlood));

        // different startDate -> returns false
        editedBlood = new EventBuilder(BLOOD).withStartDate(VALID_START_DATE_YOUTH).build();
        assertFalse(BLOOD.equals(editedBlood));

        // different startTime -> returns false
        editedBlood = new EventBuilder(BLOOD).withStartTime(VALID_START_TIME_YOUTH).build();
        assertFalse(BLOOD.equals(editedBlood));

        // different endTime -> returns false
        editedBlood = new EventBuilder(BLOOD).withEndTime(VALID_END_TIME_YOUTH).build();
        assertFalse(BLOOD.equals(editedBlood));

        // different endTime -> returns false
        editedBlood = new EventBuilder(BLOOD).withDescription(VALID_DESCRIPTION_YOUTH).build();
        assertFalse(BLOOD.equals(editedBlood));

        // different tags -> returns false
        editedBlood = new EventBuilder(BLOOD).withTags(VALID_TAG_COMPETITION).build();
        assertFalse(BLOOD.equals(editedBlood));
    }
}
