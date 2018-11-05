package seedu.restaurant.logic;

import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PASSWORD;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.restaurant.commons.core.ComponentManager;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.ExitCommand;
import seedu.restaurant.logic.commands.HelpCommand;
import seedu.restaurant.logic.commands.account.LoginCommand;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.logic.parser.RestaurantBookParser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.person.Person;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.salesrecord.SalesRecord;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final RestaurantBookParser restaurantBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        restaurantBookParser = new RestaurantBookParser();
    }

    //@@author AZhiKai
    private boolean isPublicCommand(Command command) {
        return command instanceof LoginCommand || command instanceof HelpCommand || command instanceof ExitCommand;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        String commandTextToLog = commandText;
        if (commandText.contains(PREFIX_PASSWORD.getPrefix())) {
            commandTextToLog = Password.maskPassword(commandText);
        }
        logger.info("----------------[USER COMMAND][" + commandTextToLog + "]");

        try {
            Command command = restaurantBookParser.parseCommand(commandText);

            if (!isPublicCommand(command) && !UserSession.isAuthenticated()) {
                throw new CommandException(Messages.MESSAGE_COMMAND_FORBIDDEN);
            }

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
    public ObservableList<Account> getFilteredAccountList() {
        return model.getFilteredAccountList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return model.getFilteredIngredientList();
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        return model.getFilteredItemList();
    }

    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return model.getFilteredReservationList();
    }

    @Override
    public ObservableList<SalesRecord> getFilteredRecordList() {
        return model.getFilteredRecordList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
