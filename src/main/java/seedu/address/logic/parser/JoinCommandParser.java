package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.JoinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.shared.Address;
import seedu.address.model.shared.Title;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new JoinCommand object
 */
public class JoinCommandParser implements Parser<JoinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JoinCommand
     * and returns an JoinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JoinCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP);

        if (!argMultimap.areAllPrefixesPresent(PREFIX_NAME, PREFIX_GROUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JoinCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_GROUP).get());

        Phone phone = new Phone("0000");
        Email email = new Email("dummy_email@gmail.com");
        Address addr = new Address("dummy_address");
        Set<Tag> tagList = new HashSet<>();

        Person person = new Person(name, phone, email, addr, tagList);
        Group group = new Group(title);

        return new JoinCommand(person, group);
    }
}
