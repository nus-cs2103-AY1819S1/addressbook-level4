package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.meeting.commons.core.Messages;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.person.Person;

/**
 * Finds and lists all persons in MeetingBook whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPersonCommand extends FindCommand<Person> {

    public static final String FIND_PERSON_PARAM = "person";
    public static final String FIND_PERSON_PARAM_SHORT = "p";

    public FindPersonCommand(Predicate<Person> predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_FOUND_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
