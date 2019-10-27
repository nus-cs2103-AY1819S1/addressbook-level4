package seedu.address.testutil;

import static seedu.address.testutil.TypicalEntryDescription.getTypicalEntryDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EntryBook;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.util.EntryBuilder;

/**
 * class containing default entries.
 */
public class TypicalEntrys {

    // included in getTypicalEntries()
    public static final ResumeEntry WORK_FACEBOOK = new EntryBuilder().withCategory("work")
            .withTitle("Facebook").withDuration("2010 - 2013")
            .withSubHeader("software engineering intern")
            .withTags("java").build();

    public static final ResumeEntry WORK_FACEBOOK_DIFF_ENTRYINFO_SAME_CAT_TAG = new EntryBuilder().withCategory("work")
            .withTitle("Facebook").withDuration("May 2007 - July 2007")
            .withSubHeader("Data Science Intern")
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
            .withCategory("awards").buildMinorEntry();

    // Entries with description
    public static final ResumeEntry WORK_FACEBOOK_WITH_DESC = new EntryBuilder().withCategory("work")
            .withTitle("Facebook").withDuration("2010 - 2013")
            .withSubHeader("software engineering intern")
            .withTags("java")
            .withDescription(getTypicalEntryDescription())
            .build();

    // manually added
    public static final ResumeEntry WORK_CAROUSELL = new EntryBuilder().withCategory("work")
            .withTitle("Carousell").withDuration("Nov 2017 - Jan 2018")
            .withSubHeader("Intern")
            .withTags("mobile", "web").build();

    public static final ResumeEntry PROJECT_ORBITALAPP = new EntryBuilder().withCategory("projects")
            .withTitle("Schedule Planner for Android").withDuration("May 2018 - Aug 2018")
            .withSubHeader("NUS Orbital Gemini level project")
            .withTags("mobile", "app", "school").build();

    private TypicalEntrys() {} // prevents instantiation

    /**
     * Returns an {@code EntryBook} with all the typical persons.
     */
    public static EntryBook getTypicalEntryBook() {
        EntryBook eb = new EntryBook();
        /* to be completed when model is updated. */
        for (ResumeEntry entry : getTypicalEntries()) {
            eb.addEntry(entry);
        }
        return eb;
    }

    public static List<ResumeEntry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(
                    WORK_FACEBOOK,
                    NUS_EDUCATION_WITH_SPACED_TAG,
                    AWARD_WITH_NO_ENTRYINFO_NO_DESC,
                    NUS_EDUCATION,
                    WORK_FACEBOOK_DIFF_ENTRYINFO_SAME_CAT_TAG));
    }
}
