package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.TypeUtil;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import javax.xml.bind.annotation.XmlElement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JAXB-friendly version of the Occasion.
 *
 * @author alistair
 */
public class XmlAdaptedOccasion {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Occasion's %s field is missing!";

    @XmlElement(required = true)
    private String occasionName;
    @XmlElement(required = true)
    private String occasionDateTime;
    @XmlElement(required = true)
    private String organiser;
    @XmlElement(required = true)
    private String location;
    @XmlElement
    private List<Person> attendanceList = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedOccasion.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedOccasion() {}

    /**
     * Constructs an {@code XmlAdaptedOccasion} with the given occasion details.
     */
    public XmlAdaptedOccasion(String occasionName, String occasionDate, String organiser, String location, List<XmlAdaptedTag> tagged) {
        this.occasionName = occasionName;
        this.occasionDateTime = occasionDate;
        this.organiser = organiser;
        this.location = location;
        this.attendanceList = new ArrayList<>();
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Occasion into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOccasion
     */
    public XmlAdaptedOccasion(Occasion source) {
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted occasion object into the model's Occasion object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted occasion
     */
    public void toModelType() throws IllegalValueException {
        final List<Tag> occasionTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            occasionTags.add(tag.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>(occasionTags);
//        return new Occasion(new OccasionName(occasionName), new OccasionDate(occasionDateTime), organiser, modelTags, TypeUtil.OCCASION);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedOccasion)) {
            return false;
        }

        XmlAdaptedOccasion otherOccasion = (XmlAdaptedOccasion) other;
        return Objects.equals(occasionName, otherOccasion.occasionName)
                && Objects.equals(occasionDateTime, otherOccasion.occasionDateTime)
                && Objects.equals(location, otherOccasion.location)
                && tagged.equals(otherOccasion.tagged);
    }
}
