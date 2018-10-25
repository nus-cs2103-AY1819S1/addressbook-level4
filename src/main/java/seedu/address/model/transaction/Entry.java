package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

//@@uthor ericyjw
/**
 * Represents a Transaction Entry in the budget book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * @author ericyjw
 */
public class Entry {
    public static final String MESSAGE_ENTRY_CONSTRAINTS = "Entry should contain a valid entry number, date, amount " +
        "and remarks";

    public static final String ENTRY_NUM_VALIDATION_REGEX = "[1-9][0-9]*";

    private Integer entryNum;
    private Date date;
    private Amount amount;
    private Remarks remarks;

    public Entry(int entryNum, Date date, Amount amount, Remarks remarks) {
//        requireAllNonNull(entryNum, date, amount, remarks);
        this.entryNum = entryNum;
        this.date = date;
        this.amount = amount;
        this.remarks = remarks;
    }

    public Entry(String entryNum, String date, String amount, String remarks) {
        requireAllNonNull(entryNum, date, amount, remarks);
        this.entryNum = Integer.valueOf(entryNum);
        this.date = new Date(date);
        this.amount = new Amount(Integer.valueOf(amount));
        this.remarks = new Remarks(remarks);
    }

    public int getEntryNum() {
        return entryNum;
    }

    public Date getDate() {
        return date;
    }

    public String getDateValue() {
        return this.date.getDate();
    }

    public Amount getAmount() {
        return amount;
    }

    public int getAmountValue() {
        return this.amount.getAmount();
    }

    public Remarks getRemarks() {
        return remarks;
    }

    public String getRemarkValue() {
        return this.remarks.getRemarks();
    }

    /**
     * Update the Transaction Date of a specific transaction entry in a specific CCA
     * @param toUpdate the new date to be updated
     */
    public void updateDate(Optional<Date> toUpdate) {
        Date date = toUpdate.get();
        this.date = date;
    }

    /**
     * Update the Transaction Amount of a specific transaction entry in a specific CCA
     * @param toUpdate the new amount to be updated
     */
    public void updateAmount(Optional<Amount> toUpdate) {
        Amount amount = toUpdate.get();
        this.amount = amount;
    }

    /**
     * Update the Transaction Remark of a specific transaction remark in a specific CCA
     * @param toUpdate the new amount to be updated
     */
    public void updateRemarks(Optional<Remarks> toUpdate) {
        Remarks remarks = toUpdate.get();
        this.remarks = remarks;
    }

    /**
     * Returns true if both Entry are the same.
     */
    public boolean isSameEntry(Entry otherEntry) {
        if (otherEntry == this) {
            return true;
        }

        return false;
    }

    public static boolean isValidEntry(String entryNum, String date, String amount, String remarks) {
        return entryNum.matches(ENTRY_NUM_VALIDATION_REGEX)
            && Date.isValidDate(date)
            && Amount.isValidAmount(amount)
            && Remarks.isValidRemark(remarks);
    }

    public static boolean isValidEntry(Entry e) {
        return String.valueOf(e.getEntryNum()).matches(ENTRY_NUM_VALIDATION_REGEX)
            && Date.isValidDate(e)
            && Amount.isValidAmount(e)
            && Remarks.isValidRemark(e);
    }
}


