package seedu.address.logic.commands;

import static seedu.address.model.person.Note.MESSAGE_NOTE_CONSTRAINTS;
import static seedu.address.model.person.Note.isValid;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Utility class for Commands that contains methods to check validity.
 */
class ValidatorUtil {

    /**
     * Checks if the index is valid, i.e. less than list's size.
     * Guarantees: index is positive.
     * @param list whose size the index is checked against.
     * @throws CommandException that indicates index is invalid.
     */
    static void checkIfIndexValid(int index, List<Person> list) throws CommandException {
        if (index >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if the text is valid relative to {@code Note}.
     * @throws CommandException that indicates note is invalid.
     */
    static void checkIfTextValid(String text) throws CommandException {
        if (!isValid(text)) {
            throw new CommandException(MESSAGE_NOTE_CONSTRAINTS);
        }
    }
}
