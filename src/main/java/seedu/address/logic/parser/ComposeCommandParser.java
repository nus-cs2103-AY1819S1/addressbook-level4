package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.stream.Stream;

import seedu.address.logic.commands.ComposeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ComposeCommandParser implements Parser<ComposeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ComposeCommand
     * and returns an ComposeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ComposeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FROM, PREFIX_TO, PREFIX_SUBJECT, PREFIX_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_FROM, PREFIX_TO, PREFIX_SUBJECT, PREFIX_CONTENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ComposeCommand.MESSAGE_USAGE));
        }

        String from = argMultimap.getValue(PREFIX_FROM).get();
        String to = argMultimap.getValue(PREFIX_TO).get();
        String subject = argMultimap.getValue(PREFIX_SUBJECT).get();
        String content = argMultimap.getValue(PREFIX_CONTENT).get();

        Email email = EmailBuilder.startingBlank()
                .from(from)
                .to(to)
                .withSubject(subject)
                .withPlainText(content)
                .buildEmail();

        return new ComposeCommand(email);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}