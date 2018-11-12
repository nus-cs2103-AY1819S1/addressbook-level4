package seedu.address.model.group;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.Name;
import seedu.address.model.user.Email;
import seedu.address.model.user.Password;
import seedu.address.model.user.Phone;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

public class UserGroupListTest {

    @Test
    public void createGroup() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        Group group = alice.createGroup("My Lonely Club");
        Assert.assertEquals(alice.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(alice.listGroupRequests() , "");
        ArrayList<User> listAcceptedUsers = new ArrayList<>();
        listAcceptedUsers.add(alice);
        ArrayList<User> listPendingUsers = new ArrayList<>();
        Assert.assertEquals(group.getAcceptedUsers(), listAcceptedUsers);
        Assert.assertEquals(group.getPendingUsers(), listPendingUsers);
    }

    @Test
    public void addMultipleUsersAfterCreation() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        User carol = new User(new Username("Carol"), new Password("password"), new Name("Carol"),
                new Phone("2433"), new Email("carol@g.com"));
        Group group = alice.createGroup("My Lonely Club");
        alice.addGroupMembers(group, bob, carol);
        Assert.assertEquals(alice.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(alice.listGroupRequests() , "");
        Assert.assertEquals(bob.listGroups() , "");
        Assert.assertEquals(bob.listGroupRequests() , "My Lonely Club\n");
        Assert.assertEquals(carol.listGroups() , "");
        Assert.assertEquals(carol.listGroupRequests() , "My Lonely Club\n");
        ArrayList<User> listAcceptedUsers = new ArrayList<>();
        listAcceptedUsers.add(alice);
        ArrayList<User> listPendingUsers = new ArrayList<>();
        listPendingUsers.add(bob);
        listPendingUsers.add(carol);
        Assert.assertEquals(group.getAcceptedUsers(), listAcceptedUsers);
        Assert.assertEquals(group.getPendingUsers(), listPendingUsers);
    }

    @Test
    public void acceptGroupRequest() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        User carol = new User(new Username("Carol"), new Password("password"), new Name("Carol"),
                new Phone("2433"), new Email("carol@g.com"));
        Group group = alice.createGroup("My Lonely Club");
        alice.addGroupMembers(group, bob, carol);
        bob.acceptGroupRequest(group);
        Assert.assertEquals(alice.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(alice.listGroupRequests() , "");
        Assert.assertEquals(bob.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(bob.listGroupRequests() , "");
        Assert.assertEquals(carol.listGroups() , "");
        Assert.assertEquals(carol.listGroupRequests() , "My Lonely Club\n");
        ArrayList<User> listAcceptedUsers = new ArrayList<>();
        listAcceptedUsers.add(alice);
        listAcceptedUsers.add(bob);
        ArrayList<User> listPendingUsers = new ArrayList<>();
        listPendingUsers.add(carol);
        Assert.assertEquals(group.getAcceptedUsers(), listAcceptedUsers);
        Assert.assertEquals(group.getPendingUsers(), listPendingUsers);
    }

    @Test
    public void deleteGroupRequest() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        User carol = new User(new Username("Carol"), new Password("password"), new Name("Carol"),
                new Phone("2433"), new Email("carol@g.com"));
        Group group = alice.createGroup("My Lonely Club");
        alice.addGroupMembers(group, bob, carol);
        bob.deleteGroupRequest(group);
        Assert.assertEquals(alice.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(alice.listGroupRequests() , "");
        Assert.assertEquals(bob.listGroups() , "");
        Assert.assertEquals(bob.listGroupRequests() , "");
        Assert.assertEquals(carol.listGroups() , "");
        Assert.assertEquals(carol.listGroupRequests() , "My Lonely Club\n");
        ArrayList<User> listAcceptedUsers = new ArrayList<>();
        listAcceptedUsers.add(alice);
        ArrayList<User> listPendingUsers = new ArrayList<>();
        listPendingUsers.add(carol);
        Assert.assertEquals(group.getAcceptedUsers(), listAcceptedUsers);
        Assert.assertEquals(group.getPendingUsers(), listPendingUsers);
    }

    @Test
    public void deleteGroup() {
        User alice = new User(new Username("Alice"), new Password("password"), new Name("Alice"),
                new Phone("8942"), new Email("alice@g.com"));
        User bob = new User(new Username("Bob"), new Password("password"), new Name("Bob"),
                new Phone("2433"), new Email("bob@g.com"));
        User carol = new User(new Username("Carol"), new Password("password"), new Name("Carol"),
                new Phone("2433"), new Email("carol@g.com"));
        Group group = alice.createGroup("My Lonely Club");
        alice.addGroupMembers(group, bob, carol);
        bob.acceptGroupRequest(group);
        carol.acceptGroupRequest(group);
        Assert.assertEquals(alice.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(alice.listGroupRequests() , "");
        Assert.assertEquals(bob.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(bob.listGroupRequests() , "");
        Assert.assertEquals(carol.listGroups() , "My Lonely Club\n");
        Assert.assertEquals(carol.listGroupRequests() , "");
        ArrayList<User> listAcceptedUsers = new ArrayList<>();
        listAcceptedUsers.add(alice);
        listAcceptedUsers.add(bob);
        listAcceptedUsers.add(carol);
        ArrayList<User> listPendingUsers = new ArrayList<>();
        Assert.assertEquals(group.getAcceptedUsers(), listAcceptedUsers);
        Assert.assertEquals(group.getPendingUsers(), listPendingUsers);
        bob.deleteGroup(group);
        Assert.assertEquals(bob.listGroups() , "");
        Assert.assertEquals(bob.listGroupRequests() , "");
        listAcceptedUsers.remove(bob);
        Assert.assertEquals(group.getAcceptedUsers(), listAcceptedUsers);
        Assert.assertEquals(group.getPendingUsers(), listPendingUsers);
    }
}
