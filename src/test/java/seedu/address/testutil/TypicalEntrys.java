package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EntryBook;
import seedu.address.model.entry.ResumeEntry;

/**
 * class containing default entries.
 */
public class TypicalEntrys {

    // manually added
    public static final ResumeEntry WORK_FACEBOOK = new EntryBuilder().withCategory("work")
            .withTitle("Facebook").withDuration("2010 - 2013")
            .withSubHeader("software engineering intern")
            .withTags("java").build();

    public static final ResumeEntry NUS_EDUCATION = new EntryBuilder().withCategory("education")
            .withTitle("National University of Singapore").withDuration("2010 - 2014")
            .withSubHeader("Bachelor of computing")
            .withTags("machinelearning").build();

    public static final ResumeEntry NUS_EDUCATION_WITH_SPACED_TAG = new EntryBuilder().withCategory("education")
            .withTitle("National University of Singapore").withDuration("2010 - 2013")
            .withSubHeader("Bachelor of computing")
            .withTags("Machine Learning").build();

    public static final ResumeEntry AWARD_WITH_NO_ENTRYINFO_NO_DESC = new EntryBuilder()
            .withCategory("Awards").buildMinorEntry();

    private TypicalEntrys() {} // prevents instantiation

    /**
     * Returns an {@code EntryBook} with all the typical persons.
     */
    public static EntryBook getTypicalEntryBook() {
        EntryBook eb = new EntryBook();
        /* to be completed when model is updated. */
        for (ResumeEntry entry : getTypicalEntries()) {
            eb.addEnty(entry);
        }
        return eb;
    }

    public static List<ResumeEntry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(NUS_EDUCATION_WITH_SPACED_TAG,
                AWARD_WITH_NO_ENTRYINFO_NO_DESC, NUS_EDUCATION));
    }
}
