package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.util.ModuleBuilder;

/**
 * Adds a person to the address book.
 */
public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the module specified by the module code. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "-code MODULE_CODE "
            + "-year [YEAR] "
            + "-semester [SEMESTER] "
            + "-credit [CREDIT] "
            + "-grade [GRADE] ";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited module: %1$s";
    public static final String MESSAGE_NO_SUCH_MODULE = "No such module exist.";
    public static final String MESSAGE_MODULE_EXIST = "Edited module already exist.";
    public static final String MESSAGE_MULTIPLE_MODULE_EXIST = "Multiple module entries with the "
            + "same module code exist but year or semester is not specified.";

    private final Code targetCode;
    private final Year targetYear;
    private final Semester targetSemester;
    private final Code newCode;
    private final Year newYear;
    private final Semester newSemester;
    private final Credit newCredit;
    private final Grade newGrade;

    /**
     * Prevents the use of empty constructor.
     */
    private EditModuleCommand() {
        this.targetCode = null;
        this.targetYear = null;
        this.targetSemester = null;
        this.newCode = null;
        this.newYear = null;
        this.newSemester = null;
        this.newCredit = null;
        this.newGrade = null;
    }

    public EditModuleCommand(Code targetCode, Year targetYear, Semester targetSemester,
            Code newCode, Year newYear, Semester newSemester, Credit newCredit, Grade newGrade) {
        requireNonNull(targetCode);

        this.targetCode = targetCode;
        this.targetYear = targetYear;
        this.targetSemester = targetSemester;
        this.newCode = newCode;
        this.newYear = newYear;
        this.newSemester = newSemester;
        this.newCredit = newCredit;
        this.newGrade = newGrade;
    }

    /**
     * Edits the current module in the transcripts.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Module target = getTargetModule(model);

        // create the edited module and check if it already exist in module list of transcript
        Module editedModule = createEditedModule(target);
        editedModuleNotExist(model, editedModule);

        // update and commit
        model.updateModule(target, editedModule);
        model.commitTranscript();

        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    /**
     * Returns the targeted module.
     * <p>
     * Checks if module specified by {@code targetCode} exist. If multiple module entries matches
     * {@code targetCode}, check if {@code targetYear} and {@code targetSemester} has been
     * specified. If all check passes, the targeted module is returned.
     *
     * @param model model containing the transcript
     * @return targeted module
     * @throws CommandException thrown when specified module does not exist or there are multiple
     * module entries matching the {@code targetCode} but {@code targetYear} or
     * {@code targetSemester} was not specified
     */
    private Module getTargetModule(Model model) throws CommandException {
        boolean yearOrSemesterIsNull = targetYear == null || targetSemester == null;

        // returns the number of modules with the target code
        long numOfModulesWithTargetCode = model.getFilteredModuleList()
                .stream()
                .filter(target -> target.getCode().equals(targetCode))
                .count();

        // throws exception if specified module does not exist
        if (numOfModulesWithTargetCode == 0) {
            throw new CommandException(MESSAGE_NO_SUCH_MODULE);
        }

        // throws exception if multiple module exist but year or semester was not specified
        if (numOfModulesWithTargetCode > 1 && yearOrSemesterIsNull) {
            throw new CommandException(MESSAGE_MULTIPLE_MODULE_EXIST);
        }

        // returns the targeted module
        return model.getFilteredModuleList()
                .stream()
                .filter(index -> {
                    return index.getCode().equals(targetCode)
                            && (newYear == null || index.getYear().equals(targetYear))
                            && (newSemester == null || index.getSemester().equals(targetSemester));
                })
                .findAny()
                .orElseThrow(() -> new CommandException(MESSAGE_NO_SUCH_MODULE));
    }

    /**
     * Returns the edited version of the target module.
     *
     * @param target the module to be edited
     * @return the edited version of {@code target}
     */
    private Module createEditedModule(Module target) {
        ModuleBuilder moduleBuilder = new ModuleBuilder(target);

        if (newCode != null) {
            moduleBuilder = moduleBuilder.withCode(newCode);
        }

        if (newYear != null) {
            moduleBuilder = moduleBuilder.withYear(newYear);
        }

        if (newSemester != null) {
            moduleBuilder = moduleBuilder.withSemester(newSemester);
        }

        if (newCredit != null) {
            moduleBuilder = moduleBuilder.withCredit(newCredit);
        }

        if (newGrade != null) {
            moduleBuilder = moduleBuilder.withGrade(newGrade);
        }

        return moduleBuilder.build();
    }

    /**
     * Checks if code, year, or semester has been changed. If code, year, or semester has been
     * changed, {@code editedModuleNotExist} then checks if any module in module list of transcript
     * shares the same module code, year, and semester as the {@code editedModule}.
     *
     * @param model model that contains the transcript
     * @param editedModule module with updated fields
     * @throws CommandException thrown if current module list already contain a module sharing the
     * same module code, year, and semester as the {@code editedModule}
     */
    private void editedModuleNotExist(Model model, Module editedModule) throws CommandException {
        boolean identifierChanged = newCode != null || newYear != null || newSemester != null;

        if (identifierChanged && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_MODULE_EXIST);
        }
    }
}
