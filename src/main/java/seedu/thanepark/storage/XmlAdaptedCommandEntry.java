package seedu.thanepark.storage;

import java.time.Instant;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.thanepark.commons.exceptions.IllegalValueException;
import seedu.thanepark.model.logging.CommandEntry;
import seedu.thanepark.model.logging.ExecutedCommand;

/**
 * JAXB-friendly representation of the CommandEntry.
 */
@XmlRootElement
public class XmlAdaptedCommandEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CommandEntry's %s field is missing!";

    @XmlElement(required = true)
    private String timeOfEntryString;
    @XmlElement(required = true)
    private String executedCommandString;

    /**
     * Constructs an XmlAdaptedCommandEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCommandEntry() {}

    /**
     * Constructs an {@code XmlAdaptedCommandEntry} with the given ride details.
     */
    public XmlAdaptedCommandEntry(String timeOfEntryString, String executedCommandString) {
        this.timeOfEntryString = timeOfEntryString;
        this.executedCommandString = executedCommandString;
    }

    /**
     * Converts a given CommandEntry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCommandEntry
     */
    public XmlAdaptedCommandEntry(CommandEntry source) {
        timeOfEntryString = source.getTimeOfEntry().toString();
        executedCommandString = source.getExecutedCommand().toString();
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

        if (executedCommandString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExecutedCommand.class.getSimpleName()));
        }
        final ExecutedCommand executedCommand = new ExecutedCommand(executedCommandString);

        return new CommandEntry(timeOfEntry, executedCommand);
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
                && Objects.equals(executedCommandString, otherCommandEntry.executedCommandString);
    }
}
