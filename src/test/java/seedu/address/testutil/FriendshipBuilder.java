package seedu.address.testutil;

import seedu.address.model.friend.Friendship;
import seedu.address.model.friend.FriendshipStatus;
import seedu.address.model.user.User;

/**
 * A utility class to help with building User objects.
 */
public class FriendshipBuilder {
    private static final FriendshipStatus DEFAULT_FRIENDSHIP_STATUS = FriendshipStatus.PENDING;
    private static final User DEFAULT_FRIEND_USER = TypicalUsers.getTypicalUsers().get(0);
    private static final User DEFAULT_ME = TypicalUsers.getTypicalUsers().get(1);
    private static final User DEFAULT_INITIATED_BY = TypicalUsers.getTypicalUsers().get(0);

    private FriendshipStatus friendshipStatus;
    private User friendUser;
    private User me;
    private User initiatedBy;

    public FriendshipBuilder() {
        friendshipStatus = DEFAULT_FRIENDSHIP_STATUS;
        friendUser = DEFAULT_FRIEND_USER;
        me = DEFAULT_ME;
        initiatedBy = DEFAULT_INITIATED_BY;
    }

    /**
     * Initializes the FriendshipBuilder with the data of {@code friendshipToCopy}.
     */
    public FriendshipBuilder(Friendship friendshipToCopy) {
        friendshipStatus = friendshipToCopy.getFriendshipStatus();
        me = friendshipToCopy.getMe();
        friendUser = friendshipToCopy.getFriendUser();
        initiatedBy = friendshipToCopy.getInitiatedBy();
    }

    /**
     * Sets the {@code FriendshipStatus} of the {@code Friendship} that we are building.
     */
    public FriendshipBuilder withFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
        return this;
    }

    /**
     * Sets the {@code me} of the {@code Friendship} that we are building.
     */
    public FriendshipBuilder withMe(User me) {
        this.me = me;
        return this;
    }

    /**
     * Sets the {@code friendUser} of the {@code Friendship} that we are building.
     */
    public FriendshipBuilder withFriendUser(User friendUser) {
        this.friendUser = friendUser;
        return this;
    }

    /**
     * Sets the {@code initiatedBy} of the {@code Friendship} that we are building.
     */
    public FriendshipBuilder withInitiatedBy(User initiatedBy) {
        this.initiatedBy = initiatedBy;
        return this;
    }

    /**
     * Builds friendship with me, friendUser, friendshipStatus, initiatedBy
     */
    public Friendship build() {
        Friendship toReturn = new Friendship(friendUser, initiatedBy, me, friendshipStatus);
        return toReturn;
    }
}
