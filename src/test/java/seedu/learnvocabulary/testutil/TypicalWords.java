package seedu.learnvocabulary.testutil;

import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_MEANING;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.word.Word;

/**
 * A utility class containing a list of {@code Word} objects to be used in tests.
 */
public class TypicalWords {

    public static final Word ALICE = new WordBuilder().withName("Alice Pauline").withMeaning("Test")
            .withTags("friends").build();
    public static final Word BENSON = new WordBuilder().withName("Benson Meier").withMeaning("Test")
            .withTags("owesMoney", "friends").build();
    public static final Word CARL = new WordBuilder().withName("Carl Kurz").withMeaning("Test")
            .build();
    public static final Word DANIEL = new WordBuilder().withName("Daniel Meier").withMeaning("Test")
            .withTags("friends").build();
    public static final Word ELLE = new WordBuilder().withName("Elle Meyer").withMeaning("Test")
            .build();
    public static final Word FIONA = new WordBuilder().withName("Fiona Kunz").withMeaning("Test")
            .build();
    public static final Word GEORGE = new WordBuilder().withName("George Best").withMeaning("Test")
            .build();

    // Manually added
    public static final Word HOON = new WordBuilder().withName("Hoon Meier").withMeaning("Test")
            .build();
    public static final Word IDA = new WordBuilder().withName("Ida Mueller").withMeaning("Test")
            .build();

    // Manually added - Word's details found in {@code CommandTestUtil}
    public static final Word AMY = new WordBuilder().withName(VALID_NAME_AMY).withMeaning(VALID_MEANING)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Word BOB = new WordBuilder().withName(VALID_NAME_BOB).withMeaning(VALID_MEANING)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalWords() {} // prevents instantiation

    /**
     * Returns an {@code LearnVocabulary} with all the typical words.
     */
    public static LearnVocabulary getTypicalLearnVocabulary() {
        LearnVocabulary ab = new LearnVocabulary();
        for (Word word : getTypicalWords()) {
            ab.addWord(word);
        }
        return ab;
    }

    public static List<Word> getTypicalWords() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
