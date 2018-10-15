package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import seedu.address.commons.core.CommandsLogCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.logging.CommandEntry;
import seedu.address.model.logging.ExecutedCommand;
import seedu.address.storage.XmlAdaptedCommandEntry;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private static final String MESSAGE_LOG_ERROR = "%1$s when saving command history to file. %2$s";
    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);
    private LinkedList<String> userInputHistory;

    /**
     * Instantiates a new CommandHistory.
     */
    public CommandHistory() {
        userInputHistory = new LinkedList<>();
    }

    /**
     * Duplicates a commandHistory.
     */
    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory = new LinkedList<>(commandHistory.userInputHistory);
    }

    /**
     * Sets the marshaller to use a standard XML output.
     */
    private static void standardizeXmlOutput(Marshaller marshaller) throws PropertyException {
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty("com.sun.xml.bind.xmlHeaders", CommandsLogCenter.STANDARDIZED_XML_ENCODING);
    }

    /**
     * Appends {@code userInput} to the list of user input entered. Logs down IOException as warnings.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
        try {
            CommandEntry commandEntry = new CommandEntry(Instant.now(), new ExecutedCommand(userInput));

            XmlAdaptedCommandEntry xmlCommandEntry = new XmlAdaptedCommandEntry(commandEntry);
            JAXBContext context = JAXBContext.newInstance(xmlCommandEntry.getClass());
            StringWriter stringWriter = new StringWriter();
            Marshaller marshaller = context.createMarshaller();
            standardizeXmlOutput(marshaller);
            marshaller.marshal(xmlCommandEntry, stringWriter);
            CommandsLogCenter.log(stringWriter.toString());
        } catch (JAXBException je) {
            je.printStackTrace();
            logger.warning(MESSAGE_LOG_ERROR.format(je.getClass().getSimpleName(), je.getMessage()));
        } catch (IOException ie) {
            ie.printStackTrace();
            logger.warning(MESSAGE_LOG_ERROR.format(ie.getClass().getSimpleName(), ie.getMessage()));
        }
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
