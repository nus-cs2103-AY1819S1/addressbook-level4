package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.budget.Budget;


//@author winsonhys
/**
 * JAXB-friendly adapted version of the Budget.
 */

public class XmlAdaptedBudget {

    @XmlElement
    private double budgetCap;
    @XmlElement
    private double currentExpenses;

    /**
     * Constructs an XmlAdaptedBudget.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBudget() {
    }

    /**
     * Constructs a {@code XmlAdaptedBudget} with the given {@code budgetCap} and {@code currentExpenses}.
     */
    public XmlAdaptedBudget(double budgetCap, double currentExpenses) {
        this.budgetCap = budgetCap;
        this.currentExpenses = currentExpenses;
    }

    /**
     * Converts a given Budget into this class for JAXB use.
     *
     * @param source source budget
     */
    public XmlAdaptedBudget(Budget source) {
        this.budgetCap = source.getBudgetCap();
        this.currentExpenses = source.getCurrentExpenses();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     */
    public Budget toModelType() {

        return new Budget(this.budgetCap, currentExpenses);
    }


}
