package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULEINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONINDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

public class InsertPersonCommand extends Command {

    public static final String COMMAND_WORD = "insertperson";
    // TODO make following messages more detailed.
    public static final String MESSAGE_SUCCESS_INSERT_INTO_MODULE = "Successfully inserted person into module.";
    public static final String MESSAGE_SUCCESS_INSERT_INTO_OCCASION  = "Successfully inserted person into occasion";
    public static final String MESSAGE_FAILURE = "Failed to insert person.";
    public static final String MESSAGE_INCORRECT_INDEX = "Please enter a valid index.";
    // TODO make a better usage string.
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Inserts a person into a module/occasion bidirectionally.\n"
                                                            + "Example " + COMMAND_WORD + " "
                                                            + PREFIX_PERSONINDEX + "1 "
                                                            + PREFIX_MODULEINDEX + "2";
    private State currState;
    private int personIndex;
    private int moduleIndex;
    private int occasionIndex;

    public InsertPersonCommand() {
        currState = State.ERROR_STATE;
    }

    public InsertPersonCommand(Index personIndex, Index moduleIndex, Module dummyModule) {
        requireAllNonNull(personIndex, moduleIndex, dummyModule);
        currState = State.MODULE_STATE;
        this.personIndex = personIndex.getZeroBased();
        this.moduleIndex = moduleIndex.getZeroBased();
    }

    public InsertPersonCommand(Index personIndex, Index occasionIndex, Occasion dummyOccasion) {
        requireAllNonNull(personIndex, occasionIndex, dummyOccasion);
        currState = State.OCCASION_STATE;
        this.personIndex = personIndex.getZeroBased();
        this.occasionIndex = occasionIndex.getZeroBased();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (State.MODULE_STATE == currState) {
            List<Person> personsList = model.getFilteredPersonList();
            List<Module> moduleList = model.getFilteredModuleList();

            if (personIndex > personsList.size()
                    || personIndex < 0
                    || moduleIndex > moduleList.size()
                    || moduleIndex < 0) {
                throw new CommandException(MESSAGE_INCORRECT_INDEX);
            }

            Person personToInsert = personsList.get(personIndex);
            Module moduleToInsert = moduleList.get(moduleIndex);

            if (moduleToInsert.getStudents().contains(personToInsert)
                    && personToInsert.getModuleList().contains(moduleToInsert)) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            model.insertPerson(personToInsert, moduleToInsert);
            model.commitAddressBook();
            return new CommandResult(MESSAGE_SUCCESS_INSERT_INTO_MODULE);
        } else if (State.OCCASION_STATE == currState) {
            List<Person> personsList = model.getFilteredPersonList();
            List<Occasion> occasionList = model.getFilteredOccasionList();

            if (personIndex > personsList.size()
                    || personIndex < 0
                    || occasionIndex > occasionList.size()
                    || occasionIndex < 0) {
                throw new CommandException(MESSAGE_INCORRECT_INDEX);
            }

            Person personToInsert = personsList.get(personIndex);
            Occasion occasionToInsert = occasionList.get(occasionIndex);

            if (occasionToInsert.getAttendanceList().contains(personToInsert)
                    && personToInsert.getOccasionList().contains(occasionToInsert)) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            model.insertPerson(personToInsert, occasionToInsert);
            model.commitAddressBook();
            return new CommandResult(MESSAGE_SUCCESS_INSERT_INTO_OCCASION);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    private enum State {
        OCCASION_STATE,
        MODULE_STATE,
        ERROR_STATE
    }
}
