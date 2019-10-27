package seedu.address.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.observers.AwarenessService;
import seedu.address.logic.observers.CmdLineObserver;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.entry.ResumeEntry;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final CommandParser commandParser;
    private final List<CmdLineObserver> cmdLineObservers = new LinkedList<>();

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        commandParser = new CommandParser();
        cmdLineObservers.add(new AwarenessService(model));
    }

    @Override
    public void observe(String currentInput) {
        for (CmdLineObserver observer : cmdLineObservers) {
            observer.observe(currentInput).ifPresent(event -> raise(event));
        }
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = commandParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<ResumeEntry> getFilteredEntryList() {
        return model.getFilteredEntryList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
