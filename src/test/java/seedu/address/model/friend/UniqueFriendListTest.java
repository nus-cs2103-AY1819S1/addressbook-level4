package seedu.address.model.friend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalFriendships.FRIENDSHIP_1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.FriendshipBuilder;

/* Note to graders:
This testing file does not account for the addition of duplicate friendships or deletion of
friendships that do not exist. This is because, it is accounted for in the command classes and tests.
 */

public class UniqueFriendListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueFriendList uniqueFriendList = new UniqueFriendList();

    @Test
    public void contains_nullFriendship_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFriendList.contains(null);
    }

    @Test
    public void contains_friendshipNotInList_returnsFalse() {
        assertFalse(uniqueFriendList.contains(FRIENDSHIP_1));
    }

    @Test
    public void contains_friendshipInList_returnsTrue() {
        uniqueFriendList.add(FRIENDSHIP_1);
        assertTrue(uniqueFriendList.contains(FRIENDSHIP_1));
    }

    @Test

    public void containsFriendshipWithSameIdentityFieldsInListReturnsTrue() {
        uniqueFriendList.add(FRIENDSHIP_1);
        Friendship friendship = new FriendshipBuilder(FRIENDSHIP_1)
                .build();
        assertTrue(uniqueFriendList.contains(friendship));
    }

    @Test
    public void add_nullFriendship_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFriendList.add(null);
    }

    @Test
    public void remove_nullFriendship_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFriendList.remove(null);
    }

    @Test
    public void remove_existingFriendship_removesFriendship() {
        uniqueFriendList.add(FRIENDSHIP_1);
        uniqueFriendList.remove(FRIENDSHIP_1);
        UniqueFriendList expectedUniqueFriendList = new UniqueFriendList();
        assertEquals(expectedUniqueFriendList, uniqueFriendList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueFriendList.asUnmodifiableObservableList().remove(0);
    }
}
