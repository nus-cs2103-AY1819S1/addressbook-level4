package seedu.address.model.cca;

import seedu.address.model.budget.Budget;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Generate a Committee CCA.
 */
public class CommitteeCca extends Cca {

    /**
     * Every identity field must be present and not null.
     * Data field can be null.
     */
    public CommitteeCca(Tag ccaName, Person head, Person viceHead, Budget budget) {
        super(ccaName, head, viceHead, budget);
    }

    /**
     * Alternative constructor.
     * In the event that there is no vice-head in the Committee CCA.
     * {@viceHead} will be assigned null
     */
    public CommitteeCca(Tag ccaName, Person head, Budget budget) {
        super(ccaName, head, budget);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("CCA: ")
            .append(getCcaName())
            .append("\nHead: ")
            .append(getHead())
            .append("\tVice-Head: ")
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
