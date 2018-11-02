package seedu.jxmusic.commons.util;

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
        StringUtil.containsWordIgnoreCase(sentence, word);
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
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for containsSubstringIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for substring: null, empty
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */


    private void assertExceptionThrownForSubstring(Class<? extends Throwable> exceptionClass, String sentence,
            String substring, Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsSubstringIgnoreCase(sentence, substring);
    }

    @Test
    public void containsSubstringIgnoreCase_nullSubstring_throwsNullPointerException() {
        assertExceptionThrownForSubstring(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    @Test
    public void containsSubstringIgnoreCase_emptySubstring_throwsIllegalArgumentException() {
        assertExceptionThrownForSubstring(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Substring parameter cannot be empty"));
    }

    @Test
    public void containsSubstringIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrownForSubstring(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for substring:
     *   - any substring
     *   - substring containing symbols/numbers
     *   - substring with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - non empty string
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first substring in sentence
     *   - last substring in sentence
     *   - middle substring in sentence
     *   - matches multiple substrings
     *
     * Possible scenarios returning false:
     *   - query substring does not match any substring in sentence
     *   - sentence substring matches part of the query substring
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsSubstringIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsSubstringIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsSubstringIgnoreCase("    ", "123"));

        // Does not match any substring
        // Query substring does not match any sentence substring
        assertFalse(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "z"));
        // Query substring bigger than sentence substring
        assertFalse(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "bbbb"));

        // Matches substring in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bBb ccc", "Bbb")); // First substring (boundary case)
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last substring (boundary case)
        assertTrue(StringUtil.containsSubstringIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsSubstringIgnoreCase("Aaa", "aaa")); // Only one substring in sentence (boundary)
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple substrings in sentence
        assertTrue(StringUtil.containsSubstringIgnoreCase("AAA bBb ccc  bbb", "bbB"));
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
