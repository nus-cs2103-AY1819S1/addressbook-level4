package seedu.address.model.accounting;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.user.User;

/**
 * Represents a Debt in the address book.
 * Guarantees: Information are not null.
 *
 */

public class Debt {

    private User creditor;
    private User debtor;
    private Amount amount;
    private DebtId debtId;
    private DebtStatus status;

    public Debt(User creditor, User debtor, Amount amount) {
        requireAllNonNull(debtor, creditor, amount);
        this.creditor = creditor;
        this.debtor = debtor;
        this.amount = amount;
        this.debtId = new DebtId();
        this.status = DebtStatus.PENDING;
    }

    public Debt(User creditor, User debtor, Amount amount, DebtId debtId, DebtStatus status) {
        requireAllNonNull(debtor, creditor, amount);
        this.creditor = creditor;
        this.debtor = debtor;
        this.amount = amount;
        this.debtId = debtId;
        this.status = status;
    }
    public User getDebtor() {
        return this.debtor;
    }
    public User getCreditor() {
        return this.creditor;
    }
    public Amount getAmount() {
        return this.amount;
    }
    public DebtId getDebtId() {
        return debtId;
    }
    public DebtStatus getDebtStatus() {
        return this.status;
    }
    /**
     * Method to change a debt status.
     * @param changeTo represent the new debt status
     */
    public void changeDebtStatus(DebtStatus changeTo) {
        if (this.getDebtStatus().toString() == "PENDING" && changeTo == DebtStatus.ACCEPTED) {
            this.status = changeTo;
        } else if (this.getDebtStatus().toString() == "ACCEPTED" && changeTo == DebtStatus.CLEARED) {
            this.status = changeTo;
        }
    }
    /**
     * Method to check if two (Debt) objects equals.
     * @param other
     * @return a boolean of the result.
     */
    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }
        if (!(other instanceof Debt)) {
            return false;
        }
        Debt test = (Debt) other;
        return test != null
                && test.getCreditor().equals(this.getCreditor())
                && test.getDebtor().equals(this.getDebtor())
                && test.getAmount().equals(this.getAmount())
                && test.getDebtId().equals(this.getDebtId())
                && test.getDebtStatus().equals(this.getDebtStatus());
    }
    /**
     * Method to convert a debt to String.
     * @return a String representing the debt
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Creditor: ")
                .append(this.getCreditor().getUsername().toString())
                .append(" Debtor: ")
                .append(this.getDebtor().getUsername().toString())
                .append(" Amount: ")
                .append(String.valueOf(this.getAmount().toDouble()))
                .append(" Status ")
                .append(this.getDebtStatus().toString())
                .append(" ID: ")
                .append(this.getDebtId().toString());
        return builder.toString();
    }
}
