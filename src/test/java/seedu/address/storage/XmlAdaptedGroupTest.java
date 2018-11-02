package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedGroup.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalGroups.NUS_BASKETBALL;
import static seedu.address.testutil.TypicalGroups.NUS_COMPUTING;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.shared.Description;
import seedu.address.model.shared.Title;
import seedu.address.testutil.Assert;



public class XmlAdaptedGroupTest {
    private static final String INVALID_TITLE = "@$#!&@"; // Invalid characters.
    private static final String INVALID_DESCRIPTION = ""; // Description must not be a blank string.

    private static final String VALID_TITLE = NUS_COMPUTING.getTitle().fullTitle;
    private static final String VALID_DESCRIPTION = NUS_COMPUTING.getDescription().statement;
    private static final XmlAdaptedMeeting VALID_MEETING = new XmlAdaptedMeeting(NUS_COMPUTING.getMeeting());
    private static final List<XmlAdaptedPerson> VALID_MEMBERS = NUS_COMPUTING.getMembersView()
            .stream().map(XmlAdaptedPerson::new).collect(Collectors.toList());

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        XmlAdaptedGroup group = new XmlAdaptedGroup(NUS_COMPUTING);
        assertEquals(NUS_COMPUTING, group.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        XmlAdaptedGroup group =
                new XmlAdaptedGroup(
                        INVALID_TITLE, VALID_DESCRIPTION, VALID_MEETING, VALID_MEMBERS);
        String expectedMessage = Title.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedGroup group =
                new XmlAdaptedGroup(
                        null, VALID_DESCRIPTION, VALID_MEETING, VALID_MEMBERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedGroup group =
                new XmlAdaptedGroup(
                        VALID_TITLE, INVALID_DESCRIPTION, VALID_MEETING, VALID_MEMBERS);

        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullDescription_success() throws Exception {
        XmlAdaptedGroup group =
                new XmlAdaptedGroup(
                        VALID_TITLE, null, VALID_MEETING, VALID_MEMBERS);
        UniquePersonList upl = new UniquePersonList();
        for (XmlAdaptedPerson p: VALID_MEMBERS) {
            upl.add(p.toModelType());
        }
        Group expectedGroup = new Group(new Title(VALID_TITLE), VALID_MEETING.toModelType(), upl);
        assertEquals(expectedGroup, group.toModelType());
    }

    @Test
    public void toModelType_nullMeeting_success() throws Exception {
        XmlAdaptedGroup group =
                new XmlAdaptedGroup(
                        VALID_TITLE, VALID_DESCRIPTION, null, VALID_MEMBERS);
        UniquePersonList upl = new UniquePersonList();
        for (XmlAdaptedPerson p: VALID_MEMBERS) {
            upl.add(p.toModelType());
        }
        Group expectedGroup = new Group(new Title(VALID_TITLE), new Description(VALID_DESCRIPTION), upl);
        assertEquals(expectedGroup, group.toModelType());
    }

    @Test
    public void equals() {
        XmlAdaptedGroup standardGroup = new XmlAdaptedGroup(NUS_COMPUTING);

        // same values -> returns true
        XmlAdaptedGroup xmlAdaptedGroupWithSameValues =
                new XmlAdaptedGroup(VALID_TITLE, VALID_DESCRIPTION, VALID_MEETING, VALID_MEMBERS);
        assertTrue(standardGroup.equals(xmlAdaptedGroupWithSameValues));

        // same object -> returns true
        assertTrue(standardGroup.equals(standardGroup));

        // null -> returns false
        assertFalse(standardGroup.equals(null));

        // different types -> returns false
        assertFalse(standardGroup.equals(new XmlAdaptedGroup()));

        // different title -> returns false
        assertFalse(standardGroup.equals(new XmlAdaptedGroup(NUS_BASKETBALL.getTitle().fullTitle,
                VALID_DESCRIPTION, VALID_MEETING, VALID_MEMBERS)));

        // different description -> returns false
        assertFalse(standardGroup.equals(new XmlAdaptedGroup(VALID_TITLE,
                NUS_BASKETBALL.getDescription().statement, VALID_MEETING, VALID_MEMBERS)));

        // different meeting -> returns false
        assertFalse(standardGroup.equals(new XmlAdaptedGroup(VALID_TITLE,
                VALID_DESCRIPTION, new XmlAdaptedMeeting(NUS_BASKETBALL.getMeeting()), VALID_MEMBERS)));

        // different members -> returns false
        assertFalse(standardGroup.equals(new XmlAdaptedGroup(VALID_TITLE,
                VALID_DESCRIPTION, VALID_MEETING,
                NUS_BASKETBALL.getMembersView().stream().map(XmlAdaptedPerson::new)
                .collect(Collectors.toList()))));

    }
}
