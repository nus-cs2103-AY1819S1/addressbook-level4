package seedu.address.model.awareness;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.util.SampleDataUtil;


public class DictionaryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void registerValidMapping_success() throws Exception {
        Dictionary dictionary = new Dictionary();
        dictionary.registerMapping("cs", "computer science");

        String expectedEventName = "computer science";
        String actualEventName = dictionary.getPossibleEventName("cs");

        assertEquals(actualEventName, expectedEventName);
    }

    @Test
    public void registerTrailingWhitespaceMapping_success() throws Exception {
        Dictionary dictionary = new Dictionary();

        dictionary.registerMapping(" sep  ", "    student exchange programme  ");
        String expectedEventName = "student exchange programme";
        String actualEventName = dictionary.getPossibleEventName("sep");

        assertEquals(actualEventName, expectedEventName);
    }

    @Test
    public void registerDuplicateFullPhrase_success() throws Exception {
        Dictionary dictionary = new Dictionary();

        dictionary.registerMapping(" sep  ", "    student exchange programme  ");
        dictionary.registerMapping(" abroad  ", "student exchange programme");

        String expectedEventName = "student exchange programme";
        String actualEventNameA = dictionary.getPossibleEventName("sep");
        String actualEventNameB = dictionary.getPossibleEventName("abroad");

        assertEquals(actualEventNameA, expectedEventName);
        assertEquals(actualEventNameB, expectedEventName);
    }


    @Test
    public void registerDuplicateSlang_failure() throws Exception {
        Dictionary dictionary = new Dictionary();
        dictionary.registerMapping("cs", "computer science");

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Dictionary.MESSAGE_SLANG_CONSTRAINTS);

        dictionary.registerMapping("cs", "counter strike");
    }

    @Test
    public void registerEmptySlang_failure() throws Exception {
        Dictionary dictionary = new Dictionary();

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Dictionary.MESSAGE_SLANG_CONSTRAINTS);
        dictionary.registerMapping("", "computer science");
    }

    @Test
    public void registerWhitespaceSlang_failure() throws Exception {
        Dictionary dictionary = new Dictionary();

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Dictionary.MESSAGE_SLANG_CONSTRAINTS);
        dictionary.registerMapping("    ", "computer science");
    }

    @Test
    public void registerMultiWordSlang_failure() throws Exception {
        Dictionary dictionary = new Dictionary();

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Dictionary.MESSAGE_SLANG_CONSTRAINTS);
        dictionary.registerMapping("comp sci", "computer science");
    }



    @Test
    public void equals() throws Exception {

        Dictionary dictionary = new Dictionary();
        dictionary.registerMapping("cs", "computer science");

        Dictionary otherDictionary = new Dictionary();

        // identical object --> true
        assertTrue(dictionary.equals(dictionary));

        // Dictionary with different mappings --> false
        assertFalse(dictionary.equals(otherDictionary));

        // Dictionary with same mappings --> true
        otherDictionary.registerMapping("cs", "computer science");
        assertTrue(dictionary.equals(dictionary));

    }

    @Test
    public void getPossibleEventName_postiveMatches() {

        Dictionary dictionary = SampleDataUtil.getSampleDictionary();

        // Expression consisting of single full phrase should match the correct possibleEventName
        assertEquals("hackathon", dictionary.getPossibleEventName("hackathon"));

        // Expression consisting of multiple full phrases should match the correct possibleEventName
        assertEquals("financial technology hackathon",
                                    dictionary.getPossibleEventName("financial technology hackathon"));

        // Expression consisting of a single partial phrase should match the correct possibleEventName
        assertEquals("computer", dictionary.getPossibleEventName("comp"));
        assertEquals("science", dictionary.getPossibleEventName("sci"));

        // Expression consisting of multiple partial phrases should match the correct possibleEventName
        assertEquals("computer science", dictionary.getPossibleEventName("comp sci"));
        assertEquals("teaching assistant", dictionary.getPossibleEventName("teach assist"));

        // Expression consisting a single slang should match the correct possibleEventName
        assertEquals("computer science", dictionary.getPossibleEventName("cs"));
        assertEquals("singapore", dictionary.getPossibleEventName("sg"));

        // Expression consisting of multiple slang should match the correct possibleEventName
        assertEquals("undergraduate teaching assistant", dictionary.getPossibleEventName("ug ta"));

        // Expression consisting of both partial and full phrases should match correct possibleEventName
        assertEquals("financial technology research programme",
                                    dictionary.getPossibleEventName("financial tech research prog"));

        assertEquals("double degree programme", dictionary.getPossibleEventName("double deg prog"));

        // Expression consisting of both partial phrase and slang should match correct possibleEventName
        assertEquals("undergraduate research assistant", dictionary.getPossibleEventName("ug res asst"));


        // Expression consisting of both full phrase and slang should match correct possibleEventName
        assertEquals("teaching assistant", dictionary.getPossibleEventName("teaching asst"));
        assertEquals("financial technology hackathon", dictionary.getPossibleEventName("fintech hackathon"));

        // Expression consisting of slang, partial phrase and full phrase should match correct possibleEventName
        assertEquals("undergraduate research programme", dictionary.getPossibleEventName("ug research prog"));
        assertEquals("postgraduate machine learning research opportunities",
                                    dictionary.getPossibleEventName("pg ml research opp"));

    }

    @Test
    public void getPossibleEventName_negativeCases() {

        Dictionary dictionary = SampleDataUtil.getSampleDictionary();

        // edge cases
        assertEquals("", dictionary.getPossibleEventName(""));
        assertEquals("", dictionary.getPossibleEventName("     "));
        assertEquals("slang1 slang2", dictionary.getPossibleEventName("slang1   slang2"));
        assertEquals("slang1 slang2", dictionary.getPossibleEventName("   slang1   slang2  "));
        assertEquals("slang1 slang2 slang3", dictionary.getPossibleEventName("   slang1   slang2      slang3"));
    }
}
