package seedu.address.logic.commands;

import java.util.ArrayList;

public class AutoCompleteCommandHelper {
    //TODO: Implement this to handle autocomplete.
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

    public static String autoCompleteWord(String partialWord) {
        for (String s : commandWordList) {
            if (s.startsWith(partialWord))
                return s;
        }
        return null;
    }
}
