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
            new Word(new Name("Alex Yeoh"), new Meaning("Test"), getTagSet("friends")),
            new Word(new Name("Bernice Yu"), new Meaning("Test"), getTagSet("colleagues", "friends")),
            new Word(new Name("Charlotte Oliveiro"), new Meaning("Test"), getTagSet("neighbours")),
            new Word(new Name("David Li"), new Meaning("Test"), getTagSet("family")),
            new Word(new Name("Irfan Ibrahim"), new Meaning("Test"), getTagSet("classmates")),
            new Word(new Name("Roy Balakrishnan"), new Meaning("Test"), getTagSet("colleagues"))
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
