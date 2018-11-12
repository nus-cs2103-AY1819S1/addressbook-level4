package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIVATE_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIVATE_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIVATE_ADDRESS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PrivacyCommand;
import seedu.address.logic.commands.PrivacyCommand.FieldsToChange;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrivacyCommand object.
 */
public class PrivacyCommandParser implements Parser<PrivacyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of PrivacyCommand
     * and returns a PrivacyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PrivacyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrivacyCommand.MESSAGE_USAGE), pe);
        }

        FieldsToChange fieldsToChange = new FieldsToChange();
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            fieldsToChange.setPhonePrivacy(ParserUtil.parsePrivacy(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            fieldsToChange.setEmailPrivacy(ParserUtil.parsePrivacy(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            fieldsToChange.setAddressPrivacy(ParserUtil.parsePrivacy(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (!fieldsToChange.isAnyFieldEdited()) {
            throw new ParseException(PrivacyCommand.MESSAGE_NOT_EDITED);
        }

        return new PrivacyCommand(index, fieldsToChange);
    }
}
