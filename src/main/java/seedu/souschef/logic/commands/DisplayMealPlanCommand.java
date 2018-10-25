package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.ui.Ui;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

public class DisplayMealPlanCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD_SHOW = "showMeal";
    public static final String COMMAND_WORD_HIDE = "hideMeal";

    public static final String MESSAGE_SUCCESS_SHOW = "showing all meal plans.";
    public static final String MESSAGE_SUCCESS_HIDE = "hide meal plans.";

    private final Ui ui;

    private final String mode;

    public DisplayMealPlanCommand(Ui ui, String mode) {
        this.ui = ui;
        this.mode = mode;
    }

    @Override
    public CommandResult execute(CommandHistory history) {


        if(mode.equals("show")) {
            ui.showMealPlanListPanel();
            return new CommandResult(String.format(MESSAGE_SUCCESS_SHOW, history.getContext().toLowerCase()));
        } else {
            ui.hideMealPlanListPanel();
            return new CommandResult(String.format(MESSAGE_SUCCESS_HIDE, history.getContext().toLowerCase()));

        }
    }
}
