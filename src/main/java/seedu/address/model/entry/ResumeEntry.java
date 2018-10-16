package seedu.address.model.entry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.tag.Tag;

/**
 * class encapsulating entries under education, experience or project section
 */
public class ResumeEntry implements Taggable {

    private Category category;
    private EntryInfo entryInfo; // contains either title,subheader and duration of the entry, or none at all
    private Set<Tag> tags = new HashSet<>();
    private EntryDescription description = new EntryDescription();

    /**
     * Constructs a {@code ResumeEntry}.
     *  @param category A valid category name.
     * @param entryInfo A valid entryInfo.
     * @param tags A set of tags, can be empty.
     *
     */
    public ResumeEntry(Category category, EntryInfo entryInfo, Set<Tag> tags) {
        requireAllNonNull(category, tags, entryInfo);
        this.category = category;
        this.tags.addAll(tags);
        this.entryInfo = entryInfo;
    }

    /**
     * @return checks if the entry is a minor entry := any entry without title, subHeader and duration.
     */
    public boolean isMonorEntry() {
        return entryInfo.isEmpty();
    }

    public Category getCategory() {
        return category;
    }

    public EntryInfo getEntryInfo() {
        return entryInfo;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public EntryDescription getDescription() {
        return description;
    }

    /**
     * Returns true if both entries of the same category, entryInfo and
     * tags
     * This defines a weaker notion of equality between two entries.
     */
    public boolean isSameEntry(ResumeEntry otherMajorEntry) {
        if (otherMajorEntry == this) {
            return true;
        }

        return otherMajorEntry != null
                && otherMajorEntry.getCategory().equals(category)
                && otherMajorEntry.getEntryInfo().equals(entryInfo)
                && otherMajorEntry.getTags().equals(getTags());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ResumeEntry)) {
            return false;
        }

        ResumeEntry otherMajorEntry = (ResumeEntry) other;
        return otherMajorEntry.category.equals(category)
                && otherMajorEntry.getTags().equals(tags)
                && otherMajorEntry.getDescription().equals(description)
                && otherMajorEntry.getEntryInfo().equals(entryInfo);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(category, tags, description, entryInfo);
    }

    @Override
    // note: Description is omitted
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Category: ")
                .append(category.cateName)
                .append(entryInfo)
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
