package seedu.address.model.user;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class UserFriendListTest {
    @Test
    public void listFriendsTest() {
        User Alice  = new User(new Name("Alice"), new Phone("899742"), new Email("alice@gmail.com"), new Address("6 Clementi Road"));
        User Bob  = new User(new Name("Bob"), new Phone("899742"), new Email("bob@gmail.com"), new Address("6 Clementi Road"));
        Alice.addFriend(Bob);
        System.out.println(Alice.listFriends());
        Assert.assertEquals(Alice.listFriends(), "Bob\n");
        Assert.assertEquals(Bob.listFriends(),"Alice\n");
    }

    @Test
    public void multipleListFriends() {
        User Alice  = new User(new Name("Alice"), new Phone("899742"), new Email("alice@gmail.com"), new Address("6 Clementi Road"));
        User Bob  = new User(new Name("Bob"), new Phone("899742"), new Email("bob@gmail.com"), new Address("6 Clementi Road"));
        User Carol  = new User(new Name("Carol"), new Phone("899742"), new Email("carol@gmail.com"), new Address("6 Clementi Road"));
        Alice.addFriend(Bob);
        Carol.addFriend(Alice);
        System.out.println(Alice.listFriends());
        Assert.assertEquals(Alice.listFriends(), "Carol\nBob\n");
        Assert.assertEquals(Bob.listFriends(),"Alice\n");
        Assert.assertEquals(Carol.listFriends(),"Alice\n");
    }
}
