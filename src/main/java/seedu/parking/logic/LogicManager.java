package seedu.parking.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.parking.commons.core.ComponentManager;
import seedu.parking.commons.core.LogsCenter;
import seedu.parking.logic.commands.Command;
import seedu.parking.logic.commands.CommandResult;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.logic.parser.CarparkFinderParser;
import seedu.parking.logic.parser.exceptions.ParseException;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final CarparkFinderParser carparkFinderParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        carparkFinderParser = new CarparkFinderParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = carparkFinderParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Carpark> getFilteredCarparkList() {
        return model.getFilteredCarparkList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
