package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.friend.Friendship;
import seedu.address.model.friend.FriendshipStatus;
import seedu.address.model.user.User;

/**
 * A utility class containing a list of {@code Friendship} objects to be used in tests.
 */
public class TypicalFriendships {
    // list below should be private but style check error wil be caused if private
    // -> variable access
    public static final List<User> LISTUSERS = TypicalUsers.getTypicalUsers();
    public static final Friendship FRIENDSHIP_1 = new FriendshipBuilder().build();
    public static final Friendship FRIENDSHIP_2 = new FriendshipBuilder()
            .withFriendshipStatus(FriendshipStatus.ACCEPTED)
            .withFriendUser(LISTUSERS.get(2))
            .withMe(LISTUSERS.get(3))
            .withInitiatedBy(LISTUSERS.get(2))
            .build();
    public static final Friendship FRIENDSHIP_3 = new FriendshipBuilder()
            .withFriendshipStatus(FriendshipStatus.ACCEPTED)
            .withFriendUser(LISTUSERS.get(3))
            .withMe(LISTUSERS.get(2))
            .withInitiatedBy(LISTUSERS.get(2))
            .build();

    private TypicalFriendships() {} // prevents instantiation

    public static List<Friendship> getTypicalFriendships() {
        return new ArrayList<>(Arrays.asList(FRIENDSHIP_1, FRIENDSHIP_2, FRIENDSHIP_3));
    }

    public static List<User> getListUsers() {
        return LISTUSERS;
    }
}
