package seedu.address.testutil;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;

//@@author alexkmj
/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code newModule}.
     *
     * @param newModule module to be added
     * @return add command string for adding {@code newModule}
     */
    public static String getAddModuleCommand(Module newModule) {
        return AddModuleCommand.COMMAND_WORD
                + " "
                + newModule.getCode().value
                + " "
                + newModule.getYear().value
                + " "
                + newModule.getSemester().value
                + " "
                + newModule.getCredits().value
                + " "
                + newModule.getGrade().value;
    }

    /**
     * Returns a delete command string for deleting {@code target}.
     *
     * @param target module to be deleted
     * @return delete command string for deleting {@code target}
     */
    public static String getDeleteModuleCommand(Module target) {
        return DeleteModuleCommand.COMMAND_WORD
                + " "
                + target.getCode().value
                + " "
                + target.getYear().value
                + " "
                + target.getSemester().value;
    }

    /**
     * Returns a edit command string for editing {@code target}.
     *
     * @param target module to be edited
     * @param code new {@code Code} object
     * @param year new {@code Year} object
     * @param semester new {@code Semester} object
     * @param credit new {@code Credit} object
     * @param grade new {@code Grade} object
     * @return edit command string for editing {@code target}
     */
    public static String getEditModuleCommand(Module target, Code code,
            Year year, Semester semester, Credit credit, Grade grade) {
        String command = EditModuleCommand.COMMAND_WORD
                + " "
                + target.getCode().value
                + " "
                + target.getYear().value
                + " "
                + target.getSemester().value;

        if (code != null) {
            command += " -code " + code.value;
        }

        if (year != null) {
            command += " -year " + year.value;
        }

        if (semester != null) {
            command += " -semester " + semester.value;
        }

        if (credit != null) {
            command += " -credit " + credit.value;
        }

        if (grade != null) {
            command += " -grade " + grade.value;
        }

        return command;
    }
}
