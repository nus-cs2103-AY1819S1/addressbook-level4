package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.commands.GoToCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

//@@author GabrielYik

/**
 * A parser that parses an argument for the {@code GoToCommand}.
 */
public class GoToCommandParser implements Parser<GoToCommand> {

    private static final Logger logger = LogsCenter.getLogger(GoToCommandParser.class);

    @Override
    public GoToCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_YEAR, PREFIX_SEMESTER);

        if (!argMultimap.containsAllPrefixes(PREFIX_YEAR, PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.fine("In goto command parser: year or semester or both not supplied");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));
        }

        int year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        int semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());

        return new GoToCommand(year, semester);
    }
}
