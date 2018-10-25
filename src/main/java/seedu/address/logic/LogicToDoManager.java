package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandToDo;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ToDoListParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelToDo;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * The main LogicToDoManager of the app.
 */
public class LogicToDoManager extends ComponentManager implements LogicToDo {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final ModelToDo modelToDo;
    private final CommandHistory history;
    private final ToDoListParser toDoListParser;

    public LogicToDoManager(ModelToDo modelToDo) {
        this.modelToDo = modelToDo;
        history = new CommandHistory();
        toDoListParser = new ToDoListParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            CommandToDo commandToDo = toDoListParser.toDoParseCommand(commandText);
            return commandToDo.execute(modelToDo, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<ToDoListEvent> getFilteredToDoListEventList() {
        return modelToDo.getFilteredToDoListEventList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
