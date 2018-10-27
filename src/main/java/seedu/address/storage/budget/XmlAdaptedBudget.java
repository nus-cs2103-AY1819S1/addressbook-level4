package seedu.address.storage.budget;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import seedu.address.model.budget.TotalBudget;
import seedu.address.storage.storageutil.LocalDateTimeAdapter;


//@author winsonhys
/**
 * JAXB-friendly adapted version of the TotalBudget.
 */

public class XmlAdaptedBudget {

    @XmlElement
    private double budgetCap;
    @XmlElement
    private double currentExpenses;
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime nextRecurrence;
    @XmlElement
    private long numberOfSecondsToRecurAgain;




    /**
     * Constructs an XmlAdaptedBudget.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBudget() {
    }

    /**
     * Converts a given TotalBudget into this class for JAXB use.
     *
     * @param source source totalBudget
     */
    public XmlAdaptedBudget(TotalBudget source) {
        this.budgetCap = source.getBudgetCap();
        this.currentExpenses = source.getCurrentExpenses();
        this.nextRecurrence = source.getNextRecurrence();
        this.numberOfSecondsToRecurAgain = source.getNumberOfSecondsToRecurAgain();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     */
    public TotalBudget toModelType() {
        return new TotalBudget(this.budgetCap, currentExpenses, this.nextRecurrence, this.numberOfSecondsToRecurAgain);
    }



}
