package seedu.address.model.state;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Defines the state of the application with their respective constraints on the type of command it can run.
 */
public class AppState {
    private static State currentState = State.NORMAL;

    private static Set<String> normalCommands = new HashSet<>(Arrays.asList(AddCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD));

    public static void setAppState(State state) {
        currentState = state;
    }

    /**
     *
     * @param command The string command word to be permitted.
     * @return a boolean on whether the given command is permitted.
     */
    public static boolean isCommandPermitted(String command) {
        return permissibleCommands(currentState).contains(command);
    }

    /**
     * Will obtain the set of commands that are permissible in the given state.
     *
     * @param state The state of the app whose commands you are interested in.
     * @return The set of commands that are permissible in the given state.
     */
    private static Set<String> permissibleCommands(State state) {
        switch (state) {

        case NORMAL:
            return normalCommands;

        default:
            return new HashSet<>();
        }
    }
}
