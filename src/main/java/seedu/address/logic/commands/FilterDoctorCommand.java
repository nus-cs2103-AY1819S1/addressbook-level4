package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.tag.TagContainsDoctorPredicate;


/**
 * Finds and lists all persons in health book who are tagged as patient.
 */
public class FilterDoctorCommand extends Command {
    public static final String COMMAND_WORD = "filter-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are tagged as Doctor. \n"
            + "Example: " + COMMAND_WORD;

    private final TagContainsDoctorPredicate predicate = new TagContainsDoctorPredicate();

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterDoctorCommand // instanceof handles nulls
                && predicate.equals(((FilterDoctorCommand) other).predicate)); // state check
    }

}
