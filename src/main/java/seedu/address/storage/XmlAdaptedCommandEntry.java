package seedu.address.storage;

import java.time.Instant;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.logging.CommandArgs;
import seedu.address.model.logging.CommandEntry;
import seedu.address.model.logging.CommandWord;

/**
 * JAXB-friendly representation of the CommandEntry.
 */
public class XmlAdaptedCommandEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CommandEntry's %s field is missing!";

    @XmlElement(required = true)
    private String timeOfEntryString;
    @XmlElement(required = true)
    private String commandWordString;
    @XmlElement(required = true)
    private String commandArgsString;

    /**
     * Constructs an XmlAdaptedCommandEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCommandEntry() {}

    /**
     * Constructs an {@code XmlAdaptedCommandEntry} with the given ride details.
     */
    public XmlAdaptedCommandEntry(String timeOfEntryString, String commandWordString, String commandArgsString) {
        this.timeOfEntryString = timeOfEntryString;
        this.commandWordString = commandWordString;
        this.commandArgsString = commandArgsString;
    }

    /**
     * Converts a given CommandEntry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRide
     */
    public XmlAdaptedCommandEntry(CommandEntry source) {
        timeOfEntryString = source.getTimeOfEntry().toString();
        commandWordString = source.getCommandWord().toString();
        commandArgsString = source.getCommandArgs().toString();
    }

    /**
     * Converts this jaxb-friendly adapted ride object into the model's CommandEntry object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ride
     */
    public CommandEntry toModelType() throws IllegalValueException {
        if (timeOfEntryString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Instant.class.getSimpleName()));
        }
        final Instant timeOfEntry = Instant.parse(timeOfEntryString);

        if (commandWordString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CommandWord.class.getSimpleName()));
        }
        final CommandWord commandWord = new CommandWord(commandWordString);

        if (commandArgsString == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, CommandArgs.class.getSimpleName()));
        }
        final CommandArgs commandArgs = new CommandArgs(commandArgsString);

        return new CommandEntry(timeOfEntry, commandWord, commandArgs);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCommandEntry)) {
            return false;
        }

        XmlAdaptedCommandEntry otherCommandEntry = (XmlAdaptedCommandEntry) other;
        return Objects.equals(timeOfEntryString, otherCommandEntry.timeOfEntryString)
                && Objects.equals(commandWordString, otherCommandEntry.commandWordString)
                && Objects.equals(commandArgsString, otherCommandEntry.commandArgsString);
    }
}
