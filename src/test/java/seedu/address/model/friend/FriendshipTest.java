package seedu.address.model.friend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalFriendships.FRIENDSHIP_1;
import static seedu.address.testutil.TypicalFriendships.FRIENDSHIP_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.FriendshipBuilder;

public class FriendshipTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        // same values -> returns true
        Friendship f1 = new FriendshipBuilder(FRIENDSHIP_1).build();
        assertTrue(FRIENDSHIP_1.equals(f1));

        // same object -> returns true
        assertTrue(FRIENDSHIP_1.equals(FRIENDSHIP_1));

        // null -> returns false
        assertFalse(FRIENDSHIP_1.equals(null));

        // different type -> returns false
        assertFalse(FRIENDSHIP_1.equals(5));

        // different restaurant -> returns false
        assertFalse(FRIENDSHIP_1.equals(FRIENDSHIP_2));

        // different friendshipStatus -> returns true
        // it is not possible for two friendships with the same two Users in the friendship to
        // have different friendship statuses. This is checked in the addFriend command and tests.
        Friendship editedFriendship = new FriendshipBuilder(FRIENDSHIP_1)
                .withFriendshipStatus(FRIENDSHIP_2.getFriendshipStatus()).build();
        assertTrue(FRIENDSHIP_1.equals(editedFriendship));

        // different friendUser -> returns false
        editedFriendship = new FriendshipBuilder(FRIENDSHIP_1)
                .withFriendUser(FRIENDSHIP_2.getFriendUser()).build();
        assertFalse(FRIENDSHIP_1.equals(editedFriendship));

        // different initiatedBy User -> returns true as not the check for equality
        // it is not possible for two friendships with the same two Users in the friendship to
        // have different initiatedBy users. This is checked in the addFriend command and tests.
        editedFriendship = new FriendshipBuilder(FRIENDSHIP_1)
                .withInitiatedBy(FRIENDSHIP_2.getInitiatedBy()).build();
        assertTrue(FRIENDSHIP_1.equals(editedFriendship));

        // different me User -> returns false
        editedFriendship = new FriendshipBuilder(FRIENDSHIP_1)
                .withMe(FRIENDSHIP_2.getMe()).build();
        assertFalse(FRIENDSHIP_1.equals(editedFriendship));
    }
}
