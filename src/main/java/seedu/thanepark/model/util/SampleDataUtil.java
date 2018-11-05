package seedu.thanepark.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.Status;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ThanePark} with sample data.
 */
public class SampleDataUtil {
    public static Ride[] getSampleRides() {
        return new Ride[] {
            new Ride(new Name("Accelerator"), new Maintenance("87"), new WaitTime("1"),
                new Zone("Sci-Fi City, F3"), getTagSet("HeightLimit")),
            new Ride(new Name("Battlestar Galactica HUMAN"), new Maintenance("58"), new WaitTime("0"),
                new Zone("Sci-Fi City, F1"), getTagSet("Red", "Popular", "Dangerous", "rollerCoaster"),
                Status.SHUTDOWN),
            new Ride(new Name("Battlestar Galactica CYLON"), new Maintenance("58"), new WaitTime("0"),
                new Zone("Sci-Fi City, F2"), getTagSet("GREY", "Popular", "Dangerous", "rollerCoaster"),
                Status.SHUTDOWN),
            new Ride(new Name("TRANSFORMERS The Ride"), new Maintenance("17"), new WaitTime("18"),
                new Zone("Sci-Fi City, F4"), getTagSet("MeetAndGreet", "Popular", "3D")),
            new Ride(new Name("Canopy Flyer"), new Maintenance("93"), new WaitTime("3"),
                new Zone("The Lost World, D2"), getTagSet("neighbours")),
            new Ride(new Name("Dino Sorarin"), new Maintenance("28"), new WaitTime("12"),
                new Zone("The Lost World, D3"), getTagSet("family")),
            new Ride(new Name("WaterWorld"), new Maintenance("28"), new WaitTime("12"),
                new Zone("The Lost World, D1"), getTagSet("family")),
            new Ride(new Name("Amber Rock Climb"), new Maintenance("28"), new WaitTime("12"),
                new Zone("The Lost World, D4"), getTagSet("family")),
            new Ride(new Name("Jurassic Park Rapids Adventure"), new Maintenance("21"), new WaitTime("9"),
                new Zone("The Lost World, D5"), getTagSet("classmates")),
            new Ride(new Name("Treasure Hunters"), new Maintenance("5"), new WaitTime("10"),
                    new Zone("Ancient Egypt, E1"), getTagSet("children")),
            new Ride(new Name("Revenge of the Mummy"), new Maintenance("10"), new WaitTime("100"),
                    new Zone("Ancient Egypt, E2"), getTagSet("Popular", "Dangerous", "rollerCoaster")),
            new Ride(new Name("Enchanted Airways"), new Maintenance("13"), new WaitTime("30"),
                    new Zone("Far Far Away, C1"), getTagSet("children", "junior", "rollerCoaster")),
            new Ride(new Name("Shrek 4D Adventure"), new Maintenance("10"), new WaitTime("30"),
                    new Zone("Far Far Away, C2"), getTagSet("4D")),
            new Ride(new Name("Magic Potion Spin"), new Maintenance("1"), new WaitTime("15"),
                    new Zone("Far Far Away, C3"), getTagSet("spin")),
            new Ride(new Name("Donkey Live"), new Maintenance("51"), new WaitTime("5"),
                    new Zone("Far Far Away, C4"), getTagSet("singAlong")),
            new Ride(new Name("Puss in Boots Giant Journey"), new Maintenance("2"), new WaitTime("45"),
                    new Zone("Far Far Away, C5"), getTagSet("rollerCoaster")),
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
