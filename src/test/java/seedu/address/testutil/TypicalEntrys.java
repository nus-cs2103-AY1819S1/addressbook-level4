package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.entry.MajorResumeEntry;
import seedu.address.model.person.Person;

/**
 * class containing default entries.
 */
public class TypicalEntrys {

    // manually added
    public static final MajorResumeEntry WORK_FACEBOOK = new EntryBuilder().withCategory("work")
            .withTitle("Facebook").withDuration("2010 - 2013")
            .withSubHeader("software engineering intern")
            .withTags("java").build();

    public static final MajorResumeEntry NUS_EDUCATION = new EntryBuilder().withCategory("education")
            .withTitle("National University of Singapore").withDuration("2010 - 2014")
            .withSubHeader("Bachelor of computing")
            .withTags("machinelearning").build();

    public static final MajorResumeEntry NUS_EDUCATION_WITH_SPACED_TAG = new EntryBuilder().withCategory("education")
            .withTitle("National University of Singapore").withDuration("2010 - 2013")
            .withSubHeader("Bachelor of computing")
            .withTags("Machine Learning").build();

    private TypicalEntrys() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        /* to be completed when model is updated. */
        return ab;
    }

    public static List<Person> getTypicalEntries() {
        //return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        return new ArrayList<>();
    }
}
