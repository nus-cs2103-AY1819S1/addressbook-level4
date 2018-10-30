package seedu.souschef.logic.commands;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.events.ui.BrowserUiChangedEvent;
import seedu.souschef.logic.History;
import seedu.souschef.model.UniqueType;

/**
 * command extension to show all meal plans logged into the system
 */
public class DisplayMealPlanCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD_SHOW = "showMeal";
    public static final String COMMAND_WORD_HIDE = "hideMeal";

    public static final String MESSAGE_SUCCESS_SHOW = "Showing all meal plans.";
    public static final String MESSAGE_SUCCESS_HIDE = "Hide meal plans.";

    private final String mode;

    public DisplayMealPlanCommand(String mode) {

        this.mode = mode;
    }

    @Override
    public CommandResult execute(History history) {

        //this if else is to determine messages to show
        if (mode.equals("show")) {
            EventsCenter.getInstance().post(new BrowserUiChangedEvent("mealPlanList"));
            return new CommandResult(String.format(MESSAGE_SUCCESS_SHOW, history.getContext()));
        } else {
            EventsCenter.getInstance().post(new BrowserUiChangedEvent("hide"));
            return new CommandResult(String.format(MESSAGE_SUCCESS_HIDE, history.getContext()));

        }
    }
}
