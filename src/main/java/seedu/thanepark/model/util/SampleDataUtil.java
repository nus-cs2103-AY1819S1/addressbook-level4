package seedu.thanepark.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.ride.Address;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ThanePark} with sample data.
 */
public class SampleDataUtil {
    public static Ride[] getSampleRides() {
        return new Ride[] {
            new Ride(new Name("Accelerator"), new Maintenance("87"), new WaitTime("1"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Ride(new Name("Battlestar Galactica"), new Maintenance("58"), new WaitTime("2"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("HUMANSvsCYLON", "friends")),
            new Ride(new Name("Canopy Flyer"), new Maintenance("93"), new WaitTime("3"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Ride(new Name("Dino Sorarin"), new Maintenance("28"), new WaitTime("12"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Ride(new Name("Enchanted Airways"), new Maintenance("21"), new WaitTime("9"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Ride(new Name("TRANSFORMERS The Ride"), new Maintenance("17"), new WaitTime("18"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyThanePark getSampleThanePark() {
        ThanePark sampleAb = new ThanePark();
        for (Ride sampleRide : getSampleRides()) {
            sampleAb.addRide(sampleRide);
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
