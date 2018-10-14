package seedu.address.user;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.user.Email;
import seedu.address.model.user.Name;
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
        Assert.assertEquals(alice.listFriendRequests() , "");
        Assert.assertEquals(bob.listFriendRequests() , "Alice\n");
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
        Assert.assertEquals(alice.listFriendRequests() , "Bob\nCarol\n");
        Assert.assertEquals(bob.listFriendRequests() , "");
        Assert.assertEquals(carol.listFriendRequests() , "");
        Assert.assertEquals(alice.listFriends(), "");
        Assert.assertEquals(bob.listFriends(), "");
    }

    @Test
    public void listFriends() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        alice.addFriend(bob);
        bob.acceptFriendRequest(alice);
        Assert.assertEquals(alice.listFriendRequests() , "");
        Assert.assertEquals(bob.listFriendRequests() , "");
        Assert.assertEquals(alice.listFriends(), "Bob\n");
        Assert.assertEquals(bob.listFriends(), "Alice\n");
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
        Assert.assertEquals(alice.listFriendRequests() , "");
        Assert.assertEquals(bob.listFriendRequests() , "");
        Assert.assertEquals(alice.listFriends(), "Bob\nCarol\n");
        Assert.assertEquals(bob.listFriends(), "Alice\n");
        Assert.assertEquals(carol.listFriends(), "Alice\n");
    }

    @Test
    public void deleteFriendRequest() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        alice.addFriend(bob);
        bob.deleteFriendRequest(alice);
        Assert.assertEquals(alice.listFriendRequests() , "");
        Assert.assertEquals(bob.listFriendRequests() , "");
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
        Assert.assertEquals(alice.listFriendRequests() , "");
        Assert.assertEquals(bob.listFriendRequests() , "Alice\n");
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
        Assert.assertEquals(alice.listFriends() , "");
        Assert.assertEquals(bob.listFriends() , "");
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
        Assert.assertEquals(alice.listFriends() , "");
        Assert.assertEquals(bob.listFriends() , "");
        Assert.assertEquals(carol.listFriends() , "");
    }

}
