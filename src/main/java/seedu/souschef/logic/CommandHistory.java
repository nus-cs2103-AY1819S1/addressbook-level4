package seedu.souschef.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

import seedu.souschef.logic.parser.Context;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private LinkedList<String> userInputHistory;

    private Context context;

    public CommandHistory() {
        userInputHistory = new LinkedList<>();
        context = Context.RECIPE;
    }

    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory = new LinkedList<>(commandHistory.userInputHistory);
        context = Context.RECIPE;
    }

    /**
     * returns context.
     */
    public Context getContext() {
        return context;
    }

    public String getKeyword() {
        if (context.equals(Context.CROSS)) {
            return "recipe";
        }
        return context.toString().toLowerCase();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
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
}
