package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Deletes a patient from health book.
 */
public class DeletePersonCommand extends Command {

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_INVALID_DELETE_PERSON = "This %1$s does not exist in the HealthBook";
    public static final String MESSAGE_DUPLICATE_DELETE_PERSON =
            "There are multiple %1$s with this name. Please enter %2$s's number to identify "
                    + "the unique %3$s.\n";

    private final Name name;
    private final Phone phone;
    private final Tag tag;

    public DeletePersonCommand(Name name, Phone phone, Tag tag) {
        this.name = name;
        this.tag = tag;
        this.phone = phone;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToDelete = null;

        for (Person person : lastShownList) {
            if (person.getName().equals(name) && person.getTags().contains(tag)) {
                if (phone != null) {
                    if (person.getPhone().equals(phone)) {
                        personToDelete = person;
                    }
                } else {
                    if (personToDelete != null && personToDelete.getName().equals(name)) {
                        throw new CommandException(String.format(MESSAGE_DUPLICATE_DELETE_PERSON,
                                tag.tagName, tag.tagName, tag.tagName));
                    } else {
                        personToDelete = person;
                    }
                }
            }
        }
        if (personToDelete == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_DELETE_PERSON, tag.tagName));
        }

        model.deletePerson(personToDelete);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonCommand // instanceof handles nulls
                && name.equals(((DeletePersonCommand) other).name)
                && tag.equals(((DeletePersonCommand) other).tag)); // state check
    }
}
