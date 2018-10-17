package seedu.learnvocabulary.model.word;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_LEVITATE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_ABILITY;
import static seedu.learnvocabulary.testutil.TypicalWords.LEVITATE;
import static seedu.learnvocabulary.testutil.TypicalWords.SUMO;

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
        assertTrue(SUMO.isSameWord(SUMO));

        // null -> returns false
        assertFalse(SUMO.isSameWord(null));

        // different name -> returns false
        Word editedSumo = new WordBuilder(SUMO).build();
        editedSumo = new WordBuilder(SUMO).withName(VALID_NAME_LEVITATE).build();
        assertFalse(SUMO.isSameWord(editedSumo));

        // same name, different attributes -> returns true
        editedSumo = new WordBuilder(SUMO)
                .withTags(VALID_TAG_ABILITY).build();
        assertTrue(SUMO.isSameWord(editedSumo));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Word sumoCopy = new WordBuilder(SUMO).build();
        assertTrue(SUMO.equals(sumoCopy));

        // same object -> returns true
        assertTrue(SUMO.equals(SUMO));

        // null -> returns false
        assertFalse(SUMO.equals(null));

        // different type -> returns false
        assertFalse(SUMO.equals(5));

        // different word -> returns false
        assertFalse(SUMO.equals(LEVITATE));

        // different name -> returns false
        Word editedSumo = new WordBuilder(SUMO).withName(VALID_NAME_LEVITATE).build();
        assertFalse(SUMO.equals(editedSumo));
    }
}
