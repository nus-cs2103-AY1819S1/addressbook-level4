package seedu.address.storage.budget;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.budget.Budget;

/**
 * JAXB-friendly adapted version of the Budget. Used as a base class for other Budget types
 */

public abstract class XmlAdaptedBudget {
    @XmlElement
    protected double budgetCap;

    @XmlElement
    protected double currentExpenses;

    /**
     * Constructs an XmlAdaptedBudget.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBudget() {
    }

    public XmlAdaptedBudget(Budget source) {
        this.budgetCap = source.getBudgetCap();
        this.currentExpenses = source.getCurrentExpenses();
    }

    /**
     * Converts this jaxb-friendly adapted budget object into the model's Budget object.
     */
    public abstract Budget toModelType();
}
