package seedu.address.logic;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author zioul123
/**
 * Contains a utility method used for finding a person by their name in a model provided.
 */
public class PersonFinderUtil {

    public static final String GENERAL_MESSAGE_USAGE = "Press/type help to see the command usage.";

    /**
     * Find a single person from the specified {@Code Model} using the {@Code String personIdentifier}.
     */
    public static Person findPerson(Model model, String personIdentifier) throws ParseException, CommandException {
        String trimmedArgs = personIdentifier.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GENERAL_MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(Arrays.asList(nameKeywords));

        // Supplier is used because the stream is acted on more than once.
        Supplier<Stream<Person>> filteredPersons = () ->
                model.getAddressBook().getPersonList().stream().filter(predicate);

        long numOfPeopleMatching = filteredPersons.get().count();
        if (numOfPeopleMatching == 0) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        } else if (numOfPeopleMatching != 1) {
            throw new CommandException(Messages.MESSAGE_MULTIPLE_PERSONS_FOUND);
        }

        return filteredPersons.get().iterator().next();
    }
}
