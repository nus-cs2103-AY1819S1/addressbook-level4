package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.MODULE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowModuleRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.util.AttendanceListUtil;

/**
 * Lists all modules in the address book to the user.
 */
public class ListModuleCommand extends Command {

    public static final String COMMAND_WORD = "listmodule";

    public static final String MESSAGE_SUCCESS = "Listed all modules";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        model.setActiveType(MODULE);
        EventsCenter.getInstance().post(new ShowModuleRequestEvent());
        AttendanceListUtil.postClearEvent();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
