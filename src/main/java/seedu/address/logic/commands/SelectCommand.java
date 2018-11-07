package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.MODULE;
import static seedu.address.commons.util.TypeUtil.PERSON;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToModuleListRequestEvent;
import seedu.address.commons.events.ui.JumpToOccasionListRequestEvent;
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * Selects a person identified using it's displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the entry identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String MESSAGE_SELECT_MODULE_SUCCESS = "Selected Module: %1$s";
    public static final String MESSAGE_SELECT_OCCASION_SUCCESS = "Selected Occasion: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getActiveType().equals(PERSON)) {
            List<Person> filteredPersonList = model.getFilteredPersonList();
            if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            EventsCenter.getInstance().post(new JumpToPersonListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
        } else if (model.getActiveType().equals(MODULE)) {
            List<Module> filteredModuleList = model.getFilteredModuleList();
            if (targetIndex.getZeroBased() >= filteredModuleList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            }
            EventsCenter.getInstance().post(new JumpToModuleListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_MODULE_SUCCESS, targetIndex.getOneBased()));
        } else {
            List<Occasion> filteredOccasionList = model.getFilteredOccasionList();
            if (targetIndex.getZeroBased() >= filteredOccasionList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
            }
            EventsCenter.getInstance().post(new JumpToOccasionListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_OCCASION_SUCCESS, targetIndex.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
