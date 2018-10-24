package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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

    private final Integer entryNum;
    private final Date date;
    private final Amount amount;
    private final Remarks remarks;

    public Entry(int entryNum, Date date, Amount amount, Remarks remarks) {
        requireAllNonNull(entryNum, date, amount, remarks);
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

    public Amount getAmount() {
        return amount;
    }

    public Remarks getRemarks() {
        return remarks;
    }

    public static boolean isValidEntry(String entryNum, String date, String amount, String remarks) {
        boolean b1 =  entryNum.matches(ENTRY_NUM_VALIDATION_REGEX);
        boolean b2 = Date.isValidDate(date);
        boolean b3 = Amount.isValidAmount(amount);
        boolean b4 = Remarks.isValidRemark(remarks);

        return b1 && b2 && b3 && b4;
    }

    public static boolean isValidEntry(Entry e) {
        return String.valueOf(e.getEntryNum()).matches(ENTRY_NUM_VALIDATION_REGEX)
            && Date.isValidDate(e)
            && Amount.isValidAmount(e)
            && Remarks.isValidRemark(e);
    }
}


