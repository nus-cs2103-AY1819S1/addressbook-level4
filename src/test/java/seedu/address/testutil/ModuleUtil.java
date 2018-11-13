package seedu.address.testutil;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.AdjustCommand;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.parser.arguments.AddArgument;
import seedu.address.logic.parser.arguments.DeleteArgument;
import seedu.address.logic.parser.arguments.EditArgument;
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
     * Returns a edit command string for adding {@code module}.
     *
     * @param module {@code module} to be added
     * @return add command string for adding {@code module}
     */
    public static String getAddModuleCommand(Module module) {
        return getAddModuleCommand(
                module.getCode(),
                module.getYear(),
                module.getSemester(),
                module.getCredits(),
                module.getGrade()
        );
    }

    /**
     * Returns a edit command string for adding module.
     *
     * @param code new {@code Code} object
     * @param year new {@code Year} object
     * @param semester new {@code Semester} object
     * @param credit new {@code Credit} object
     * @param grade new {@code Grade} object
     * @return add command string for adding a module
     */
    public static String getAddModuleCommand(Code code, Year year,
            Semester semester, Credit credit, Grade grade) {
        String command = AddModuleCommand.COMMAND_WORD
                + " " + AddArgument.NEW_CODE.getShortName()
                + " " + code.value
                + " " + AddArgument.NEW_YEAR.getShortName()
                + " " + year.value
                + " " + AddArgument.NEW_SEMESTER.getShortName()
                + " " + semester.value
                + " " + AddArgument.NEW_CREDIT.getShortName()
                + " " + credit.value
                + " ";

        if (grade != null) {
            command += AddArgument.NEW_GRADE.getShortName()
                    + " " + grade.value
                    + " ";
        }

        return command;
    }

    /**
     * Returns a delete command string for deleting {@code target}.
     *
     * @param target module to be deleted
     * @return delete command string for deleting {@code target}
     */
    public static String getDeleteModuleCommand(Module target) {
        return DeleteModuleCommand.COMMAND_WORD
                + " " + DeleteArgument.TARGET_CODE.getShortName()
                + " " + target.getCode().value
                + " " + DeleteArgument.TARGET_YEAR.getShortName()
                + " " + target.getYear().value
                + " " + DeleteArgument.TARGET_SEMESTER.getShortName()
                + " " + target.getSemester().value;
    }

    /**
     * Returns a delete command string for deleting a module.
     *
     * @param targetCode module matching the code
     * @param targetYear if module matching the year if not null
     * @param targetSemester if module matching the semester if not null
     * @return delete command for deleting module
     */
    public static String getDeleteModuleCommand(Code targetCode,
            Year targetYear, Semester targetSemester) {
        return DeleteModuleCommand.COMMAND_WORD
                + " " + DeleteArgument.TARGET_CODE.getShortName()
                + " " + targetCode.value
                + " " + DeleteArgument.TARGET_YEAR.getShortName()
                + " " + targetYear.value
                + " " + DeleteArgument.TARGET_SEMESTER.getShortName()
                + " " + targetSemester.value;
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
        return getEditModuleCommand(target.getCode(),
                target.getYear(),
                target.getSemester(),
                code,
                year,
                semester,
                credit,
                grade);
    }

    /**
     * Returns a edit command string for editing module.
     *
     * @param targetCode module matching the code
     * @param targetYear if module matching the year if not null
     * @param targetSemester if module matching the semester if not null
     * @param code new {@code Code} object
     * @param year new {@code Year} object
     * @param semester new {@code Semester} object
     * @param credit new {@code Credit} object
     * @param grade new {@code Grade} object
     * @return edit command string for editing {@code target}
     */
    public static String getEditModuleCommand(Code targetCode, Year targetYear,
            Semester targetSemester, Code code, Year year, Semester semester,
            Credit credit, Grade grade) {
        String command = EditModuleCommand.COMMAND_WORD
                + " " + EditArgument.TARGET_CODE.getShortName()
                + " " + targetCode.value
                + " " + EditArgument.TARGET_YEAR.getShortName()
                + " " + targetYear.value
                + " " + EditArgument.TARGET_SEMESTER.getShortName()
                + " " + targetSemester.value;

        command += " ";

        if (code != null) {
            command += " " + EditArgument.NEW_CODE.getShortName()
                    + " " + code.value;
        }

        if (year != null) {
            command += " " + EditArgument.NEW_YEAR.getShortName()
                    + " " + year.value;
        }

        if (semester != null) {
            command += " " + EditArgument.NEW_SEMESTER.getShortName()
                    + " " + semester.value;
        }

        if (credit != null) {
            command += " " + EditArgument.NEW_CREDIT.getShortName()
                    + " " + credit.value;
        }

        if (grade != null) {
            command += " " + EditArgument.NEW_GRADE.getShortName()
                    + " " + grade.value;
        }

        return command;
    }

    //@@author jeremiah-ang

    /**
     * Returns an adjust command string for adjusting {@code code}
     *
     * @param code
     * @param year
     * @param sem
     * @param grade
     * @return an adjust command string
     */
    public static String getAdjustModuleCommand(Code code, Year year, Semester sem, Grade grade) {
        return AdjustCommand.COMMAND_WORD + " "
                + code.value + " "
                + ((year == null) ? "" : year.value + " ")
                + ((sem == null) ? "" : sem.value + " ")
                + grade.value;
    }
}
