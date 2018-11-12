package seedu.address.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence, String word,
            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordIgnoreCaseAndFuzzyAddress(sentence, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCaseAndFuzzy_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyName("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyName("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyName("aaa bbb ccc", "bb"));
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyName("aaa bbb ccc", "bbbb"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyName("aaa bBb ccc", "Bbb"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyName("aaa bBb ccc@1", "CCc@1"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyName("  AAA   bBb   ccc  ", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyName("Aaa", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyName("aaa bbb ccc", "  ccc  "));

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyName("AAA bBb ccc  bbb", "bbB"));

        //@@author LZYAndy
        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("", "abcdef")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("    ", "123def"));

        // Matches a partial word only
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("aaa bbb ccc", "bb"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("aaa bbb ccc", "bbbb"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("aaa bBb ccc", "Bbb"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("aaa bBb ccc@1", "CCc@1"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("  AAA   bBb   ccc  ", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("Aaa", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("aaa bbb ccc", "  ccc  "));

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyEmail("AAA bBb ccc  bbb", "bbB"));

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("    ", "123"));

        // Matches a partial word only
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("aaa bbb ccc", "bb"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("aaa bbb ccc", "bbbb"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("aaa bBb ccc", "Bbb"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("aaa bBb ccc@1", "CCc@1"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("Aaa", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("aaa bbb ccc", "  ccc  "));

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyPhone("AAA bBb ccc  bbb", "bbB"));

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("aaa bbb ccc", "bb"));
        assertFalse(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("aaa bbb ccc", "bbbb"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("aaa bBb ccc", "Bbb"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("aaa bBb ccc@1", "CCc@1"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("  AAA   bBb   ccc  ", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("Aaa", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("aaa bbb ccc", "  ccc  "));

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCaseAndFuzzyAddress("AAA bBb ccc  bbb", "bbB"));
        //@@author LZYAndy
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getDetails(null);
    }


}
