package seedu.learnvocabulary.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.tag.Tag;

import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;

/**
 * Contains utility methods for populating {@code LearnVocabulary} with sample data.
 */
public class SampleDataUtil {
    public static Word[] getSampleWords() {
        return new Word[] {
            new Word(new Name("rainstorm"), new Meaning("A storm with heavy rain."), getTagSet(Tag.DEFAULT_TAG)),
            new Word(new Name("hurricane"), new Meaning("A storm with a violent wind, in particular a tropical"
                    + " cyclone in the Caribbean."), getTagSet("weather", Tag.DEFAULT_TAG)),
            new Word(new Name("tsunami"), new Meaning("A long, high sea wave caused"
                    + "by an earthquake or other disturbance."), getTagSet("disasters")),
            new Word(new Name("malpractice"), new Meaning("Improper, illegal, or "
                    + "negligent professional behaviour."), getTagSet(Tag.DEFAULT_TAG)),
            new Word(new Name("sovereignty"), new Meaning("Supreme power or authority."),
                    getTagSet(Tag.DEFAULT_TAG)),
            new Word(new Name("funny"), new Meaning("Causing laughter or amusement; humorous."),
                    getTagSet(Tag.DEFAULT_TAG))
        };
    }

    public static ReadOnlyLearnVocabulary getSampleLearnVocabulary() {
        LearnVocabulary sampleAb = new LearnVocabulary();
        for (Word sampleWord : getSampleWords()) {
            sampleAb.addWord(sampleWord);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
