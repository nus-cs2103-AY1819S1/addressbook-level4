package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.MODULE;
import static seedu.address.commons.util.TypeUtil.PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;
import seedu.address.model.util.AttendanceListUtil;

//@@author waytan
/**
 * Deletes an entry identified using its displayed index in the active list.
 */
public class DeleteCommand extends Command {
    //@@author waytan
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the entry identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_DELETE_OCCASION_SUCCESS = "Deleted Occasion: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        CommandResult commandResult;
        if (model.getActiveType().equals(PERSON)) {
            commandResult = deletePersonAndGetResult(model);
        } else if (model.getActiveType().equals(MODULE)) {
            commandResult = deleteModuleAndGetResult(model);
        } else {
            commandResult = deleteOccasionAndGetResult(model);
        }
        model.commitAddressBook();
        return commandResult;
    }


    /**
     * Deletes the person at the targeted index, and returns the command result.
     */
    private CommandResult deletePersonAndGetResult(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        AttendanceListUtil.removePersonFromAssociatedModules(model, personToDelete);
        AttendanceListUtil.removePersonFromAssociatedOccasions(model, personToDelete);
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * Deletes the module at the targeted index, and returns the command result.
     */
    private CommandResult deleteModuleAndGetResult(Model model) throws CommandException {
        List<Module> lastShownList = model.getFilteredModuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToDelete = lastShownList.get(targetIndex.getZeroBased());

        AttendanceListUtil.removeModuleFromAssociatedPersons(model, moduleToDelete);
        model.deleteModule(moduleToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    /**
     * Deletes the occasion at the targeted index, and returns the command result.
     */
    private CommandResult deleteOccasionAndGetResult(Model model) throws CommandException {
        List<Occasion> lastShownList = model.getFilteredOccasionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
        }

        Occasion occasionToDelete = lastShownList.get(targetIndex.getZeroBased());

        AttendanceListUtil.removeOccasionFromAssociatedPersons(model, occasionToDelete);

        model.deleteOccasion(occasionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_OCCASION_SUCCESS, occasionToDelete));
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
