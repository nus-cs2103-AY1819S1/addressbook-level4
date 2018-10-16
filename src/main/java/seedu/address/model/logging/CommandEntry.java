package seedu.address.model.logging;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a command entry inside command history.
 */
public class CommandEntry {
    private static final DateTimeFormatter formatter =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
            .withLocale(Locale.UK)
            .withZone(ZoneId.systemDefault());

    private final Instant timeOfEntry;
    private final ExecutedCommand executedCommand;

    /**
     * Instantiates a CommandEntry
     */
    public CommandEntry(Instant timeOfEntry, ExecutedCommand executedCommand) {
        this.timeOfEntry = timeOfEntry;
        this.executedCommand = executedCommand;
    }

    public Instant getTimeOfEntry() {
        return timeOfEntry;
    }

    public String getTimeOfEntryString() {
        return formatter.format(timeOfEntry);
    }

    public ExecutedCommand getExecutedCommand() {
        return executedCommand;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandEntry)) {
            return false;
        }

        CommandEntry otherCommandEntry = (CommandEntry) other;
        return Objects.equals(timeOfEntry, otherCommandEntry.getTimeOfEntry())
                && Objects.equals(executedCommand, otherCommandEntry.getExecutedCommand());
    }
}
