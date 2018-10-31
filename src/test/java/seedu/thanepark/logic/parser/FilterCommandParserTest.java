package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.thanepark.logic.commands.FilterCommand;
import seedu.thanepark.model.ride.AttributePredicate;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.RideContainsConditionPredicate;
import seedu.thanepark.model.ride.WaitTime;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        List<AttributePredicate> predicates = new ArrayList<>();

        // Tests with 2 attributes of same type for waiting time
        predicates.add(new AttributePredicate("<", new WaitTime("100")));
        predicates.add(new AttributePredicate(">", new WaitTime("5")));
        FilterCommand expectedFilterCommand = new FilterCommand(new RideContainsConditionPredicate(predicates));
        String userInput = " " + PREFIX_WAITING_TIME + " < 100 " + PREFIX_WAITING_TIME + " > 5";
        assertParseSuccess(parser, userInput, expectedFilterCommand);

        predicates.clear();

        // Tests with Maintenance
        predicates.add(new AttributePredicate("<", new Maintenance("100")));
        predicates.add(new AttributePredicate(">", new Maintenance("5")));
        expectedFilterCommand = new FilterCommand(new RideContainsConditionPredicate(predicates));
        userInput = " " + PREFIX_MAINTENANCE + " < 100 " + PREFIX_MAINTENANCE + " > 5";
        assertParseSuccess(parser, userInput, expectedFilterCommand);

        predicates.clear();

        // Test with 2 different types
        predicates.add(new AttributePredicate("<", new Maintenance("100")));
        predicates.add(new AttributePredicate(">", new WaitTime("5")));
        expectedFilterCommand = new FilterCommand(new RideContainsConditionPredicate(predicates));
        userInput = " " + PREFIX_MAINTENANCE + " < 100 " + PREFIX_WAITING_TIME + " > 5";
        assertParseSuccess(parser, userInput, expectedFilterCommand);
    }
}
