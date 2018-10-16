package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MC_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REFERRAL_CONTENT;

import seedu.address.logic.commands.DocumentContentAddCommand;
import seedu.address.logic.commands.DocumentContentAddCommand.DocumentContentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DocumentContentAddCommand object
 */
public class DocumentContentAddCommandParser implements Parser<DocumentContentAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DocumentContentAddCommand
     * and returns an DocumentContentAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DocumentContentAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MC_CONTENT, PREFIX_NOTE_CONTENT, PREFIX_REFERRAL_CONTENT);
        DocumentContentDescriptor documentContentDescriptor = new DocumentContentDescriptor();
        if (argMultimap.getValue(PREFIX_MC_CONTENT).isPresent()) {
            documentContentDescriptor.setMcContent(argMultimap.getValue(PREFIX_MC_CONTENT).get());
        }
        if (argMultimap.getValue(PREFIX_NOTE_CONTENT).isPresent()) {
            documentContentDescriptor.setNoteContent(argMultimap.getValue(PREFIX_NOTE_CONTENT).get());
        }
        if (argMultimap.getValue(PREFIX_REFERRAL_CONTENT).isPresent()) {
            documentContentDescriptor.setReferralContent(argMultimap.getValue(PREFIX_REFERRAL_CONTENT).get());
        }

        if (!documentContentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(DocumentContentAddCommand.MESSAGE_USAGE);
        }

        return new DocumentContentAddCommand(documentContentDescriptor);
    }

}
