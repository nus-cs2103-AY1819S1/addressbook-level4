package seedu.address.model.friend;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.Name;
import seedu.address.model.user.Email;
import seedu.address.model.user.Password;
import seedu.address.model.user.Phone;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;


public class UserFriendListTest {
    @Test
    public void listFriendRequestsTest() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        alice.addFriend(bob);
        UniqueFriendList uniqueFriendRequestListBob = new UniqueFriendList();
        uniqueFriendRequestListBob.add(new Friendship(alice, alice, bob));
        Assert.assertEquals(alice.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriendRequests() , uniqueFriendRequestListBob.asUnmodifiableObservableList());
    }

    @Test
    public void multipleFriendRequests() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        User carol = new User(new Username("Carol"), new Password("password"), new Name("Carol"),
                new Phone("2433"), new Email("carol@g.com"));
        bob.addFriend(alice);
        carol.addFriend(alice);
        UniqueFriendList uniqueFriendRequestListAlice = new UniqueFriendList();
        uniqueFriendRequestListAlice.add(new Friendship(bob, bob, alice));
        uniqueFriendRequestListAlice.add(new Friendship(carol, carol, alice));
        Assert.assertEquals(alice.getFriendRequests() , uniqueFriendRequestListAlice.asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(carol.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(alice.getFriends(), new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriends(), new UniqueFriendList().asUnmodifiableObservableList());
    }

    @Test
    public void listFriends() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        alice.addFriend(bob);
        bob.acceptFriendRequest(alice);
        Assert.assertEquals(alice.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        UniqueFriendList uniqueFriendListAlice = new UniqueFriendList();
        UniqueFriendList uniqueFriendListBob = new UniqueFriendList();
        uniqueFriendListAlice.add(new Friendship(bob, alice, alice, FriendshipStatus.ACCEPTED));
        uniqueFriendListBob.add(new Friendship(alice, alice, bob, FriendshipStatus.ACCEPTED));
        Assert.assertEquals(alice.getFriends(), uniqueFriendListAlice.asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriends(), uniqueFriendListBob.asUnmodifiableObservableList());
    }

    @Test
    public void listMultipleFriends() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        User carol = new User(new Username("Carol"), new Password("password"), new Name("Carol"),
                new Phone("2433"), new Email("carol@g.com"));
        alice.addFriend(bob);
        alice.addFriend(carol);
        bob.acceptFriendRequest(alice);
        carol.acceptFriendRequest(alice);
        Assert.assertEquals(alice.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        UniqueFriendList uniqueFriendListAlice = new UniqueFriendList();
        UniqueFriendList uniqueFriendListBob = new UniqueFriendList();
        UniqueFriendList uniqueFriendListCarol = new UniqueFriendList();
        uniqueFriendListAlice.add(new Friendship(bob, alice, alice));
        uniqueFriendListAlice.add(new Friendship(carol, alice, alice));
        uniqueFriendListBob.add(new Friendship(alice, alice, bob));
        uniqueFriendListCarol.add(new Friendship(alice, alice, carol));
        Assert.assertEquals(alice.getFriends(), uniqueFriendListAlice.asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriends(), uniqueFriendListBob.asUnmodifiableObservableList());
        Assert.assertEquals(carol.getFriends(), uniqueFriendListCarol.asUnmodifiableObservableList());
    }

    @Test
    public void deleteFriendRequest() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        alice.addFriend(bob);
        bob.deleteFriendRequest(alice);
        Assert.assertEquals(alice.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
    }

    @Test
    public void multipleFriendRequestWithDelete() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        alice.addFriend(bob);
        bob.deleteFriendRequest(alice);
        alice.addFriend(bob);
        Assert.assertEquals(alice.getFriendRequests() , new UniqueFriendList().asUnmodifiableObservableList());
        UniqueFriendList uniqueFriendList = new UniqueFriendList();
        uniqueFriendList.add(new Friendship(alice, alice, bob));
        Assert.assertEquals(bob.getFriendRequests() , uniqueFriendList.asUnmodifiableObservableList());
    }

    @Test
    public void multipleFriendRequestToSameUser() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        Assert.assertTrue(alice.equals(bob));
    }

    @Test
    public void friendDelete() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        alice.addFriend(bob);
        bob.acceptFriendRequest(alice);
        alice.deleteFriend(bob);
        Assert.assertEquals(alice.getFriends() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriends() , new UniqueFriendList().asUnmodifiableObservableList());
    }

    @Test
    public void friendDifferentUserMultipleDelete() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        User carol = new User(new Username("Carol"), new Password("password"), new Name("Carol"),
                new Phone("2433"), new Email("carol@g.com"));
        alice.addFriend(bob);
        alice.addFriend(carol);
        bob.acceptFriendRequest(alice);
        carol.acceptFriendRequest(alice);
        alice.deleteFriend(bob);
        carol.deleteFriend(alice);
        Assert.assertEquals(alice.getFriends(), new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(bob.getFriends() , new UniqueFriendList().asUnmodifiableObservableList());
        Assert.assertEquals(carol.getFriends() , new UniqueFriendList().asUnmodifiableObservableList());
    }

}
