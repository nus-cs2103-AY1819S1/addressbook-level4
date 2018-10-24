package seedu.learnvocabulary.model.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.testutil.TypicalWords.SUMO;

import org.junit.jupiter.api.Test;

import seedu.learnvocabulary.logic.parser.exceptions.ParseException;

class DictionaryTest {

    @Test
    void getWordToLearn() {
        Dictionary dictionary = new Dictionary("fly");
        assertNotNull(dictionary);
    }

    @Test
    void invoke() throws ParseException {
        Dictionary dictionary = new Dictionary(SUMO.getName().fullName);
        dictionary.invoke();
        assertNotNull(dictionary.getWordToLearn());
        assertNotNull(dictionary.getDefinition());
    }

    @Test
    void getDefinition() throws ParseException {
        Dictionary dictionary = new Dictionary(SUMO.getName().fullName);
        assertNotNull(dictionary.invoke().getDefinition());
    }

    @Test
    void isConnectedToInternet() throws ParseException {
        Dictionary dictionary = new Dictionary(SUMO.getName().fullName);
        dictionary.invoke();
        assertTrue(Dictionary.isConnectedToInternet());
    }

    @Test
    void isWordInOnlineDictionary() throws ParseException {
        Dictionary dictionary = new Dictionary(SUMO.getName().fullName);
        dictionary.invoke();
        assertNotNull(Dictionary.isWordInOnlineDictionary(dictionary.getWordToLearn()));
    }
}
