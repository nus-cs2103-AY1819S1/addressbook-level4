//@@author chantca95
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
/**
 * Directs user to choose a file, then reads from the file.
 * Prepares a list of Persons to add to the address book.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private static final int NAME_FIELD = 0;
    private static final int PHONE_FIELD = 1;
    private static final int EMAIL_FIELD = 2;
    private static final int ADDRESS_FIELD = 3;
    private static final int MEETING_FIELD = 4;
    private static final int TAG_FIELD_START = 5;

    private ArrayList<Person> persons;

    /**
     * Creates a new ImportCommandParser with an empty ArrayList of Persons to be added.
     */
    public ImportCommandParser() {
        persons = new ArrayList<>();
    }

    /**
     * Starts the import process by directing users to choose a file.
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return parseFile(getFileFromFileBrowser());
        } else {
            return parseFile(getFileFromArgs(args));
        }
    }
    /**
     * Parses the selected csv file pointed to by the user's input.
     */
    private File getFileFromArgs(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILE_LOCATION);
        if (!argMultimap.getValue(PREFIX_FILE_LOCATION).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        Path fileLocation = ParserUtil.parseCsv(argMultimap.getValue(PREFIX_FILE_LOCATION).get());
        return fileLocation.toFile();
    }

    private File getFileFromFileBrowser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select .csv file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT", "*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());
        return file;
    }

    /**
     * Parses the selected csv file.
     */
    public ImportCommand parseFile(File file) throws ParseException {

        FileReader fr;

        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException fnfe) {
            throw new ParseException("File not found");
        }

        BufferedReader br = new BufferedReader(fr);
        return parseLinesFromFile(br);
    }
    /**
     * Reads every row of the chosen csv file.
     * Contacts with wrongly formatted fields and/or without Name fields are ignored.
     */
    private ImportCommand parseLinesFromFile(BufferedReader br) {
        boolean hasContactWithInvalidField = false;
        boolean hasContactWithoutName = false;
        try {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",", -1);
                int numAttributes = attributes.length;

                if (attributes[NAME_FIELD].equalsIgnoreCase("Name")
                        || attributes[NAME_FIELD].equalsIgnoreCase("Name:")) { // ignore headers
                    line = br.readLine();
                    continue;
                }

                if (contactHasNoName(attributes, numAttributes)) {
                    hasContactWithoutName = true;
                    line = br.readLine();
                    continue;
                }

                Name name = null;
                Optional<Phone> phone = Optional.empty();
                Optional<Email> email = Optional.empty();
                Optional<Address> address = Optional.empty();
                Meeting meeting = null;

                try {
                    name = ParserUtil.parseName(attributes[NAME_FIELD]);
                    if (!attributes[PHONE_FIELD].matches("")) {
                        phone = Optional.of(ParserUtil.parsePhone(attributes[PHONE_FIELD]));
                    }
                    if (!attributes[EMAIL_FIELD].matches("")) {
                        email = Optional.of(ParserUtil.parseEmail(attributes[EMAIL_FIELD]));
                    }
                    if (!attributes[ADDRESS_FIELD].matches("")) {
                        address = Optional.of(ParserUtil.parseAddress(attributes[ADDRESS_FIELD]));
                    }
                    if (!attributes[MEETING_FIELD].matches("")) {
                        meeting = ParserUtil.parseMeeting(attributes[MEETING_FIELD]);
                    }
                } catch (ParseException pe) {
                    hasContactWithInvalidField = true;
                    line = br.readLine();
                    continue;
                }

                ArrayList<String> tags = new ArrayList<>();
                //Check for tags
                if (numAttributes > TAG_FIELD_START) {
                    for (int i = TAG_FIELD_START; i < numAttributes; i++) {
                        if (!attributes[i].matches("")) {
                            tags.add(attributes[i]);
                        }
                    }
                }

                Set<Tag> tagList = null;
                try {
                    tagList = ParserUtil.parseTags(tags);
                } catch (ParseException e) {
                    line = br.readLine();
                    continue;
                }
                if (meeting == null) {
                    persons.add(new Person(name, phone, email, address, tagList));
                } else {
                    persons.add(new Person(name, phone, email, address, tagList, meeting));
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new ImportCommand(persons, hasContactWithInvalidField, hasContactWithoutName);
    }

    /**
        this method checks if the compulsory name field is filled up.
    */
    private boolean contactHasNoName(String[] attributes, int numAttributes) {
        if (attributes[0].matches("")) {
            return true;
        }
        return false;
    }
}
