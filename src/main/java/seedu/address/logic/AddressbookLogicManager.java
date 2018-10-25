package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.AddressbookComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressbookModel;
import seedu.address.model.person.Person;

/**
 * The main AddressbookLogicManager of the app.
 */
public class AddressbookLogicManager extends AddressbookComponentManager implements AddressbookLogic {
    private final Logger logger = LogsCenter.getLogger(AddressbookLogicManager.class);

    private final AddressbookModel addressbookModel;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public AddressbookLogicManager(AddressbookModel addressbookModel) {
        this.addressbookModel = addressbookModel;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(addressbookModel, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressbookModel.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
