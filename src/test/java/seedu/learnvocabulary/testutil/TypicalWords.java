package seedu.learnvocabulary.testutil;

import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_MEANING;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
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
            .withAddress("123, Jurong West Ave 6, #08-111").withPhone("94351253")
            .withTags("friends").build();
    public static final Word BENSON = new WordBuilder().withName("Benson Meier").withMeaning("Test")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Word CARL = new WordBuilder().withName("Carl Kurz").withMeaning("Test")
            .withPhone("95352563").withAddress("wall street").build();
    public static final Word DANIEL = new WordBuilder().withName("Daniel Meier").withMeaning("Test")
            .withPhone("87652533")
            .withAddress("10th street").withTags("friends").build();
    public static final Word ELLE = new WordBuilder().withName("Elle Meyer").withMeaning("Test")
            .withPhone("9482224").withAddress("michegan ave").build();
    public static final Word FIONA = new WordBuilder().withName("Fiona Kunz").withMeaning("Test")
            .withPhone("9482427").withAddress("little tokyo").build();
    public static final Word GEORGE = new WordBuilder().withName("George Best").withMeaning("Test")
            .withPhone("9482442").withAddress("4th street").build();

    // Manually added
    public static final Word HOON = new WordBuilder().withName("Hoon Meier").withMeaning("Test")
            .withPhone("8482424").withAddress("little india").build();
    public static final Word IDA = new WordBuilder().withName("Ida Mueller").withMeaning("Test")
            .withPhone("8482131").withAddress("chicago ave").build();

    // Manually added - Word's details found in {@code CommandTestUtil}
    public static final Word AMY = new WordBuilder().withName(VALID_NAME_AMY).withMeaning(VALID_MEANING)
            .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Word BOB = new WordBuilder().withName(VALID_NAME_BOB).withMeaning(VALID_MEANING)
            .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
