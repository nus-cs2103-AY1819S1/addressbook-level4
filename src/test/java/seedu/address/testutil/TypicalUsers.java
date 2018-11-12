package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UserData;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.user.RestaurantReview;
import seedu.address.model.user.User;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {
    public static final User BENNY = new UserBuilder()
            .withUsername("benny123")
            .withPassword("pAssw0rd")
            .withName("Benny Tobias")
            .withPhone("99332001")
            .withEmail("benny123@example.com").build();
    public static final User CARRLY = new UserBuilder()
            .withUsername("carrlymaximus")
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

    private static RestaurantReview typicalRestaurantReviews = new RestaurantReview(new Name("Waa Cow"),
            new Rating(4), new WrittenReview("I tried the Beef Sushi Bowl and it was amazing."));

    public static final User ASA = new UserBuilder()
            .withUsername("Cockburn123")
            .withPassword("pAssw0rd")
            .withName("Asa Cockburn")
            .withPhone("99919001")
            .withEmail("cockburn123@example.com")
            .withReviews(typicalRestaurantReviews)
            .build();

    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns {@code UserData} with all the typical users.
     */
    public static UserData getTypicalUserData() {
        UserData userData = new UserData();
        for (User user : getTypicalUsers()) {
            userData.addUser(user);
        }

        // Add jios to userData
        List<Jio> jios = TypicalJios.getTypicalJios();
        jios.forEach(jio -> userData.addJio(jio));
        return userData;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(ASA, BENNY, CARRLY, DANEL, ELLIE, FIOXA, GOMES));
    }

}
