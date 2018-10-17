package seedu.address.logic.parser;

import seedu.address.logic.commands.EditTimeCommand;
import seedu.address.logic.commands.FilterByEducationCommand;
import seedu.address.logic.commands.TimeAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Time;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

public class EditTimeCommandParser implements Parser<EditTimeCommand> {

    /**
     * EditTimeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public EditTimeCommand parse(String args) throws ParseException {

        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIME, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIME, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTimeCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Time oldTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Time newTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

        return new EditTimeCommand(name.toString(), oldTime, newTime);



    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
