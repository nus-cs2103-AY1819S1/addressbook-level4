package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.imports.ImportAddressBook;
import seedu.address.logic.commands.imports.ImportCcaList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

//@@author kengwoon
/**
 * Imports an XML file to update Hallper.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports an XML file to update Hallper.\n "
            + "Parameters: "
            + "import "
            + "f/C://Users/Documents/FILENAME.xml";

    public static final String MESSAGE_SUCCESS = "%1$s file read and database updated.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_EMPTY_FILE = "File is empty";
    public static final String MESSAGE_CONFIG_ERR = "Configuration error.";
    public static final String MESSAGE_PARSE_ERR = "Error parsing XML file.";

    private final Path path;
    private final List<Person> personList;
    private final Set<Tag> tags;
    private final List<String> roomsList;
    private String cca;


    /**
     * Creates an ImportCommand to import the specified file.
     */
    public ImportCommand(Path path) {
        requireNonNull(path);
        this.path = path;
        this.personList = new ArrayList<>();
        this.tags = new HashSet<>();
        this.roomsList = new ArrayList<>();
        this.cca = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Document doc = parseFile();

        if (doc.getElementsByTagName("persons").getLength() != 0) {
            new ImportAddressBook(doc, model).execute();
        } else {
            new ImportCcaList(doc, model).execute();
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, path.getFileName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && path.equals(((ImportCommand) other).path));
    }

    /**
     * Parses XML file for reading.
     */
    private Document parseFile() throws CommandException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(path.toFile());
            doc.getDocumentElement().normalize();
            return doc;

        } catch (ParserConfigurationException e) {
            throw new CommandException(MESSAGE_CONFIG_ERR);
        } catch (SAXException e) {
            throw new CommandException(MESSAGE_PARSE_ERR);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE);
        }
    }
}
