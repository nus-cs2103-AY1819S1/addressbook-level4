package seedu.thanepark.logic.parser;

import seedu.thanepark.logic.commands.AllCommandWords;
import seedu.thanepark.logic.commands.HelpCommand;

/**
 * Parses input arguments and creates a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {
    private static final String MORE_INFO_FLAG = "more";

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     */
    @Override
    public HelpCommand parse(String args) {
        String option = args.trim().split(" ")[0];

        boolean isSummarized = true;
        String commandWord = "";
        //summarized help
        if (option.isEmpty()) {
            isSummarized = true;
        //full help
        } else if (option.equals(MORE_INFO_FLAG)) {
            isSummarized = false;
        //help on specific command
        } else if (AllCommandWords.isCommandWord(option)) {
            isSummarized = false;
            commandWord = option;
        //show default summarized help
        } else {
            isSummarized = true;
        }

        return new HelpCommand(isSummarized, commandWord);
    }

}
