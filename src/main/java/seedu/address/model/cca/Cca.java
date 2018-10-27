package seedu.address.model.cca;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.transaction.Entry;
import seedu.address.model.person.Name;

//@@author ericyjw
/**
 * Represents a Cca in the cca book.
 *
 * @author ericyjw
 */
public class Cca {

    // Identity fields
    private final CcaName name;
    private final Name head;
    private final Name viceHead;

    // Data fields
    private final Budget budget;
    private final Spent spent;
    private final Outstanding outstanding;
    private final Set<Entry> transactionEntries;

    /**
     * Constructor for Cca where there is a vice-head.
     * Every identity field must be present and not null.
     * Data field can be null.
     *
     * @param name name of Cca
     * @param head name of head of Cca
     * @param viceHead name of viceHead of Cca
     * @param budget budget of Cca
     * @param spent amount spent by the Cca
     * @param outstanding outstanding amount of the Cca
     * @param transactionEntries transaction entries of the Cca
     */
    public Cca(CcaName name, Name head, Name viceHead, Budget budget, Spent spent, Outstanding outstanding,
               Set<Entry> transactionEntries) {
        requireAllNonNull(name, head, viceHead, budget, spent, outstanding);
        this.name = name;
        this.head = head;
        this.viceHead = viceHead;
        this.budget = budget;
        this.spent = spent;
        this.outstanding = outstanding;
        this.transactionEntries = transactionEntries;
    }

    /**
     * Alternative constructor for Cca where there is a vice-head.
     * Every identity field must be present and not null.
     *
     * @param name name of the Cca
     * @param head name of the head of the Cca
     * @param budget budget of the Cca
     * @param spent amount spent by the Cca
     * @param outstanding outstanding amount of the Cca
     * @param transactionEntries transaction entries of the Cca
     */
    public Cca(CcaName name, Name head, Budget budget, Spent spent, Outstanding outstanding,
               Set<Entry> transactionEntries) {
        requireAllNonNull(name, head, budget, spent, outstanding);
        this.name = name;
        this.head = head;
        this.viceHead = null;
        this.budget = budget;
        this.spent = spent;
        this.outstanding = outstanding;
        this.transactionEntries = transactionEntries;
    }

    /**
     * Create a Cca object with a given {@code CcaName}.
     *
     * @param ccaName the name of the Cca to be create
     */
    public Cca(String ccaName) {
        CcaName name = new CcaName(ccaName);
        this.name = name;
        this.head = null;
        this.viceHead = null;
        this.budget = null;
        this.spent = null;
        this.outstanding = null;
        this.transactionEntries = null;
    }

    /**
     * Create a Cca object with a given {@code CcaName} and a given {@code Budget}.
     * Used for creating new Cca with a given budget from the {@code CreateCcaCommand}.
     *
     * @param ccaName the name of the Cca to be created
     * @param budget the budget given to the Cca
     */
    public Cca(CcaName ccaName, Budget budget) {
        this.name = ccaName;
        this.head = new Name("-");
        this.viceHead = new Name("-");
        this.budget = budget;
        this.spent = new Spent(0);
        this.outstanding = new Outstanding(budget.getBudgetValue());
        this.transactionEntries = new LinkedHashSet<>();
    }

    public String getCcaName() {
        return name.getNameOfCca();
    }

    public String getHeadName() {
        return head.fullName;
    }

    public String getViceHeadName() {
        return viceHead.fullName;
    }

    public int getBudgetAmount() {
        return budget.getBudgetValue();
    }

    public int getSpentAmount() {
        return spent.getSpentValue();
    }

    public int getOutstandingAmount() {
        return outstanding.getOutstandingValue();
    }

    /**
     * Returns an immutable Transaction Entry set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Entry> getEntries() {
        return Collections.unmodifiableSet(transactionEntries);
    }

    public CcaName getName() {
        return this.name;
    }

    public Name getHead() {
        return head;
    }

    public Name getViceHead() {
        return viceHead;
    }

    public Spent getSpent() {
        return spent;
    }

    public Outstanding getOutstanding() {
        return outstanding;
    }

    public Budget getBudget() {
        return budget;
    }

    /**
     * Returns the number of transaction entries in the Cca.
     */
    public int getEntrySize() {
        return this.transactionEntries.size();
    }

    /**
     * Adds a {@code newEntry} to the transaction list in the Cca.
     *
     * @param newEntry the new transaction entry to be added
     */
    public Cca addNewTransaction(Entry newEntry) {
        this.transactionEntries.add(newEntry);

        return this;
    }

    /**
     * Returns true if both Ccas have the same name
     * This defines a weaker notion of equality between two CCAs.
     *
     * @param toCheck name of the CCA to be checked
     */
    public boolean isSameCca(Cca toCheck) {
        if (toCheck == this) {
            return true;
        }

        return toCheck != null
            && toCheck.getCcaName().equals(getCcaName());
    }

    /**
     * Returns true if both CCAs have the same identity and data fields.
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
        return otherCca.name.equals(this.name)
            && otherCca.head.equals(this.head)
            && otherCca.viceHead.equals(this.viceHead)
            && otherCca.budget.equals(this.budget);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, head, viceHead, budget);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCcaName())
            .append(" Head: ")
            .append(getHeadName())
            .append(" Vice-Head: ")
            .append(getViceHeadName())
            .append(" Budget: ")
            .append(getBudgetAmount())
            .append(" Outstanding: ")
            .append(getOutstandingAmount());
        return builder.toString();
    }
}
