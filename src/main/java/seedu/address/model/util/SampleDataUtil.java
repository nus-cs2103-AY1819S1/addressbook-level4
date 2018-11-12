package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Reviews;
import seedu.address.model.restaurant.UserReview;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.Username;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */

public class SampleDataUtil {
    public static final Reviews WAA_COW_REVIEWS = new Reviews()
            .addUserReview(new UserReview(new Username("navekom"), new Rating(3),
                new WrittenReview("I tried the Ultimate Beef Sushi and they were delicious.")))
            .addUserReview(new UserReview(new Username("chelchia"), new Rating(2),
                new WrittenReview("I tried the Udon Cirashi and they were so-so.")))
            .addUserReview(new UserReview(new Username("meena567"), new Rating(2),
                new WrittenReview("Definitely coming back.")))
            .addUserReview(new UserReview(new Username("katespade"), new Rating(2),
                new WrittenReview("The maki was fantastic.")));

    public static final Reviews SUBWAY_REVIEWS = new Reviews()
            .addUserReview(new UserReview(new Username("themyth"), new Rating(5),
                    new WrittenReview("Subway eat fresh.")))
            .addUserReview(new UserReview(new Username("chelchia"), new Rating(5),
                    new WrittenReview("Namaste. Subway nice nice. Good food.")))
            .addUserReview(new UserReview(new Username("meena567"), new Rating(4),
                    new WrittenReview("Subway give fresh food.")))
            .addUserReview(new UserReview(new Username("katespade"), new Rating(5),
                    new WrittenReview("5 stars sub.")));

    public static final Reviews ROYAL_BISTRO_REVIEWS = new Reviews()
            .addUserReview(new UserReview(new Username("brotherLoong"), new Rating(5),
                    new WrittenReview("The food here is 5 stars. Just like the Singapore flag.")))
            .addUserReview(new UserReview(new Username("themyth"), new Rating(5),
                    new WrittenReview("I stalked LHL here.")));

    public static Restaurant[] getSampleRestaurants() {
        return new Restaurant[] {
            new Restaurant(new Name("Waa Cow"), new Phone("63421111"),
                new Address("Stephen Riady Centre"),
                getTagSet("Western"), WAA_COW_REVIEWS),
            new Restaurant(new Name("The Royals Bistro"), new Phone("61221218"),
                new Address("Town Plaza"),
                getTagSet("Italian", "Halal"), ROYAL_BISTRO_REVIEWS),
            new Restaurant(new Name("Subway"), new Phone("66596109"),
                new Address("Town Plaza"),
                getTagSet("Halal", "FastFood"), SUBWAY_REVIEWS),
            new Restaurant(new Name("Starbucks"), new Phone("66596081"),
                new Address("Education Resource Centre"),
                getTagSet("Cafe"), new Reviews()),
            new Restaurant(new Name("Spice Table by Pines"), new Phone("63399912"),
                new Address("Town Plaza"),
                getTagSet("Asian"), new Reviews()),
            new Restaurant(new Name("Sapore Italian Restaurant"), new Phone("62620287"),
                new Address("Town Plaza"),
                getTagSet("Italian"), new Reviews()),
            new Restaurant(new Name("Pizza Hut"), new Phone("62353535"),
                new Address("Stephen Riady Centre"),
                getTagSet("FastFood", "Halal"), new Reviews())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();

        for (Restaurant sampleRestaurant : getSampleRestaurants()) {
            sampleAb.addRestaurant(sampleRestaurant);
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
