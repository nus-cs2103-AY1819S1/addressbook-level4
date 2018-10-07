package seedu.jxmusic.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.jxmusic.commons.core.ComponentManager;
import seedu.jxmusic.commons.core.LogsCenter;
import seedu.jxmusic.logic.commands.Command;
import seedu.jxmusic.logic.commands.CommandResult;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.logic.parser.AddressBookParser;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Model;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
