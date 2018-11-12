package seedu.address.model.friend;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Represents a Friendship in the address book.
 * Guarantees: details are not null.
 */
public class Friendship {
    private FriendshipStatus friendshipStatus;
    private final User friendUser;
    private final User me;
    private final User initiatedBy;

    /**
     * Constructor for friendship.
     * Creates friendship with a User friendUser, User initiatedBy and User me.
     * initiatedBy should be either the friendUser or me.
     * @param friendUser
     * @param initiatedBy
     * @param me
     */
    public Friendship(User friendUser, User initiatedBy, User me) {
        requireAllNonNull(friendUser, initiatedBy);
        this.friendUser = friendUser;
        this.friendshipStatus = FriendshipStatus.PENDING;
        this.me = me;
        this.initiatedBy = initiatedBy;
    }

    /**
     * Constructor for friendship.
     * Creates friendship with a User friendUser, User initiatedBy, User me and friendshipStatus.
     * initiatedBy should be either the friendUser or me.
     * @param friendUser
     * @param initiatedBy
     * @param me
     * @param friendshipStatus
     */
    public Friendship(User friendUser, User initiatedBy, User me, FriendshipStatus friendshipStatus) {
        requireAllNonNull(friendUser, initiatedBy, friendshipStatus);
        this.friendUser = friendUser;
        this.friendshipStatus = friendshipStatus;
        this.initiatedBy = initiatedBy;
        this.me = me;
    }

    /**
     * Changes the friendshipStatus to ACCEPTED.
     */
    public void changeFriendshipStatus() {
        this.friendshipStatus = FriendshipStatus.ACCEPTED;
    }

    /**
     * Returns the friendshipStatus of the friendship.
     * @return friendshipStatus
     */
    public FriendshipStatus getFriendshipStatus() {
        return this.friendshipStatus;
    }

    /**
     * Returns the me User in the friendship; me is User is who the friendship is stored under.
     * @return me User
     */
    public User getMe() {
        return this.me;
    }

    /**
     * Returns the friendUser in the friendship; friendUser is the User in the friendship
     * who the friendship is not stored under.
     * @return friendUser User
     */
    public User getFriendUser() {
        return this.friendUser;
    }

    /**
     * Returns the User who initiated the friendship.
     * initiatedBy user has to either be User me or User friendUser.
     * @return initiatedBy User
     */
    public User getInitiatedBy() {
        return this.initiatedBy;
    }

    /**
     * Returns User me's username
     * @return username of me User
     */
    public Username getMyUsername() {
        return me.getUsername();
    }

    /**
     * Returns User friendUser's username
     * @return username of friendUser User
     */
    public Username getFriendUsername() {
        return friendUser.getUsername();
    }

    @Override
    public boolean equals(Object otherFriendship) {
        if (otherFriendship == this) {
            return true;
        }

        if (!(otherFriendship instanceof Friendship)) {
            return false;
        }

        Friendship other = (Friendship) otherFriendship;

        return other != null
                && other.getFriendUser().isSameUser(this.friendUser)
                && other.getMe().isSameUser(this.me);
    }
}
