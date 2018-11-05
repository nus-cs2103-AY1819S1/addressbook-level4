package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE_FULL;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import seedu.thanepark.logic.commands.FindCommand;
import seedu.thanepark.model.ride.RideContainsKeywordsPredicate;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new RideContainsKeywordsPredicate(
                Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    
        // input looking with thanepark prefix
        expectedFindCommand = new FindCommand(new RideContainsKeywordsPredicate(
                new ArrayList<>(), Optional.of(new Zone("10th street"))));
        assertParseSuccess(parser, PREFIX_ZONE.getPrefix() + " 10th street", expectedFindCommand);
    
        // input with thanepark in full
        expectedFindCommand = new FindCommand(new RideContainsKeywordsPredicate(
                new ArrayList<>(), Optional.of(new Zone("10th street"))));
        assertParseSuccess(parser, PREFIX_ZONE_FULL.getPrefix() + " 10th street", expectedFindCommand);
    
        // input with single tag prefix
        List<String> list = Arrays.asList(PREFIX_TAG.getPrefix(), "friends");
        String userInput = getUserInput(list);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        expectedFindCommand = new FindCommand(new RideContainsKeywordsPredicate(new ArrayList<>(), Optional.of(tags)));
        assertParseSuccess(parser, userInput, expectedFindCommand);

        // input with single tag prefix in full
        list = Arrays.asList(PREFIX_TAG_FULL.getPrefix(), "friends");
        userInput = getUserInput(list);
        expectedFindCommand = new FindCommand(new RideContainsKeywordsPredicate(new ArrayList<>(), Optional.of(tags)));
        assertParseSuccess(parser, userInput, expectedFindCommand);

        // input with multiple tags prefix
        list = Arrays.asList(PREFIX_TAG.getPrefix(), "friends", PREFIX_TAG.getPrefix(), "neighbours");
        userInput = getUserInput(list);
        tags.add(new Tag("neighbours"));
        expectedFindCommand = new FindCommand(new RideContainsKeywordsPredicate(new ArrayList<>(), Optional.of(tags)));
        assertParseSuccess(parser, userInput, expectedFindCommand);

        // input with multiple tag prefix in full
        list = Arrays.asList(PREFIX_TAG_FULL.getPrefix(), "friends", PREFIX_TAG_FULL.getPrefix(), "neighbours");
        userInput = getUserInput(list);
        expectedFindCommand = new FindCommand(new RideContainsKeywordsPredicate(new ArrayList<>(), Optional.of(tags)));
        assertParseSuccess(parser, userInput, expectedFindCommand);
    }

    /**
     * Creates a user input from a list of strings
     */
    private String getUserInput(List<String> strings) {
        String userInput = "";
        for (String s : strings) {
            userInput = userInput.concat(s).concat(" ");
        }
        return userInput;
    }
}
