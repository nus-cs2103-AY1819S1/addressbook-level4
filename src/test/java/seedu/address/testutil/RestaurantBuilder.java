package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Reviews;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Restaurant objects.
 */
public class RestaurantBuilder {

    public static final String DEFAULT_NAME = "Hwangs Korean Restaurant";
    public static final String DEFAULT_PHONE = "68888282";
    public static final String DEFAULT_ADDRESS = "Town Plaza";

    private Name name;
    private Phone phone;
    private Address address;
    private Set<Tag> tags;
    private Reviews reviews;

    public RestaurantBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        reviews = new Reviews();
    }

    /**
     * Initializes the RestaurantBuilder with the data of {@code restaurantToCopy}.
     */
    public RestaurantBuilder(Restaurant restaurantToCopy) {
        name = restaurantToCopy.getName();
        phone = restaurantToCopy.getPhone();
        address = restaurantToCopy.getAddress();
        tags = new HashSet<>(restaurantToCopy.getTags());
        reviews = restaurantToCopy.getReviews();
    }

    /**
     * Sets the {@code Name} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Reviews} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withReviews(Reviews reviews) {
        this.reviews = reviews;
        return this;
    }

    public Restaurant build() {
        return new Restaurant(name, phone, address, tags, reviews);
    }

}
