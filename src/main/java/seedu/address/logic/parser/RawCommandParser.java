package seedu.address.logic.parser;

import seedu.address.logic.commands.RawCommand;

/**
 * Parses a raw input string. Since this is a command only for power users, error checking is purposely omitted.
 */

public class RawCommandParser {
    public RawCommand parse(String args) {
        return new RawCommand(args);
    }
}
