package seedu.address.model.cca;

import seedu.address.model.budget.Budget;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Generate a sports CCA.
 */
public class SportsCca extends Cca {

    /**
     * Every identity field must be present and not null.
     * Data field can be null.
     */
    public SportsCca(Tag ccaName, Person captain, Person viceCapt, Budget budget) {
        super(ccaName, captain, viceCapt, budget);
    }

    /**
     * Alternative constructor.
     * In the event that there is no vice-captain in the sports CCA.
     * {@viceHead} will be assigned null
     */
    public SportsCca(Tag ccaName, Person captain, Budget budget) {
        super(ccaName, captain, budget);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("CCA: ")
            .append(getCcaName())
            .append("\nCaptain: ")
            .append(getHead())
            .append("\tVice-Captain: ")
            .append(getViceHead())
            .append("\nGiven Budget: ")
            .append(getGivenBudget())
            .append("\nSpent: ")
            .append(getSpent())
            .append("\tOutstanding: ")
            .append(getOutstanding())
            .append("\nTransaction History:\n")
            .append(getTransactionLog());
        return builder.toString();
    }
}
