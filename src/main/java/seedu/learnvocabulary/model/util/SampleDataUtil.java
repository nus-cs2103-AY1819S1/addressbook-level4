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
            new Word(new Name("Rainstorm"), new Meaning("a storm with heavy rain."), getTagSet("toLearn")),
            new Word(new Name("Hurricane"), new Meaning("a storm with a violent wind, in particular a tropical"
                    + "cyclone in the Caribbean.\n"), getTagSet("weather", "toLearn")),
            new Word(new Name("Tsunami"), new Meaning("a long, high sea wave caused"
                    + "by an earthquake or other disturbance.\n"), getTagSet("disasters")),
            new Word(new Name("Malpractice"), new Meaning("improper, illegal, or "
                    + "negligent professional behaviour.\n"), getTagSet("toLearn")),
            new Word(new Name("Sovereignty"), new Meaning("supreme power or authority."),
                    getTagSet("toLearn")),
            new Word(new Name("Funny"), new Meaning("causing laughter or amusement; humorous."),
                    getTagSet("toLearn"))
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
