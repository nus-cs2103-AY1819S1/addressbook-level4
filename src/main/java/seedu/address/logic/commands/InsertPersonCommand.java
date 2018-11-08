package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULEINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONINDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * A command that enables users to insert a person, bidirectionally, into either a module
 * or an occasion.
 */
public class InsertPersonCommand extends Command {

    public static final String COMMAND_WORD = "insertperson";
    public static final String MESSAGE_SUCCESS_INSERT_INTO_MODULE = "Successfully inserted person into module.";
    public static final String MESSAGE_SUCCESS_INSERT_INTO_OCCASION = "Successfully inserted person into occasion";
    public static final String MESSAGE_FAILURE = "Failed to insert person.";
    public static final String MESSAGE_INCORRECT_INDEX = "Please enter a valid index.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Inserts a person into a module/occasion "
                                                            + "bidirectionally.\n" + "Example " + COMMAND_WORD + " "
                                                            + PREFIX_PERSONINDEX + "1 "
                                                            + PREFIX_MODULEINDEX + "2";
    private State currState;
    private int personIndex;
    private int moduleIndex;
    private int occasionIndex;

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

            if (personIndex >= personsList.size()
                    || moduleIndex >= moduleList.size()) {
                throw new CommandException(MESSAGE_INCORRECT_INDEX);
            }

            Person personToReplace = personsList.get(personIndex);
            Module moduleToReplace = moduleList.get(moduleIndex);

            if (moduleToReplace.getStudents().contains(personToReplace)
                    && personToReplace.getModuleList().contains(moduleToReplace)) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            model.insertPerson(personToReplace.makeDeepDuplicate(),
                                moduleToReplace.makeDeepDuplicate(),
                                personToReplace, moduleToReplace);
            model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();
            return new CommandResult(MESSAGE_SUCCESS_INSERT_INTO_MODULE);

        } else if (State.OCCASION_STATE == currState) {
            List<Person> personsList = model.getFilteredPersonList();
            List<Occasion> occasionList = model.getFilteredOccasionList();

            if (personIndex >= personsList.size()
                    || occasionIndex >= occasionList.size()) {
                throw new CommandException(MESSAGE_INCORRECT_INDEX);
            }

            Person personToReplace = personsList.get(personIndex);
            Occasion occasionToReplace = occasionList.get(occasionIndex);

            if (occasionToReplace.getAttendanceList().contains(personToReplace)
                    && personToReplace.getOccasionList().contains(occasionToReplace)) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            model.insertPerson(personToReplace.makeDeepDuplicate(),
                                occasionToReplace.makeDeepDuplicate(),
                                personToReplace, occasionToReplace);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
            model.commitAddressBook();
            return new CommandResult(MESSAGE_SUCCESS_INSERT_INTO_OCCASION);

        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    private State getCurrState() {
        return currState;
    }

    private int getPersonIndex() {
        return personIndex;
    }

    private int getModuleIndex() {
        return moduleIndex;
    }

    private int getOccasionIndex() {
        return occasionIndex;
    }

    /**
     * Captures the current state of the insert command.
     * Represents whether we are bidirectionally inserting a person
     * into an occasion, module or in an erroneous state.
     */
    private enum State {
        OCCASION_STATE,
        MODULE_STATE,
        ERROR_STATE
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof InsertPersonCommand
                && currState.equals(((InsertPersonCommand) other).getCurrState())
                && personIndex == ((InsertPersonCommand) other).getPersonIndex()
                && moduleIndex == ((InsertPersonCommand) other).getModuleIndex()
                && occasionIndex == ((InsertPersonCommand) other).getOccasionIndex();
    }
}
