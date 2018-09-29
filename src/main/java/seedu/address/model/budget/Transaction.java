package seedu.address.model.budget;

/**
 * Represent the transaction log.
 */
public class Transaction {
    private String log;

    public Transaction() {
        this.log = null;
    }

    public String getTransactionLog() {
        return log;
    }

    /**
     * Update the transaction log by appending new entry
     * @param entry new transaction entry
     */
    public void updateTransaction(String entry) {
        StringBuilder sb = new StringBuilder(this.log);
        sb.append(entry);
    }

    /**
     * Returns true if both ccas have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherBudget = (Transaction) other;
        return otherBudget.getTransactionLog().equals(getTransactionLog());
    }
}
