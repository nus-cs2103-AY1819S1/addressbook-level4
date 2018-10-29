package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import seedu.address.commons.core.CommandsLogCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.logging.CommandEntry;
import seedu.address.model.logging.ExecutedCommand;
import seedu.address.storage.XmlAdaptedCommandEntry;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private static final String MESSAGE_LOG_ERROR = "%1$s when saving command history to file. %2$s";
    private static Logger logger = LogsCenter.getLogger(CommandHistory.class);
    private LinkedList<String> userInputHistory;

    /**
     * Instantiates a new CommandHistory.
     */
    public CommandHistory() {
        userInputHistory = new LinkedList<>();
        CommandsLogCenter.init();
    }

    /**
     * Duplicates a commandHistory.
     */
    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory = new LinkedList<>(commandHistory.userInputHistory);
        CommandsLogCenter.init();
    }

    /**
     * Appends {@code userInput} to the list of user input entered. Logs down IOException as warnings.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
        try {
            CommandEntry commandEntry = new CommandEntry(Instant.now(), new ExecutedCommand(userInput));
            CommandsLogCenter.log(commandEntry);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
            logger.warning(MESSAGE_LOG_ERROR.format(e.getClass().getSimpleName(), e.getMessage()));
        }
    }

    /**
     * Returns a list of CommandEntry. Will log error message if JAXBException | IOException | IllegalValueException
     * is met, where the commands log will be deleted and reinitialized.
     * @return
     */
    public List<CommandEntry> getCommandEntryList() {
        List<CommandEntry> result = new LinkedList<>();
        try {
            List<XmlAdaptedCommandEntry> xmlAdaptedCommandEntryList = CommandsLogCenter.retrieve().getValue();
            if (xmlAdaptedCommandEntryList == null || xmlAdaptedCommandEntryList.isEmpty()) {
                return result;
            }
            for (XmlAdaptedCommandEntry xmlAdaptedCommandEntry : xmlAdaptedCommandEntryList) {
                result.add(xmlAdaptedCommandEntry.toModelType());
            }
        } catch (JAXBException | IOException | IllegalValueException e) {
            e.printStackTrace();
            logger.warning(String.format(MESSAGE_LOG_ERROR, e.getClass().getSimpleName(), e.getMessage()));
            if (!CommandsLogCenter.delete()) {
                logger.warning("Failed to recreate Commands Log file.");
            }
            CommandsLogCenter.init();
        }

        return result;
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
