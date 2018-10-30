package seedu.address.testutil;

import java.time.Instant;

import seedu.address.model.logging.CommandEntry;
import seedu.address.model.logging.ExecutedCommand;

/**
 * Builds valid CommandEntry for test and valid/invalid Command Strings.
 */
public class CommandEntryBuilder {
    public static final CommandEntry[] COMMAND_ENTRIES = {
        new CommandEntry(Instant.now(), new ExecutedCommand("add asdfvf")),
        new CommandEntry(Instant.now(), new ExecutedCommand("delete 1")),
        new CommandEntry(Instant.now(), new ExecutedCommand("help"))};

    public static final String[] COMMAND_STRINGS = {"add 20", "delete 1", "history"};

}
