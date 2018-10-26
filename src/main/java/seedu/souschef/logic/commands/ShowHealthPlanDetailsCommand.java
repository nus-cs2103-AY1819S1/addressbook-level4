package seedu.souschef.logic.commands;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.events.ui.BrowserUiChangedEvent;
import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.UniqueType;


/**
 * command extension to show details for indexed healthplan
 */
public class ShowHealthPlanDetailsCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "showDetails";

    public static final String MESSAGE_SUCCESS = "showing details of plan %1$s.";

    //private final Ui ui;

    private final String index;


    public ShowHealthPlanDetailsCommand(String index) {

        this.index = (index.trim());
    }


    @Override
    public CommandResult execute(CommandHistory history) {
        EventsCenter.getInstance().post(new BrowserUiChangedEvent("healthplanDetails",
                Integer.parseInt(this.index)));

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.index));

    }

}
