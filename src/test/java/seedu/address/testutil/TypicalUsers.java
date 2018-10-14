package seedu.address.testutil;

import seedu.address.model.UserData;
import seedu.address.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalUsers {
    public static final User ASA = new UserBuilder()
            .withUsername("Cockburn123")
            .withPassword("pAssw0rd")
            .withName("Asa Cockburn")
            .withPhone("99919001")
            .withEmail("cockburn123@example.com").build();
    public static final User BENNY = new UserBuilder()
            .withUsername("benny123")
            .withPassword("pAssw0rd")
            .withName("Benny Tobias")
            .withPhone("99332001")
            .withEmail("benny123@example.com").build();
    public static final User CARLY = new UserBuilder()
            .withUsername("carlymaximus")
            .withPassword("pAssw0rd")
            .withName("Carly Maximus")
            .withPhone("99209001")
            .withEmail("carlymaximus@example.com").build();
    public static final User DANEL = new UserBuilder()
            .withUsername("danelhand")
            .withPassword("pAssw0rd")
            .withName("Danel Handcock")
            .withPhone("93421233")
            .withEmail("danel@example.com").build();
    public static final User ELLIE = new UserBuilder()
            .withUsername("ellie241")
            .withPassword("pAssw0rd")
            .withName("Ellie burnhand")
            .withPhone("94421232")
            .withEmail("handburn@gmail.com").build();
    public static final User FIOXA = new UserBuilder()
            .withUsername("fioxaxiexie")
            .withPassword("pAssw0rd")
            .withName("Fioxa Xie")
            .withPhone("98819001")
            .withEmail("xiexiexie@example.com").build();
    public static final User GOMES = new UserBuilder()
            .withUsername("gomes122")
            .withPassword("pAssw0rd")
            .withName("Gomes Washingburn")
            .withPhone("99915001")
            .withEmail("washingburns@gmail.com").build();

    // Manually added
    public static final User HONG = new UserBuilder()
            .withUsername("beehoooong")
            .withPassword("pAssw0rd")
            .withName("Hong Babae")
            .withPhone("92215001")
            .withEmail("hong1212@gmail.com").build();

    public static final User IPAT = new UserBuilder()
            .withUsername("ipat2323")
            .withPassword("pAssw0rd")
            .withName("Ipat McGriddles")
            .withPhone("97715001")
            .withEmail("ipatmail@gmail.com").build();

    // Manually added - Restaurant's details found in {@code CommandTestUtil}
    public static final User ANG = new UserBuilder()
            .withUsername("leeHsienLoong")
            .withPassword("pAssw0rd")
            .withName("Ang More Kio")
            .withPhone("98822221")
            .withEmail("amkgrc@gmail.com").build();
    public static final User BOON = new UserBuilder()
            .withUsername("leeKongChian")
            .withPassword("pAssw0rd")
            .withName("Shanmu Boon Lay")
            .withPhone("98722221")
            .withEmail("boonlaygrc@gmail.com").build();

    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical restaurants.
     */
    public static UserData getTypicalUserData() {
        UserData userData = new UserData();
        for (User user : getTypicalUsers()) {
            userData.addUser(user);
        }
        return userData;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(ASA, BENNY, CARLY, DANEL, ELLIE, FIOXA, GOMES));
    }

}
