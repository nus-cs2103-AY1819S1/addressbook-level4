package seedu.learnvocabulary.model.word;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.learnvocabulary.testutil.TypicalWords.ALICE;
import static seedu.learnvocabulary.testutil.TypicalWords.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.learnvocabulary.testutil.WordBuilder;

public class WordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Word word = new WordBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        word.getTags().remove(0);
    }

    @Test
    public void isSameWord() {
        // same object -> returns true
        assertTrue(ALICE.isSameWord(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameWord(null));

        // different phone and email -> returns false
        Word editedAlice = new WordBuilder(ALICE).build();
        assertFalse(ALICE.isSameWord(editedAlice));

        // different name -> returns false
        editedAlice = new WordBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameWord(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new WordBuilder(ALICE)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWord(editedAlice));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Word aliceCopy = new WordBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different word -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Word editedAlice = new WordBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new WordBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
