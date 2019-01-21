package seedu.inventory.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.commands.item.EditItemCommand;
import seedu.inventory.logic.commands.purchaseorder.EditPurchaseOrderCommand;
import seedu.inventory.logic.commands.staff.EditStaffCommand;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.NameContainsKeywordsPredicate;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffNameContainsKeywordsPredicate;
import seedu.inventory.testutil.EditItemDescriptorBuilder;
import seedu.inventory.testutil.purchaseorder.EditPurchaseOrderDescriptorBuilder;
import seedu.inventory.testutil.staff.EditStaffDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_OPPO = "Oppo F7 Dual SIM";
    public static final String VALID_NAME_SONY = "Sony Xperia XZ2";
    public static final String VALID_PRICE_OPPO = "999.99";
    public static final String VALID_PRICE_SONY = "123.45";
    public static final String VALID_QUANTITY_OPPO = "11111111";
    public static final String VALID_QUANTITY_SONY = "22222222";
    public static final String VALID_SKU_DIFFERENT = "different-sku";
    public static final String VALID_SKU_OPPO = "oppo-f7-dual_sim";
    public static final String VALID_SKU_SONY = "sony-xperia-xz2";
    public static final String VALID_IMAGE_OPPO = "docs/images/oppo.jpg";
    public static final String VALID_IMAGE_SONY = "docs/images/sony.jpg";
    public static final String VALID_TAG_SMARTPHONE = "smartphone";
    public static final String VALID_TAG_GADGET = "gadget";

    public static final String NAME_DESC_OPPO = " " + PREFIX_NAME + VALID_NAME_OPPO;
    public static final String NAME_DESC_SONY = " " + PREFIX_NAME + VALID_NAME_SONY;
    public static final String PRICE_DESC_OPPO = " " + PREFIX_PRICE + VALID_PRICE_OPPO;
    public static final String PRICE_DESC_SONY = " " + PREFIX_PRICE + VALID_PRICE_SONY;
    public static final String QUANTITY_DESC_OPPO = " " + PREFIX_QUANTITY + VALID_QUANTITY_OPPO;
    public static final String QUANTITY_DESC_SONY = " " + PREFIX_QUANTITY + VALID_QUANTITY_SONY;
    public static final String SKU_DESC_OPPO = " " + PREFIX_SKU + VALID_SKU_OPPO;
    public static final String SKU_DESC_SONY = " " + PREFIX_SKU + VALID_SKU_SONY;
    public static final String SKU_DESC_DIFFERENT = " " + PREFIX_SKU + VALID_SKU_DIFFERENT;
    public static final String IMAGE_DESC_OPPO = " " + PREFIX_IMAGE + VALID_IMAGE_OPPO;
    public static final String IMAGE_DESC_SONY = " " + PREFIX_IMAGE + VALID_IMAGE_SONY;
    public static final String TAG_DESC_GADGET = " " + PREFIX_TAG + VALID_TAG_GADGET;
    public static final String TAG_DESC_SMARTPHONE = " " + PREFIX_TAG + VALID_TAG_SMARTPHONE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "iPhone&"; // '&' not allowed in names
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "99."; // invalid decimal
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "911a"; // 'a' not allowed in quantities
    public static final String INVALID_SKU_DESC = " " + PREFIX_SKU + "bob!yahoo"; // '!' not allowed in SKUs
    public static final String INVALID_IMAGE_DESC = " " + PREFIX_IMAGE; // empty string not allowed for image path
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "tech*"; // '*' not allowed in tags

    public static final String VALID_NAME_ZUL = "Muhammad Zulqarnain";
    public static final String VALID_USERNAME_ZUL = "zulq9";
    public static final String VALID_PASSWORD_ZUL = "zulq9999";
    public static final String VALID_NAME_DARREN = "Darren";
    public static final String VALID_USERNAME_DARREN = "darren96";
    public static final String VALID_PASSWORD_DARREN = "darrennnn";
    public static final String VALID_USERNAME_USER = "user";

    public static final String USERNAME_DESC_ZUL = " " + PREFIX_USERNAME + VALID_USERNAME_ZUL;
    public static final String NAME_DESC_ZUL = " " + PREFIX_NAME + VALID_NAME_ZUL;
    public static final String PASSWORD_DESC_ZUL = " " + PREFIX_PASSWORD + VALID_PASSWORD_ZUL;
    public static final String USERNAME_DESC_DARREN = " " + PREFIX_USERNAME + VALID_USERNAME_DARREN;
    public static final String NAME_DESC_DARREN = " " + PREFIX_NAME + VALID_NAME_DARREN;
    public static final String PASSWORD_DESC_DARREN = " " + PREFIX_PASSWORD + VALID_PASSWORD_DARREN;
    public static final String ROLE_DESC_USER = " " + PREFIX_ROLE + "user";
    public static final String ROLE_DESC_ADMIN = " " + PREFIX_ROLE + "admin";

    public static final String INVALID_USERNAME_DESC = " " + PREFIX_USERNAME + "z*lq(";
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + "as..sd-f!";
    public static final String INVALID_STAFFNAME_DESC = " " + PREFIX_NAME + "Zulq@rn@!n";
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "Storeman";

    public static final String VALID_REQUIRED_DATE_OPPO = "2098-12-31";
    public static final String VALID_REQUIRED_DATE_SONY = "2098-12-30";
    public static final String VALID_SUPPLIER_OPPO = "Mobile Square Pte Ltd";
    public static final String VALID_SUPPLIER_SONY = "Sony Corporation";
    public static final String VALID_STATUS_OPPO = "PENDING";
    public static final String VALID_STATUS_SONY = "PENDING";

    public static final String REQUIRED_DATE_DESC_OPPO = " " + PREFIX_REQDATE + VALID_REQUIRED_DATE_OPPO;
    public static final String REQUIRED_DATE_DESC_SONY = " " + PREFIX_REQDATE + VALID_REQUIRED_DATE_SONY;
    public static final String SUPPLIER_DESC_OPPO = " " + PREFIX_SUPPLIER + VALID_SUPPLIER_OPPO;
    public static final String SUPPLIER_DESC_SONY = " " + PREFIX_SUPPLIER + VALID_SUPPLIER_SONY;

    public static final String INVALID_REQUIRED_DATE_DESC = " " + PREFIX_REQDATE + "2012.2.21"; // incorrect format
    public static final String INVALID_SUPPLIER_DESC = " " + PREFIX_SUPPLIER + ""; // not allowed empty string

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditItemCommand.EditItemDescriptor DESC_OPPO;
    public static final EditItemCommand.EditItemDescriptor DESC_SONY;

    static {
        DESC_OPPO = new EditItemDescriptorBuilder().withName(VALID_NAME_OPPO).withPrice(VALID_PRICE_OPPO)
                .withQuantity(VALID_QUANTITY_OPPO).withSku(VALID_SKU_OPPO).withImage(VALID_IMAGE_OPPO)
                .withTags(VALID_TAG_GADGET).build();
        DESC_SONY = new EditItemDescriptorBuilder().withName(VALID_NAME_SONY).withPrice(VALID_PRICE_SONY)
                .withQuantity(VALID_QUANTITY_SONY).withSku(VALID_SKU_SONY).withImage(VALID_IMAGE_SONY)
                .withTags(VALID_TAG_SMARTPHONE, VALID_TAG_GADGET).build();
    }

    public static final EditStaffCommand.EditStaffDescriptor DESC_ZUL;
    public static final EditStaffCommand.EditStaffDescriptor DESC_DARREN;

    static {
        DESC_ZUL = new EditStaffDescriptorBuilder().withName(VALID_NAME_ZUL).withUsername(VALID_USERNAME_ZUL)
                .withPassword(VALID_PASSWORD_ZUL).withRole(ROLE_DESC_ADMIN).build();
        DESC_DARREN = new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN).withUsername(VALID_USERNAME_DARREN)
                .withPassword(VALID_PASSWORD_DARREN).withRole(ROLE_DESC_ADMIN).build();
    }

    public static final EditPurchaseOrderCommand.EditPoDescriptor DESC_OPPO_PO;
    public static final EditPurchaseOrderCommand.EditPoDescriptor DESC_SONY_PO;

    static {
        DESC_OPPO_PO = new EditPurchaseOrderDescriptorBuilder().withQuantity(VALID_QUANTITY_OPPO)
                .withRequiredDate(VALID_REQUIRED_DATE_OPPO).withSupplier(VALID_SUPPLIER_OPPO).build();
        DESC_SONY_PO = new EditPurchaseOrderDescriptorBuilder().withQuantity(VALID_QUANTITY_SONY)
                .withRequiredDate(VALID_REQUIRED_DATE_SONY).withSupplier(VALID_SUPPLIER_SONY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the inventory and the filtered item list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Inventory expectedInventory = new Inventory(actualModel.getInventory());
        List<Item> expectedItemFilteredList = new ArrayList<>(actualModel.getFilteredItemList());
        List<PurchaseOrder> expectedPoFilteredList = new ArrayList<>(actualModel.getFilteredPurchaseOrderList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedInventory, actualModel.getInventory());
            assertEquals(expectedItemFilteredList, actualModel.getFilteredItemList());
            assertEquals(expectedPoFilteredList, actualModel.getFilteredPurchaseOrderList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the item at the given {@code targetIndex} in the
     * {@code model}'s inventory.
     */
    public static void showItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredItemList().size());

        Item item = model.getFilteredItemList().get(targetIndex.getZeroBased());
        final String[] splitName = item.getName().fullName.split("\\s+");
        model.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredItemList().size());
    }

    /**
     * Deletes the first item in {@code model}'s filtered list from {@code model}'s inventory.
     */
    public static void deleteFirstItem(Model model) {
        Item firstItem = model.getFilteredItemList().get(0);
        model.deleteItem(firstItem);
        model.commitInventory();
    }

    /**
     * Updates {@code model}'s filtered list to show only the staff at the given {@code targetIndex} in the
     * {@code model}'s inventory.
     */
    public static void showStaffAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStaffList().size());

        Staff staff = model.getFilteredStaffList().get(targetIndex.getZeroBased());
        final String[] splitName = staff.getStaffName().fullName.split("\\s+");
        model.updateFilteredStaffList(new StaffNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStaffList().size());
    }

    /**
     * Deletes the first staff in {@code model}'s filtered list from {@code model}'s inventory.
     */
    public static void deleteFirstStaff(Model model) {
        Staff firstStaff = model.getFilteredStaffList().get(0);
        model.deleteStaff(firstStaff);
        model.commitInventory();
    }

    /**
     * Deletes the first item in {@code model}'s filtered list from {@code model}'s inventory.
     */
    public static void deleteFirstPurchaseOrder(Model model) {
        PurchaseOrder firstPo = model.getFilteredPurchaseOrderList().get(0);
        model.deletePurchaseOrder(0);
        model.commitInventory();
    }

}
