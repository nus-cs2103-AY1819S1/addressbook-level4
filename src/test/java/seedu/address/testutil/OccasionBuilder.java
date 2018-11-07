package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.TypeUtil;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Occasion objects.
 */
public class OccasionBuilder {
    public static final String DEFAULT_OCCASIONNAME = "meeting";
    public static final String DEFAULT_OCCASIONDATE = "2018-01-01".trim();
    public static final String DEFAULT_OCCASIONLOCATION = "SoC";

    private OccasionName occasionName;
    private OccasionDate occasionDate;
    private OccasionLocation occasionLocation;
    private UniquePersonList attendanceList;
    private Set<Tag> tags;

    public OccasionBuilder() {
        occasionName = new OccasionName(DEFAULT_OCCASIONNAME);
        occasionDate = new OccasionDate(DEFAULT_OCCASIONDATE);
        occasionLocation = new OccasionLocation(DEFAULT_OCCASIONLOCATION);
        tags = new HashSet<>();
    }

    public OccasionBuilder(Occasion occasionToCopy) {
        occasionName = occasionToCopy.getOccasionName();
        occasionDate = occasionToCopy.getOccasionDate();
        occasionLocation = occasionToCopy.getOccasionLocation();
        attendanceList = occasionToCopy.getAttendanceList();
        tags = occasionToCopy.getTags();
    }

    /**
     * Sets the {@code occasionName} of the {@code Occasion} that we are building.
     */
    public OccasionBuilder withOccasionName(String occasionName) {
        this.occasionName = new OccasionName(occasionName);
        return this;
    }

    /**
     * Sets the {@code occasionDate} of the {@code Occasion} that we are building.
     */
    public OccasionBuilder withOccasionDate(String occasionDate) {
        this.occasionDate = new OccasionDate(occasionDate);
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Occasion} that we are building.
     */
    public OccasionBuilder withOccasionLocation(String occasionLocation) {
        this.occasionLocation = new OccasionLocation(occasionLocation);
        return this;
    }

    /**
     * Sets the {@code Occasion} as having no {@code occasionDate}.
     */
    public OccasionBuilder withoutOccasionDate() {
        this.occasionDate = new OccasionDate();
        return this;
    }

    /**
     * Sets the {@code attendance list} by using {@code personList}.
     */
    public OccasionBuilder withAttendanceList(List<Person> personList) {
        for (Person person : personList) {
            attendanceList.add(person);
        }

        return this;
    }

    /**
     * Sets the {@code attendance list} by using the UniquePersonList {@code personList}.
     */
    public OccasionBuilder withAttendanceList(UniquePersonList personList) {
        this.attendanceList = personList;
        return this;
    }

    /**
     * Sets the {@code Occasion} as having no {@code location}.
     */
    public OccasionBuilder withoutOccasionLocation() {
        this.occasionLocation = new OccasionLocation();
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Occasion} that we are building.
     */
    public OccasionBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Occasion build() {
        return new Occasion(occasionName, occasionDate, occasionLocation, tags, TypeUtil.OCCASION);
    }
}
