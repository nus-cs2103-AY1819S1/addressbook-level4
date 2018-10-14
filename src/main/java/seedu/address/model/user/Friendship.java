package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Friendship in the address book.
 * Guarantees: details are not null.
 */
public class Friendship {
    private FriendshipStatus friendshipStatus;
    private final User friendUser;
    private final User me;
    private final User initiatedBy;

    public Friendship(User friendUser, User initiatedBy, User me) {
        requireAllNonNull(friendUser, initiatedBy);
        this.friendUser = friendUser;
        this.friendshipStatus = FriendshipStatus.PENDING;
        this.me = me;
        this.initiatedBy = initiatedBy;
    }

    public Friendship(User friendUser, User initiatedBy, User me, FriendshipStatus friendshipStatus) {
        requireAllNonNull(friendUser, initiatedBy, friendshipStatus);
        this.friendUser = friendUser;
        this.friendshipStatus = friendshipStatus;
        this.initiatedBy = initiatedBy;
        this.me = me;
    }

    public void changeFriendshipStatus() {
        this.friendshipStatus = FriendshipStatus.ACCEPTED;
    }

    public FriendshipStatus getFrienshipStatus() {
        return this.friendshipStatus;
    }

    public User getMe() {
        return this.me;
    }

    public User getFriendUser() {
        return this.friendUser;
    }

    public User getInitiatedBy() {
        return this.initiatedBy;
    }

    public Username getMyUsername() {
        return me.getUsername();
    }

    public Username getFriendUsername() {
        return friendUser.getUsername();
    }

    @Override
    public boolean equals(Object otherFriendship) {
        if (otherFriendship == this) {
            return true;
        }

        Friendship other = (Friendship) otherFriendship;

        return other != null
                && other.getFriendUser().isSameUser(this.friendUser)
                && other.getMe().isSameUser(this.me);
    }
}
