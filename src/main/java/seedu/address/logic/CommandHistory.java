package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private LinkedList<String> userInputHistory;

    private Event selectedEvent;

    private Person selectedPerson;

    public CommandHistory() {
        userInputHistory = new LinkedList<>();
    }

    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory = new LinkedList<>(commandHistory.userInputHistory);
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
    }

    public void setSelectedEvent(Event event) {
        selectedEvent = event;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new LinkedList<>(userInputHistory);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return userInputHistory.equals(other.userInputHistory);
    }

    @Override
    public int hashCode() {
        return userInputHistory.hashCode();
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
}
