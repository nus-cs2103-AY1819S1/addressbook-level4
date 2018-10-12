package seedu.learnvocabulary.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.tag.Tag;

import seedu.learnvocabulary.model.word.Address;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Phone;
import seedu.learnvocabulary.model.word.Word;

/**
 * Contains utility methods for populating {@code LearnVocabulary} with sample data.
 */
public class SampleDataUtil {
    public static Word[] getSampleWords() {
        return new Word[] {
            new Word(new Name("Alex Yeoh"), new Meaning("Test"), new Phone("87438807"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Word(new Name("Bernice Yu"), new Meaning("Test"), new Phone("99272758"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Word(new Name("Charlotte Oliveiro"), new Meaning("Test"), new Phone("93210283"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Word(new Name("David Li"), new Meaning("Test"), new Phone("91031282"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Word(new Name("Irfan Ibrahim"), new Meaning("Test"), new Phone("92492021"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Word(new Name("Roy Balakrishnan"), new Meaning("Test"), new Phone("92624417"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
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
