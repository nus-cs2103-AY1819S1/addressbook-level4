package seedu.learnvocabulary.model.word;

import static seedu.learnvocabulary.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.learnvocabulary.model.tag.Tag;

/**
 * Represents a Word in LearnVocabulary.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Word {

    // Identity fields
    private final Name name;
    private final Meaning meaning;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Word(Name name, Meaning meaning, Set<Tag> tags) {
        requireAllNonNull(name, meaning, tags);
        this.name = name;
        this.meaning = meaning;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
    //@@author Harryqu123
    public void deleteTags(Tag toDelete) {
        tags.remove(toDelete);
    }
    //@@author
    /**
     * Returns true if both words of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two words.
     */
    public boolean isSameWord(Word otherWord) {
        if (otherWord == this) {
            return true;
        }

        return otherWord != null
                && otherWord.getName().fullName.toLowerCase()
                .equals(getName().fullName.toLowerCase());
    }

    /**
     * Returns true if both words have the same identity and data fields.
     * This defines a stronger notion of equality between two words.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Word)) {
            return false;
        }

        Word otherWord = (Word) other;
        return otherWord.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, meaning, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + "\n")
                .append("Meaning: ")
                .append(getMeaning() + "\n")
                .append("Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
