package seedu.thanepark.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.thanepark.commons.core.ComponentManager;
import seedu.thanepark.commons.core.LogsCenter;
import seedu.thanepark.logic.commands.Command;
import seedu.thanepark.logic.commands.CommandResult;
import seedu.thanepark.logic.commands.exceptions.CommandException;
import seedu.thanepark.logic.parser.ThaneParkParser;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Ride;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final ThaneParkParser thaneParkParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        thaneParkParser = new ThaneParkParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = thaneParkParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Ride> getFilteredRideList() {
        return model.getFilteredRideList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
