//@@author chantca95
package seedu.address.logic.commands;

import static seedu.address.model.meeting.Meeting.NO_MEETING;
import static seedu.address.model.meeting.Meeting.NO_MEETING_MSG;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
/**
 * Exports current state of InsuRen as a .csv file whose name is specified by the user.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "x";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " destinationFilename.csv";
    public static final String MESSAGE_SUCCESS = "Contacts successfully exported.";

    private String fileName;

    public ExportCommand(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Writes current InsuRen data into .csv file.
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fos);
        populateFile(pw, model);
        pw.close();

        return new CommandResult(MESSAGE_SUCCESS);
    }
    /**
     * Gets list of persons from a ReadOnlyAddressBook and populates the .csv file
     */
    private void populateFile(PrintWriter pw, Model model) {
        ObservableList<Person> bufferList = model.getAddressBook().getPersonList();
        pw.println("Name, Phone, Email, Address, Meeting, Tags");
        for (Person current : bufferList) {
            insertPersonIntoCsv(current, pw);
        }
    }
    /**
     * Insert Persons one by one, cleaning up their entries in the process.
     */
    private void insertPersonIntoCsv(Person current, PrintWriter pw) {
        String name;
        String phone;
        String email;
        String address;
        String meeting;
        Set<Tag> tags;
        ArrayList<String> stringTags = new ArrayList<>();

        name = cleanEntry(current.getName().toString());
        if (current.getPhone().isPresent()) {
            phone = cleanEntry(current.getPhone().toString());
        } else {
            phone = "";
        }
        if (current.getEmail().isPresent()) {
            email = cleanEntry(current.getEmail().toString());
        } else {
            email = "";
        }
        if (current.getAddress().isPresent()) {
            address = cleanEntry(current.getAddress().toString());
        } else {
            address = "";
        }
        meeting = current.getMeeting().toString();
        if(meeting.equals(NO_MEETING_MSG)) {
            meeting = NO_MEETING;
        }
        tags = current.getTags();
        for (Tag currentTag : tags) {
            String currString = currentTag.toString();
            currString = cleanEntry(currString);
            stringTags.add(currString);
        }

        //write this person to the printwriter
        pw.print(name + "," + phone + "," + email + "," + address + "," + meeting + ",");
        for (String currentTag : stringTags) {
            pw.print(currentTag);
            pw.print(",");
        }
        pw.println();
    }
    /**
     * Removes commas and Optional[] bracketing from a Person's contact fields.
     */
    private String cleanEntry(String oldStr) {
        String newStr = oldStr.replaceAll(",", "");
        newStr = newStr.replaceAll("Optional", "");
        newStr = newStr.replaceAll("\\[", "");
        newStr = newStr.replaceAll("]", "");
        return newStr;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExportCommand)) {
            ExportCommand otherEc = (ExportCommand) other;
            return (this.fileName.equals(otherEc.getFileName()));
        }
        return false;
    }
}
