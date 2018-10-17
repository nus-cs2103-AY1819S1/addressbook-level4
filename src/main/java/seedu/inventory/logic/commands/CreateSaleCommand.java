package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;

/**
 * Adds a sale to the sale list.
 */
public class CreateSaleCommand extends Command {
    public static final String COMMAND_WORD = "createSale";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a sale to the system. "
            + "Parameters: "
            + PREFIX_SKU + "SKU "
            + PREFIX_QUANTITY + "QUANTITY";
    public static final String MESSAGE_FAILED = "Sale creation failed. SKU %1$s not found.";
    public static final String MESSAGE_SUCCESS = "New sale created, sale ID %1$s, item %2$s.";

    private final Sku sku;
    private final Quantity quantity;

    /**
     * Creates an CreateSaleCommand to add the specified {@code Item}
     */
    public CreateSaleCommand(Sku sku, Quantity quantity) {
        requireAllNonNull(sku, quantity);

        this.sku = sku;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        SaleId saleId = new SaleId(model.getSaleList().getNextSaleId());
        Item item = model.getInventory().getItemBySku(sku.toString());
        SaleDate saleDate = new SaleDate(formatter.format(date));

        if (item == null) {
            throw new CommandException(String.format(MESSAGE_FAILED, sku.toString()));
        }

        Sale sale = new Sale(saleId, item, quantity, saleDate);

        model.createSale(sale);

        int newIntQuantity = Integer.parseInt(item.getQuantity().toString()) - Integer.parseInt(quantity.toString());
        Quantity newQuantity = new Quantity(Integer.toString(newIntQuantity));

        // Deduct item quantity
        model.updateItem(item, new Item(item.getName(), item.getPrice(), newQuantity, item.getSku(),
                item.getImage(), item.getTags()));

        return new CommandResult(String.format(MESSAGE_SUCCESS, saleId.toString(), item.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateSaleCommand // instanceof handles nulls
                && sku.equals(((CreateSaleCommand) other).sku)
                && quantity.equals(((CreateSaleCommand) other).quantity));
    }
}
