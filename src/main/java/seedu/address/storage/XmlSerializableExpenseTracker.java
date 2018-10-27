package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenseTracker;
import seedu.address.model.ReadOnlyExpenseTracker;
import seedu.address.model.expense.Expense;
import seedu.address.model.user.Password;
import seedu.address.storage.budget.XmlAdaptedBudget;

/**
 * An Immutable ExpenseTracker that is serializable to XML format
 */
@XmlRootElement(name = "expensetracker")
public class XmlSerializableExpenseTracker {

    public static final String MESSAGE_DUPLICATE_EXPENSE = "Expenses list contains duplicate expense(s).";

    @XmlElement
    private List<XmlAdaptedExpense> expenses;
    @XmlElement
    private XmlAdaptedUsername username;
    @XmlElement
    private XmlAdaptedBudget budget;
    @XmlElement
    private XmlAdaptedPassword password;

    /**
     * Creates an empty XmlSerializableExpenseTracker.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableExpenseTracker() {
        expenses = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableExpenseTracker(ReadOnlyExpenseTracker src) {
        this();
        this.username = new XmlAdaptedUsername(src.getUsername());
        this.password = src.getPassword().map(XmlAdaptedPassword::new).orElse(null);
        expenses.addAll(src.getExpenseList().stream().map(XmlAdaptedExpense::new).collect(Collectors.toList()));
        this.budget = new XmlAdaptedBudget(src.getMaximumTotalBudget());
    }

    /**
     * Converts this expensetracker into the model's {@code ExpenseTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedExpense}.
     */
    public ExpenseTracker toModelType() throws IllegalValueException {
        Optional<Password> passwordOptional = Optional.ofNullable(password).map(XmlAdaptedPassword::toModelType);
        ExpenseTracker expenseTracker = new ExpenseTracker(username.toModelType(), passwordOptional);
        for (XmlAdaptedExpense p : expenses) {
            Expense expense = p.toModelType();
            if (expenseTracker.hasExpense(expense)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXPENSE);
            }
            expenseTracker.addExpense(expense);
        }
        if (this.budget != null) {
            expenseTracker.modifyMaximumBudget(this.budget.toModelType());
        }
        return expenseTracker;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableExpenseTracker)) {
            return false;
        }
        return expenses.equals(((XmlSerializableExpenseTracker) other).expenses);
    }
}
