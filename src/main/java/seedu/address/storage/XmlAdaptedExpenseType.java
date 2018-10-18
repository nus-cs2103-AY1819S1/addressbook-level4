package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenses.ExpenseType;

/**
 * JAXB-friendly adapted version of Expense.
 */
public class XmlAdaptedExpenseType {

    public static final String MESSAGE_NUMBER_MISSING = "ExpenseType's number field is missing!";
    public static final String MESSAGE_NAME_MISSING = "ExpenseType's name field is missing!";
    public static final String MESSAGE_COST_MISSING = "ExpenseType's cost field is missing!";

    @XmlElement (required = true)
    private String itemNumber;
    @XmlElement (required = true)
    private String itemName;
    @XmlElement (required = true)
    private Double itemCost;

    /**
     * Constructs an XmlAdaptedExpenseType.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExpenseType() {}

    /**
     * Constructs an XmlAdaptedExpenseType with the given fields.
     *  @param itemNumber The menu number of the item.
     * @param itemName The cost incurred in this expense.
     * @param itemCost The date and time of this transaction.
     */
    public XmlAdaptedExpenseType(String itemNumber, String itemName, Double itemCost) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemCost = itemCost;
    }

    /**
     * Converts an ExpenseType object into JAXB-friendly form.
     *
     * @param source The ExpenseType object to be converted.
     */
    public XmlAdaptedExpenseType(ExpenseType source) {
        this.itemNumber = source.getItemNumber();
        this.itemName = source.getItemName();
        this.itemCost = source.getItemCost();
    }

    /**
     * Converts this object into the proper ExpenseType object.
     * @return The ExpenseType representation of this object.
     */
    public ExpenseType toModelType() throws IllegalValueException {
        if (itemNumber == null) {
            throw new IllegalValueException(MESSAGE_NUMBER_MISSING);
        }
        if (itemName == null) {
            throw new IllegalValueException(MESSAGE_NAME_MISSING);
        }
        if (itemCost == null) {
            throw new IllegalValueException(MESSAGE_COST_MISSING);
        }
        try {
            return new ExpenseType(itemNumber, itemName, itemCost);
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(iae.getMessage());
        }
    }
}
