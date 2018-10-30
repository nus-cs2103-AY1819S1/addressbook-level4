package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.occasion.Occasion;

/**
 * A utility class containing a list of {@code Occasions} objects to be used in tests.
 */
public class TypicalOccasions {

    public static final Occasion TYPICAL_OCCASION_ONE = new OccasionBuilder()
            .withOccasionName("Project Meeting").withOccasionDate("2018-10-29")
            .withOccasionLocation("UTown").withTags("Needs Prep").build();
    public static final Occasion TYPICAL_OCCASION_TWO = new OccasionBuilder()
            .withOccasionName("Next CS2103 Tutorial").withOccasionDate("2018-10-31")
            .withOccasionLocation("Active Learning Room").withTags("Work Due").build();
    public static final Occasion TYPICAL_OCCASION_THREE = new OccasionBuilder()
            .withOccasionName("Final Project Submission").withOccasionDate("2018-11-13")
            .withOccasionLocation("NIL").withTags("Important").build();
    public static final Occasion TYPICAL_OCCASION_FOUR = new OccasionBuilder()
            .withOccasionName("Christmas Day").withOccasionDate("2018-12-25")
            .withOccasionLocation("NIL").withTags("With Family", "Holiday").build();
    public static final Occasion TYPICAL_OCCASION_FIVE = new OccasionBuilder()
            .withOccasionName("Hiking Trip").withOccasionDate("2019-01-03")
            .withOccasionLocation("Mongolia").build();
    public static final Occasion TYPICAL_OCCASION_SIX = new OccasionBuilder()
            .withOccasionName("Combined Birthday Celebration").withOccasionDate("2019-01-10")
            .withOccasionLocation("TBC").withTags("School Friends").build();

    public static final Occasion EXAM_2103 = new OccasionBuilder()
            .withOccasionName("CS2103 Exam").withOccasionDate("2018-12-05")
            .withOccasionLocation("MPSH 1A").withTags("Must Prepare").build();

    private TypicalOccasions() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical occasions.
     */
    public static AddressBook getTypicalOccasionsAddressBook() {
        AddressBook ab = new AddressBook();
        for (Occasion occasion : getTypicalOccasions()) {
            ab.addOccasion(occasion);
        }
        return ab;
    }

    public static List<Occasion> getTypicalOccasions() {
        return new ArrayList<>(Arrays.asList(TYPICAL_OCCASION_ONE, TYPICAL_OCCASION_TWO,
                TYPICAL_OCCASION_THREE, TYPICAL_OCCASION_FOUR, TYPICAL_OCCASION_FIVE, TYPICAL_OCCASION_SIX));
    }
}
