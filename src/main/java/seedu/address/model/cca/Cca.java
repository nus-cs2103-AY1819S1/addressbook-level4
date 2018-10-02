package seedu.address.model.cca;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.budget.Budget;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Cca {
    public static final String MESSAGE_CCA_CONSTRAINTS = "CCAs names should only contain letters and brackets";
    public static final String CCA_VALIDATION_REGEX = "\\p{Alpha} + \\p{Punct} +";

    // Identity fields
    private final Tag ccaName;
    private final Person head;
    private final Person viceHead;

    // Data fields
    private final Budget budget;

    /**
     * Every identity field must be present and not null.
     * Data field can be null.
     */
    public Cca(Tag ccaName, Person head, Person viceHead, Budget budget) {
        requireAllNonNull(ccaName, head, viceHead);
        this.ccaName = ccaName;
        this.head = head;
        this.viceHead = viceHead;
        this.budget = budget;
    }

    /**
     * Alternative constructor.
     * In the event that there is no vice-head in the CCA.
     * {@viceHead} will be assigned null
     */
    public Cca(Tag ccaName, Person head, Budget budget) {
        requireAllNonNull(ccaName, head);
        this.ccaName = ccaName;
        this.head = head;
        this.viceHead = null;
        this.budget = budget;
    }

    /**
     * Returns true if a given string is a valid CCA name.
     */
    public static boolean isValidCcaName(String test) {
        return test.matches(CCA_VALIDATION_REGEX);
    }

    public String getCcaName() {
        return ccaName.toString().replaceAll("\\p{P}", "");
    }

    public String getHead() {
        return head.getName().toString();
    }

    public String getViceHead() {
        if (!viceHead.equals(null)) {
            return viceHead.getName().toString();
        }
        return "";
    }

    public int getGivenBudget() {
        return budget.getGivenBudget();
    }

    public int getSpent() {
        return budget.getSpent();
    }

    public int getOutstanding() {
        return budget.getOutstanding();
    }

    public String getTransactionLog() {
        return budget.getTransactionLog();
    }

    /**
     * Returns true if both CCAs are the same.
     */
    public boolean isSameCca(Cca otherCca) {
        if (otherCca == this) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if both ccas have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Cca)) {
            return false;
        }

        Cca otherCca = (Cca) other;
        return otherCca.getCcaName().equals(getCcaName())
            && otherCca.getHead().equals(getHead())
            && otherCca.getViceHead().equals(getViceHead())
            && otherCca.budget.equals(this.budget);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ccaName, head, viceHead, budget);
    }



}

