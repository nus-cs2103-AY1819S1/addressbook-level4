package seedu.address.storage.entry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryDescription;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedTag;

/**
 * JAXB-friendly version of the ResumeEntry.
 */
public class XmlAdaptedResumeEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Entry's %s field is missing!";

    @XmlAttribute
    private String category;
    @XmlElement
    private XmlAdaptedEntryInfo entryInfo;
    @XmlElement
    private XmlAdaptedEntryDescription entryDesc;
    @XmlElement
    private List<XmlAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedResumeEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedResumeEntry() {}


    /**
     * Constructs an {@code XmlAdaptedResumeEntry} with the given entry details.
     */
    public XmlAdaptedResumeEntry(String category, XmlAdaptedEntryInfo entryInfo, XmlAdaptedEntryDescription entryDesc,
                                 List<XmlAdaptedTag> tags) {
        this.category = category;
        this.entryInfo = entryInfo;
        this.entryDesc = entryDesc;
        if (tags != null) {
            this.tags = new ArrayList<>(tags);
        }
    }

    /**
     * Converts a given Entry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedResumeEntry
     */
    public XmlAdaptedResumeEntry(ResumeEntry source) {
        category = source.getCategory().cateName;
        // entryInfo must be null, else XML will contain empty title/subheader/duration as subfields, which will be
        // detected as invalid when reading.
        entryInfo = source.isMinorEntry() ? null : new XmlAdaptedEntryInfo(source.getEntryInfo());
        EntryDescription sourceDesc = source.getDescription();
        // entryDesc must be null if source desc list is empty, else XmlAdaptedEntryDescription will have null
        // "bullets" field
        entryDesc = sourceDesc.getDescriptionList().isEmpty() ? null : new XmlAdaptedEntryDescription(sourceDesc);
        tags = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted resume entry object into the model's ResumeEntry object.
     * // TODO: Add data validation
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted resume entry.
     */
    public ResumeEntry toModelType() throws IllegalValueException {

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                                  Category.class.getSimpleName()));
        }

        if (!Category.isValidCategoryName(category)) {
            throw new IllegalValueException(Category.MESSAGE_CATE_CONSTRAINTS);
        }

        final Category modelCategory = new Category(category);

        /* tags are optional */
        final List<Tag> entryTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tags) {
            entryTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(entryTags);

        /* entry info is optional */
        final EntryInfo modelEntryInfo;

        if (entryInfo == null) {
            modelEntryInfo = new EntryInfo();
        } else {
            modelEntryInfo = entryInfo.toModelType();
        }

        /* entry description is optional*/
        if (entryDesc == null) {
            return new ResumeEntry(modelCategory, modelEntryInfo, modelTags);
        } else {
            final EntryDescription modelDescription = entryDesc.toModelType();
            return new ResumeEntry(modelCategory, modelEntryInfo, modelTags, modelDescription);
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedResumeEntry)) {
            return false;
        }

        XmlAdaptedResumeEntry otherResumeEntry = (XmlAdaptedResumeEntry) other;

        return Objects.equals(category, otherResumeEntry.category)
                && Objects.equals(entryInfo, otherResumeEntry.entryInfo)
                && Objects.equals(entryDesc, otherResumeEntry.entryDesc)
                && Objects.equals(tags, otherResumeEntry.tags);
    }
}
