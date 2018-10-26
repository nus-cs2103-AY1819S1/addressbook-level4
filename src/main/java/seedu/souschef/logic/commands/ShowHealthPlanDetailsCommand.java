package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.UniqueType;
import seedu.souschef.ui.Ui;

/**
 * command extension to show details for indexed healthplan
 */
public class ShowHealthPlanDetailsCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "showDetails";

    public static final String MESSAGE_SUCCESS = "showing details of plan %1$s.";

    private final Ui ui;

    private final String index;


    public ShowHealthPlanDetailsCommand(Ui ui, String index) {
        this.ui = ui;
        this.index = (index.trim());
    }


    @Override
    public CommandResult execute(CommandHistory history) {
        ui.showHealthPlanDetails(Integer.parseInt(this.index));
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.index));

    }

}
