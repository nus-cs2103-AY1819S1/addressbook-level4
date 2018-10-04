package seedu.address.logic.commands;

/**
 * Helper class to auto complete commands typed into command box
 */
public class AutoCompleteCommandHelper {
    private static String[] commandWordList = {
        AddCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        HistoryCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD
    };

    /**
     * This method predicts the command the user is entering.
     * @param partialWord The current characters available in command box.
     * @return The predicted command.
     */
    public static String autoCompleteWord(String partialWord) {
        for (String s : commandWordList) {
            if (s.startsWith(partialWord)) {
                return s;
            }
        }
        return null;
    }
}
