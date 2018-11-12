package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.accounting.Debt;
import seedu.address.model.friend.Friendship;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Restaurant;

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
    public boolean isCurrentlyLoggedIn() {
        return model.isCurrentlyLoggedIn();
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
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return model.getFilteredRestaurantList();
    }

    @Override
    public ObservableList<Friendship> getFriendRequestsList() {
        return model.getFriendRequestsList();
    }

    @Override
    public ObservableList<Friendship> getFriendsList() {
        return model.getFriendsList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return model.getGroupList();
    }

    @Override
    public ObservableList<Group> getGroupRequestList() {
        return model.getGroupRequestList();
    }

    @Override
    public ObservableList<Jio> getJioList() {
        return model.getJioList();
    }

    @Override
    public ObservableList<Debt> getDebtList() {
        return model.getDebtList();
    }

    @Override
    public ObservableList<Debt> getCreditorList() {
        return model.getCreditorList();
    }

    @Override
    public ObservableList<Debt> getDebtorList() {
        return model.getDebtorList();
    }

    @Override
    public ObservableList<Debt> getDebtRequestReceived() {
        return model.getDebtRequestReceived();
    }

    @Override
    public ObservableList<Debt> getDebtRequestSent() {
        return model.getDebtRequestSent();
    }

    @Override
    public void debtListing(ObservableList<Debt> debt) {
        model.debtListing(debt);
    }

    @Override
    public void groupListing(ObservableList<Group> groupList) {
        model.groupListing(groupList);
    }

    @Override
    public void friendListing(ObservableList<Friendship> friendList) {
        model.friendListing(friendList);
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
