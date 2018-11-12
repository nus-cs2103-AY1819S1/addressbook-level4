package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedFriendship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedFriendship.USER_NOT_EXIST_FRIENDSHIP_FORMAT;
import static seedu.address.storage.XmlAdaptedFriendship.WRONG_INITIATION_MESSAGE_FORMAT;
import static seedu.address.storage.XmlAdaptedFriendship.WRONG_STATUS_FORMAT;
import static seedu.address.testutil.TypicalFriendships.FRIENDSHIP_1;
import static seedu.address.testutil.TypicalFriendships.FRIENDSHIP_2;
import static seedu.address.testutil.TypicalUsers.getTypicalUserData;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.friend.Friendship;
import seedu.address.testutil.Assert;

public class XmlAdaptedFriendshipTest {
    private static final String INVALID_FRIENDUSER = "meow";
    private static final String INVALID_INITIATEDBY = "meow";
    private static final String INVALID_ME = "wot";
    private static final String INVALID_FRIENDSHIPSTATUS = "Wrong";

    private static final String VALID_FRIENDUSER = FRIENDSHIP_1.getFriendUser()
            .getUsername().toString();
    private static final String VALID_ME = FRIENDSHIP_1.getMe()
            .getUsername().toString();
    private static final String VALID_INITIATED_BY = FRIENDSHIP_1.getInitiatedBy()
            .getUsername().toString();
    private static final String VALID_FRIENDSHIP_STATUS = FRIENDSHIP_1.getFriendshipStatus()
            .toString();
    private static final String THIRD_PARTY_INITIATED_BY = FRIENDSHIP_2.getInitiatedBy()
            .getUsername().toString();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    @Test
    public void toModelType_validFriendshipDetails_returnsFriendship() throws Exception {
        XmlAdaptedFriendship friendship = new XmlAdaptedFriendship(FRIENDSHIP_1);
        assertEquals(FRIENDSHIP_1, friendship.toModelType(getTypicalUserData()
                .getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_invalidFriendUser_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship =
                new XmlAdaptedFriendship(INVALID_FRIENDUSER, VALID_INITIATED_BY,
                        VALID_ME, VALID_FRIENDSHIP_STATUS);
        String expectedMessage = String.format(USER_NOT_EXIST_FRIENDSHIP_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_invalidMe_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship =
                new XmlAdaptedFriendship(VALID_FRIENDUSER, VALID_INITIATED_BY,
                        INVALID_ME, VALID_FRIENDSHIP_STATUS);
        String expectedMessage = String.format(USER_NOT_EXIST_FRIENDSHIP_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_invalidInitiatedBy_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship =
                new XmlAdaptedFriendship(VALID_FRIENDUSER, INVALID_INITIATEDBY,
                        VALID_ME, VALID_FRIENDSHIP_STATUS);
        String expectedMessage = String.format(USER_NOT_EXIST_FRIENDSHIP_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_invalidFriendshipStatus_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship =
                new XmlAdaptedFriendship(VALID_FRIENDUSER, VALID_INITIATED_BY,
                        VALID_ME, INVALID_FRIENDSHIPSTATUS);
        String expectedMessage = String.format(WRONG_STATUS_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_nullFriendUser_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship = new XmlAdaptedFriendship(
                null, VALID_INITIATED_BY, VALID_ME, VALID_FRIENDSHIP_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_nullMe_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship = new XmlAdaptedFriendship(
                VALID_FRIENDUSER, VALID_INITIATED_BY, null, VALID_FRIENDSHIP_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_nullInitiatedBy_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship = new XmlAdaptedFriendship(
                VALID_FRIENDUSER, null, VALID_ME, VALID_FRIENDSHIP_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_nullFriendshipStatus_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship = new XmlAdaptedFriendship(
                VALID_FRIENDUSER, VALID_INITIATED_BY, VALID_ME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }

    @Test
    public void toModelType_wrongInitiatedBy_throwsIllegalValueException() throws Exception {
        XmlAdaptedFriendship friendship = new XmlAdaptedFriendship(
                VALID_FRIENDUSER, THIRD_PARTY_INITIATED_BY, VALID_ME, VALID_FRIENDSHIP_STATUS);
        String expectedMessage = String.format(WRONG_INITIATION_MESSAGE_FORMAT,
                Friendship.class.getSimpleName());
        thrown.expect(IllegalValueException.class);
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
                (Assert.VoidCallable) friendship.toModelType(getTypicalUserData().getUsernameUserHashMap()));
    }
}
