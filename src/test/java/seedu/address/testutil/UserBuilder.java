package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Name;
import seedu.address.model.user.Email;
import seedu.address.model.user.Password;
import seedu.address.model.user.Phone;
import seedu.address.model.user.RestaurantReview;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * A utility class to help with building User objects.
 */
public class UserBuilder {

    public static final String DEFAULT_USERNAME = "Cockburn123";
    public static final String DEFAULT_PASSWORD = "bananaCockburn";
    public static final String DEFAULT_NAME = "Alison Cockburn";
    public static final String DEFAULT_PHONE = "99919001";
    public static final String DEFAULT_EMAIL = "cockburn123@example.com";

    private Username username;
    private Password password;
    private Name name;
    private Phone phone;
    private Email email;
    private List<RestaurantReview> restaurantReviewList;

    public UserBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        restaurantReviewList = new ArrayList<>();
    }

    /**
     * Initializes the UserBuilder with the data of {@code userToCopy}.
     */
    public UserBuilder(User userToCopy) {
        username = userToCopy.getUsername();
        password = userToCopy.getPassword();
        name = userToCopy.getName();
        phone = userToCopy.getPhone();
        email = userToCopy.getEmail();
    }

    /**
     * Sets the {@code Username} of the {@code User} that we are building.
     */
    public UserBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code User} that we are building.
     */
    public UserBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code User} that we are building.
     */
    public UserBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code User} that we are building.
     */
    public UserBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code User} that we are building.
     */
    public UserBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Adds the {@code Reviews} to the {@code User} that we are building.
     */
    public UserBuilder withReviews(RestaurantReview...restaurantReviews) {
        this.restaurantReviewList.addAll(Arrays.asList(restaurantReviews));
        return this;
    }

    /**
     * Builds user with username, password, name, phone, email.
     */
    public User build() {
        User toReturn = new User(username, password, name, phone, email);
        for (RestaurantReview restaurantReview: restaurantReviewList) {
            toReturn.addRestaurantReviewToUser(restaurantReview);
        }
        return toReturn;
    }

}
