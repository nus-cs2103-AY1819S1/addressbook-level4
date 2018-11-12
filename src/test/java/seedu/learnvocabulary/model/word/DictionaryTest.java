package seedu.learnvocabulary.model.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void wordNotInOnlineDictionary() {
        Dictionary dictionary = new Dictionary("invalidwordtest");
        try {
            dictionary.invoke();
        } catch (ParseException e) {
            assertEquals(Dictionary.WORD_NOT_EXIST, e.getMessage());
        }
    }

    @Test
    void wordIsEmpty() {
        Dictionary dictionary = new Dictionary("");
        try {
            dictionary.invoke();
        } catch (ParseException e) {
            assertEquals(Dictionary.MESSAGE_CANNOT_BE_EMPTY, e.getMessage());
        }
    }

    @Test
    void getWordOfTheDay() {
        Dictionary dictionary = new Dictionary("");
        assertNotNull(dictionary);

        //testing with a user input afterwards. SHould have no effect.
        dictionary = new Dictionary("horse");
        assertNotNull(dictionary);
    }

    @Test
    void fetchWordOfTheDay() throws ParseException {
        Dictionary dictionary = new Dictionary("");
        dictionary.fetchWordOfTheDay();
        assertNotNull(dictionary.getWordOfTheDay());
    }

    @Test
    void doesWordOfTheDayExist() throws ParseException {
        assertNotNull(Dictionary.doesWordOfTheDayExist());
    }
}
