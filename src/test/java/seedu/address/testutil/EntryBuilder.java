package seedu.address.testutil;

import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;

//@@author ericyjw
/**
 * A utility class to help with building {@code Entry} objects.
 */
public class EntryBuilder {
    public static final Integer DEFAULT_ENTRY_NUM = 1;
    public static final Date DEFAULT_DATE = new Date("05.10.2016");
    public static final Amount DEFAULT_AMOUNT = new Amount(-300);
    public static final Remarks DEFAULT_REMARKS = new Remarks("Celebration");

    private Integer entryNum;
    private Date date;
    private Amount amount;
    private Remarks remarks;

    /**
     * Default Entrybuilder constructor.
     */
    public EntryBuilder() {
        this.entryNum = DEFAULT_ENTRY_NUM;
        this.date = DEFAULT_DATE;
        this.amount = DEFAULT_AMOUNT;
        this.remarks = DEFAULT_REMARKS;
    }

    /**
     * Initializes the EntryBuilder with the data of {@code entryToCopy}.
     */
    public EntryBuilder(Entry entryToCopy) {
        this.entryNum = entryToCopy.getEntryNum();
        this.date = entryToCopy.getDate();
        this.amount = entryToCopy.getAmount();
        this.remarks = entryToCopy.getRemarks();
    }

    /**
     * Sets the entry number of the {@code Entry} we are building.
     */
    public EntryBuilder withEntryNum(String entryNum) {
        this.entryNum = Integer.valueOf(entryNum);
        return this;
    }

    /**
     * Sets the date of the {@code Entry} we are building.
     */
    public EntryBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the amount of the {@code Entry} we are building.
     */
    public EntryBuilder withAmount(int amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the remarks of the {@code Entry} we are building.
     */
    public EntryBuilder withRemarks(String remarks) {
        this.remarks = new Remarks(remarks);
        return this;
    }

    public Entry build() {
        return new Entry(this.entryNum, this.date, this.amount, this.remarks);
    }

}
