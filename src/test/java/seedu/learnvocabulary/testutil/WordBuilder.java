package seedu.learnvocabulary.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.util.SampleDataUtil;

import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;

/**
 * A utility class to help with building Word objects.
 */
public class WordBuilder {

    public static final String DEFAULT_NAME = "fly";
    public static final String DEFAULT_MEANING = "to move through the air using wings.";

    private Name name;
    private Meaning meaning;
    private Set<Tag> tags;

    public WordBuilder() {
        name = new Name(DEFAULT_NAME);
        meaning = new Meaning(DEFAULT_MEANING);
        tags = new HashSet<>();
    }

    /**
     * Initializes the WordBuilder with the data of {@code wordToCopy}.
     */
    public WordBuilder(Word wordToCopy) {
        name = wordToCopy.getName();
        meaning = wordToCopy.getMeaning();
        tags = new HashSet<>(wordToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Word} that we are building.
     */
    public WordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Meaning} of the {@code Word} that we are building.
     */
    public WordBuilder withMeaning(String meaning) {
        this.meaning = new Meaning(meaning);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Word} that we are building.
     */
    public WordBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds a word, adds a new default tag to words initialized without tags.
     */
    public Word build() {
        if (tags.isEmpty()) {
            tags.add(new Tag("toLearn"));
        }
        return new Word(name, meaning, tags);
    }

}
