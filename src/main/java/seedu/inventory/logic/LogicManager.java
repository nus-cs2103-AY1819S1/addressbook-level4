package seedu.inventory.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.parser.InventoryParser;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final InventoryParser inventoryParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        inventoryParser = new InventoryParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = inventoryParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        return model.getFilteredItemList();
    }

    @Override
    public ObservableList<PurchaseOrder> getFilteredPurchaseOrderList() {
        return model.getFilteredPurchaseOrderList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
