package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryDescription;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * class to build an entry for test.
 */
public class EntryBuilder {
    public static final String DEFAULT_CAT = "EDUCATION";
    public static final String DEFAULT_TITLE = "THE SOURCE ACADEMY";
    public static final String DEFUALT_SUBHEADER = "Bachelor of Computing";
    public static final String DEFAULT_DURATION = "JAN 2006 - DEC 2010";

    private Category category;
    private EntryInfo entryInfo; // contains title,subheader and duration of the entry
    private Set<Tag> tags = new HashSet<>();
    private EntryDescription description = new EntryDescription();


    public EntryBuilder() {
        this.category = new Category(DEFAULT_CAT);
        this.entryInfo = new EntryInfo(DEFAULT_TITLE, DEFUALT_SUBHEADER, DEFAULT_DURATION);
    }

    /**
     * Initializes the EntryBuilder with the data of {@code entryToCopy}.
     */
    public EntryBuilder(ResumeEntry entryToCopy) {
        category = entryToCopy.getCategory();
        entryInfo = entryToCopy.getEntryInfo();
        tags = entryToCopy.getTags();
        description = entryToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public EntryBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public EntryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public EntryBuilder withTitle(String title) {
        entryInfo.setTitle(title);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public EntryBuilder withSubHeader(String subHeader) {
        entryInfo.setSubHeader(subHeader);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public EntryBuilder withDuration(String duration) {
        entryInfo.setDuration(duration);
        return this;
    }


    public ResumeEntry build() {
        return new ResumeEntry(category, entryInfo, tags);
    }

    /**
     * @return a minor entry, i.e. without entryInfo
     */
    public ResumeEntry buildMinorEntry() {
        return new ResumeEntry(category, new EntryInfo(), tags);
    }
}
