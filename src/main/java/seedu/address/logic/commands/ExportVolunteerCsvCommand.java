package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Exports a person's volunteer information from SocialCare
 */
public class ExportVolunteerCsvCommand extends Command {
    public static final String COMMAND_WORD = "exportvolunteercsv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a CSV file of the volunteer "
            + "the specified index in the displayed volunteer list.\n"
            + "You can specify more than one volunteer to add to the CSV "
            + "by adding a whitespace after each index number\n"
            + "Parameters: INDEX1 (must be a positive integer) INDEX2 INDEX3 ...\n"
            + "Example 1: " + COMMAND_WORD + " 1 2 3 4 5 6 7";

    private static final String MESSAGE_EXPORT_VOLUNTEER_SUCCESS = "Volunteer(s) exported as CSV "
            + "to your Desktop.";
    private static final String MESSAGE_EXPORT_VOLUNTEER_FAILED = "Volunteer(s) export failed, please try again.";

    private final ArrayList<Index> index;

    /**
     * @param index of volunteer or event in the filtered list
     */
    public ExportVolunteerCsvCommand(ArrayList<Index> index) {
        requireNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Volunteer> list = model.getFilteredVolunteerList();

        //Validate the given input within available index
        for ( Index i : index ) {
            // Handle case where the index input exceeds or equals the size of the last displayed list
            if (i.getZeroBased() >= list.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
            }
        }

        try {
            createVolunteerCsv(model.getFilteredVolunteerList(), index);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_EXPORT_VOLUNTEER_FAILED);
        }

        return new CommandResult(MESSAGE_EXPORT_VOLUNTEER_SUCCESS);

    }

    /**
     * Helper method to create and write the csv file given the volunteer
     * @param list to contain the list of volunteers from model
     * @param index to hold the list of indexes to export
     */
    private void createVolunteerCsv(ObservableList<Volunteer> list, ArrayList<Index> index) throws Exception {
        // Setting up file path
        File output = new File(System.getProperty("user.home") + "/Desktop/"
                + Integer.toString(index.size()) + "volunteers.csv");

        // Setting up writer & stringbuilder for appending
        PrintWriter pw = new PrintWriter(output);
        StringBuilder sb = new StringBuilder();
        String csvSplit = ",";

        //appending column titles
        sb.append("Name" + csvSplit);
        sb.append("Phone" + csvSplit);
        sb.append("Address" + csvSplit);
        sb.append("Email" + csvSplit);
        sb.append("Birthday" + csvSplit);
        sb.append("Gender" + csvSplit);
        sb.append("Tags" + csvSplit);
        sb.append("VolunteerID");
        sb.append(System.getProperty("line.separator"));

        for ( Index i : index ) {
            Volunteer volunteer = list.get(i.getOneBased());

            //appending volunteer information accordingly
            sb.append(volunteer.getName().toString() + csvSplit);
            sb.append(volunteer.getPhone().toString() + csvSplit);
            sb.append(volunteer.getAddress().toString() + csvSplit);
            sb.append(volunteer.getEmail().toString() + csvSplit);
            sb.append(volunteer.getBirthday().toString() + csvSplit);
            sb.append(volunteer.getGender().toString() + csvSplit);
            sb.append(volunteer.getTags().toString() + csvSplit);
            sb.append(volunteer.getVolunteerId().toString() + csvSplit);
            sb.append(System.getProperty("line.separator"));
        }

        pw.write(sb.toString());
        pw.close();
    }

}
