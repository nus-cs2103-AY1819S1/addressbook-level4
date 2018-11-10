package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULEINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONINDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;
import seedu.address.model.util.AttendanceListUtil;

/**
 * A command that enables users to remove a person, bidirectionally, from either a module
 * or an occasion.
 * @author alistair
 */
public class TakeOutPersonCommand extends Command {

    public static final String COMMAND_WORD = "takeoutperson";
    public static final String MESSAGE_SUCCESS_REMOVE_FROM_MODULE = "Successfully removed person from module.";
    public static final String MESSAGE_SUCCESS_REMOVE_FROM_OCCASION = "Successfully removed person from occasion";
    public static final String MESSAGE_FAILURE = "Failed to remove person as person is not linked "
            + "with said module or occasion.";
    public static final String MESSAGE_INCORRECT_INDEX = "Please enter a valid index.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a person from a module/occasion "
                                                            + "bidirectionally.\n" + "Example " + COMMAND_WORD + " "
                                                            + PREFIX_PERSONINDEX + "1 "
                                                            + PREFIX_MODULEINDEX + "2";
    private TypeUtil currType;
    private int personIndex;
    private int moduleIndex;
    private int occasionIndex;

    public TakeOutPersonCommand(Index personIndex, Index eventIndex, TypeUtil currType) {
        requireAllNonNull(personIndex, moduleIndex);
        this.currType = currType;
        this.personIndex = personIndex.getZeroBased();
        this.moduleIndex = eventIndex.getZeroBased();
        this.occasionIndex = eventIndex.getZeroBased();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> personsList = model.getFilteredPersonList();
        if (currType == TypeUtil.MODULE) {
            List<Module> moduleList = model.getFilteredModuleList();

            if (personIndex >= personsList.size()
                    || moduleIndex >= moduleList.size()) {
                throw new CommandException(MESSAGE_INCORRECT_INDEX);
            }

            Person personToRemove = personsList.get(personIndex);
            Module moduleToRemove = moduleList.get(moduleIndex);

            if (!moduleToRemove.getStudents().contains(personToRemove)
                    || !personToRemove.getModuleList().contains(moduleToRemove)) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            AttendanceListUtil.delinkPersonModule(model, personToRemove, moduleToRemove);
            model.commitAddressBook();
            return new CommandResult(MESSAGE_SUCCESS_REMOVE_FROM_MODULE);

        } else if (currType == TypeUtil.OCCASION) {
            List<Occasion> occasionList = model.getFilteredOccasionList();

            if (personIndex >= personsList.size()
                    || occasionIndex >= occasionList.size()) {
                throw new CommandException(MESSAGE_INCORRECT_INDEX);
            }

            Person personToRemove = personsList.get(personIndex);
            Occasion occasionToRemove = occasionList.get(occasionIndex);

            if (!occasionToRemove.getAttendanceList().contains(personToRemove)
                    || !personToRemove.getOccasionList().contains(occasionToRemove)) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            AttendanceListUtil.delinkPersonOccasion(model, personToRemove, occasionToRemove);
            model.commitAddressBook();
            return new CommandResult(MESSAGE_SUCCESS_REMOVE_FROM_OCCASION);

        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    private TypeUtil getCurrType() {
        return currType;
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

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof TakeOutPersonCommand
                && currType.equals(((TakeOutPersonCommand) other).getCurrType())
                && personIndex == ((TakeOutPersonCommand) other).getPersonIndex()
                && moduleIndex == ((TakeOutPersonCommand) other).getModuleIndex()
                && occasionIndex == ((TakeOutPersonCommand) other).getOccasionIndex();
    }
}
