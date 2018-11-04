package seedu.restaurant.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.ArgumentTokenizer.tokenizeToPair;

import org.junit.Test;

import seedu.restaurant.logic.commands.ingredient.StockUpCommand;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentPairMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.Prefix;

public class ArgumentTokenizerTest {

    private final Prefix unknownPrefix = new Prefix("--u");
    private final Prefix pSlash = new Prefix("p/");
    private final Prefix qSlash = new Prefix("q/");
    private final Prefix rSlash = new Prefix("r/");
    private final Prefix dashT = new Prefix("-t");
    private final Prefix hatQ = new Prefix("^Q");

    @Test
    public void tokenize_emptyArgsString_noValues() {
        String argsString = "  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);

        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);
    }

    private void assertPreamblePresent(ArgumentMultimap argMultimap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMultimap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMultimap argMultimap) {
        assertTrue(argMultimap.getPreamble().isEmpty());
    }

    /**
     * Asserts all the arguments in {@code argMultimap} with {@code prefix} match the {@code expectedValues}
     * and only the last value is returned upon calling {@code ArgumentMultimap#getValue(Prefix)}.
     */
    private void assertArgumentPresent(ArgumentMultimap argMultimap, Prefix prefix, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(prefix).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMultimap.getAllValues(prefix).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(prefix).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, Prefix prefix) {
        assertFalse(argMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void tokenize_noPrefixes_allTakenAsPreamble() {
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argMultimap, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        // Preamble present
        String argsString = "  Some preamble string p/ Argument value ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);
        assertPreamblePresent(argMultimap, "Some preamble string");
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

        // No preamble
        argsString = " p/   Argument value ";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);
        assertPreambleEmpty(argMultimap);
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() {
        // Only two arguments are present
        String argsString = "SomePreambleString -t dashT-Value p/pSlash value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentAbsent(argMultimap, hatQ);

        // All three arguments are present
        argsString = "Different Preamble String ^Q111 -t dashT-Value p/pSlash value";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "Different Preamble String");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentPresent(argMultimap, hatQ, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMultimap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);

        /** Also covers: testing for prefixes not specified as a prefix **/

        // Prefixes not previously given to the tokenizer should not return any values
        argsString = unknownPrefix + "some value";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertArgumentAbsent(argMultimap, unknownPrefix);
        assertPreamblePresent(argMultimap, argsString); // Unknown prefix is taken as part of preamble
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString -t dashT-Value ^Q ^Q -t another dashT value p/ pSlash value -t";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argMultimap, hatQ, "", "");
    }

    @Test
    public void tokenize_multipleArgumentsJoined() {
        String argsString = "SomePreambleStringp/ pSlash joined-tjoined -t not joined^Qjoined";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleStringp/ pSlash joined-tjoined");
        assertArgumentAbsent(argMultimap, pSlash);
        assertArgumentPresent(argMultimap, dashT, "not joined^Qjoined");
        assertArgumentAbsent(argMultimap, hatQ);
    }

    @Test
    public void tokenizeToPair_emptyArgsString_failure() {
        String argsString = "  ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);
    }

    @Test
    public void tokenizeToPair_noPrefixes_failure() {
        String argsString = " Cod Fish 10";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);
    }

    @Test
    public void tokenizeToPair_missingPrefix_failure() {
        String argsString = " p/Cod Fish 10";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);

        argsString = " Cod Fish q/10";
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);
    }

    @Test
    public void tokenizeToPair_missingPrefixMultiple_failure() {
        String argsString = " p/Cod Fish q/10 p/Chicken Thigh 2";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);

        argsString = " Cod Fish q/10 p/Chicken Thigh q/2";
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);
    }

    @Test
    public void tokenizeToPair_prefixInvalidSequence_failure() {
        String argsString = " q/10 p/Cod Fish";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);

        argsString = " q/10 p/Cod Fish q/2 p/Chicken Thigh";
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE);
        assertTokenizeToPairFailure(argsString, pSlash, qSlash, expectedMessage);
    }

    @Test
    public void tokenizeToPair_oneArgumentPair_success() {
        String argsString = " p/Cod Fish q/10";
        ArgumentPairMultimap expectedMultimap = new ArgumentPairMultimap();
        expectedMultimap.put("Cod Fish", "10");
        try {
            ArgumentPairMultimap actualMultimap = ArgumentTokenizer.tokenizeToPair(argsString, pSlash, qSlash);
            assertEquals(actualMultimap, expectedMultimap);
        } catch (ParseException e) {
            throw new AssertionError("The thrown ParseException was not expected.");
        }
    }

    @Test
    public void tokenizeToPair_multipleArgumentPairs_success() {
        String argsString = " p/Cod Fish q/10 p/Chicken Thigh q/5";
        ArgumentPairMultimap expectedMultimap = new ArgumentPairMultimap();
        expectedMultimap.put("Cod Fish", "10");
        expectedMultimap.put("Chicken Thigh", "5");
        try {
            ArgumentPairMultimap actualMultimap = ArgumentTokenizer.tokenizeToPair(argsString, pSlash, qSlash);
            assertEquals(actualMultimap, expectedMultimap);
        } catch (ParseException e) {
            throw new AssertionError("The thrown ParseException was not expected.");
        }
    }

    @Test
    public void tokenizeToPair_duplicateFirstArgument_success() {
        String argsString = " p/Cod Fish q/10 p/Cod Fish q/5";
        ArgumentPairMultimap expectedMultimap = new ArgumentPairMultimap();
        expectedMultimap.put("Cod Fish", "5");
        try {
            ArgumentPairMultimap actualMultimap = ArgumentTokenizer.tokenizeToPair(argsString, pSlash, qSlash);
            assertEquals(actualMultimap, expectedMultimap);
        } catch (ParseException e) {
            throw new AssertionError("The thrown ParseException was not expected.");
        }
    }

    /**
     * Asserts that {@code argsString} has failed to tokenise into an argument pair based on the prefixes
     */
    private void assertTokenizeToPairFailure(String argsString, Prefix firstPrefix, Prefix secondPrefix,
            String expectedMessage) {
        try {
            tokenizeToPair(argsString, firstPrefix, secondPrefix);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    @Test
    public void equalsMethod() {
        Prefix aaa = new Prefix("aaa");

        assertEquals(aaa, aaa);
        assertEquals(aaa, new Prefix("aaa"));

        assertNotEquals(aaa, "aaa");
        assertNotEquals(aaa, new Prefix("aab"));
    }

}
