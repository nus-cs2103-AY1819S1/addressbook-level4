package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.module.exceptions.MultipleModuleEntryFoundException;

//@@author alexkmj
/**
 * Contains utility methods used for executing commands in the various
 * Command classes.
 */
public class CommandUtil {
    public static final String MESSAGE_NO_SUCH_MODULE = "No such module.";
    public static final String MESSAGE_MULTIPLE_MODULE_ENTRIES = "There exists"
            + " more than one module that matches the target code, and target"
            + " year and target semester is not specified.";

    /**
     * Returns the targeted module.
     * <p>
     * Checks if module specified by {@code targetCode} exist. If multiple
     * module entries matches {@code targetCode}, check if {@code targetYear}
     * and {@code targetSemester} has been specified. If all check passes, the
     * targeted module is returned.
     *
     * @param model {@code model} containing the transcript
     * @return targeted module
     * @throws CommandException thrown when specified module does not exist or
     * there are multiple module entries matching the {@code targetCode} but
     * {@code targetYear} or {@code targetSemester} was not specified
     */
    public static Module getUniqueTargetModule(Model model, Code targetCode,
            Year targetYear, Semester targetSemester) throws CommandException {
        requireNonNull(targetCode);

        try {
            return model.getOnlyOneModule(targetCode,
                    targetYear,
                    targetSemester);
        } catch (ModuleNotFoundException moduleNotFoundException) {
            throw new CommandException(MESSAGE_NO_SUCH_MODULE);
        } catch (MultipleModuleEntryFoundException multipleFoundException) {
            throw new CommandException(MESSAGE_MULTIPLE_MODULE_ENTRIES);
        }
    }
}
