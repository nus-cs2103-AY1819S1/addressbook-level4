package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ContactType;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Vendor;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private final ContactType contactType;

    public AddCommandParser(ContactType contactType) {
        this.contactType = contactType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = createLegalArgumentMultimap(args);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new AddCommand(createContact(name, phone, email, address, tagList));
    }

    /**
     * Creates either a client or a service provider contact given the name, phone, email, address and tags.
     */
    private Contact createContact(Name name, Phone phone, Email email, Address address, Set<Tag> tagList) {
        switch (contactType) {
        case CLIENT:
            return new Client(name, phone, email, address, tagList);
        case VENDOR:
            return new Vendor(name, phone, email, address, tagList);
        default:
            // should nvr come here
            throw new RuntimeException("No such Contact Type");
        }
    }

    /**
     * Creates a {@code ArgumentMultimap} using the arguments from the input.
     * @param args The arguments from the input
     * @return the {@code ArgumentMultimap} generated using the input arguments.
     * @throws ParseException If a legal {@code ArgumentMultimap} is not able to be created due to an invalid command
     *     format.
     */
    protected ArgumentMultimap createLegalArgumentMultimap(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(AddCommand.MESSAGE_USAGE, contactType)));
        }

        return argMultimap;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
