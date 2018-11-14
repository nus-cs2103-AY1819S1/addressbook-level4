package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.PERSON;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.FindPersonRequestEvent;
import seedu.address.commons.events.ui.ShowPersonRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name, or phone, or email, or address contains any of the argument
 * keywords.
 * Keyword matching is case insensitive.
 */
public class FindPersonCommand extends Command {
    public static final String COMMAND_WORD = "findperson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name, phone, email, address "
            + "contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charles \n"
            + "Example: " + COMMAND_WORD + " p/123 2333 6666 \n"
            + "Example: " + COMMAND_WORD + " e/abc@de.fg \n"
            + "Example: " + COMMAND_WORD + " a/qwer 123 \n"
            + "Example: " + COMMAND_WORD + " mc/CS2103 \n";

    private final Predicate<Person> predicate;

    public FindPersonCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        model.setActiveType(PERSON);
        EventsCenter.getInstance().post(new ShowPersonRequestEvent());

        EventsCenter.getInstance().post(new FindPersonRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPersonCommand // instanceof handles nulls
                && predicate.equals(((FindPersonCommand) other).predicate)); // state check
    }
}
