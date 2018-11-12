package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.task.Frequency.NO_FREQUENCY;
import static seedu.address.model.task.Priority.NO_PRIORITY;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.SimpleParseException;
import seedu.address.logic.parser.tokenizer.ArgumentMultimap;
import seedu.address.logic.parser.tokenizer.exceptions.TokenizationException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Frequency;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an
     * AddCommand object for execution.
     *
     * @throws SimpleParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws SimpleParseException {
        ArgumentMultimap argMultimap;
        try {
            argMultimap =
                ArgumentTokenizer
                    .tokenize(args, PREFIX_NAME, PREFIX_PRIORITY, PREFIX_FREQUENCY, PREFIX_DEADLINE, PREFIX_TAG);
        } catch (TokenizationException ime) {
            throw new SimpleParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), ime);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DEADLINE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new SimpleParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).orElse(NO_PRIORITY));
        Frequency frequency = ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).orElse(NO_FREQUENCY));
        Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Task task = new Task(name, priority, frequency, deadline, tagList);

        return new AddCommand(task);
    }

}
