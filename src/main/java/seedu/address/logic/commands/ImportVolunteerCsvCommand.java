package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.io.File;

public class ImportVolunteerCsvCommand extends Command {


    public static final String COMMAND_WORD = "exportvolunteercsv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a CSV file of the volunteer "
            + "the specified index in the displayed volunteer list.\n"
            + "You can specify more than one volunteer to add to the CSV "
            + "by adding a whitespace after each index number\n"
            + "Parameters: INDEX1 (must be a positive integer) INDEX2 INDEX3 ...\n"
            + "Example 1: " + COMMAND_WORD + " 1 2 3 4 5 6 7";

    private static final String MESSAGE_IMPORT_VOLUNTEER_SUCCESS = "Volunteer(s) imported from CSV file "
            + "to your Desktop.";
    private static final String MESSAGE_IMPORT_VOLUNTEER_FAILED = "Volunteer(s) import failed, please try again.";

    public ImportVolunteerCsvCommand(File file) {

    }

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {


    }
}
