package seedu.modsuni.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.modsuni.commons.core.ComponentManager;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.logic.commands.Command;
import seedu.modsuni.logic.commands.CommandResult;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.logic.parser.ModsUniParser;
import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.user.User;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final ModsUniParser modsUniParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        modsUniParser = new ModsUniParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = modsUniParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Module> getFilteredDatabaseModuleList() {
        return model.getFilteredDatabaseModuleList();
    }

    @Override
    public ObservableList<Module> getFilteredStagedModuleList() {
        return model.getFilteredStagedModuleList();
    }

    @Override
    public ObservableList<Module> getFilteredTakenModuleList() {
        return model.getFilteredTakenModuleList();
    }

    @Override
    public User getCurrentUser() {
        return model.getCurrentUser();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
