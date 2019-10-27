package seedu.address.testutil;

import seedu.address.model.entry.EntryDescription;

/**
 * a class to get typical entry description.
 */
public class TypicalEntryDescription {
    // Description for an entry under category ~work
    public static final String FINANCIAL_HACK_AWARD = "Attained 2011 best Performance Award";
    public static final String SE_PRINCIPLE = "Adhered to high quality development standards "
            + "while delivering solutions on time and on budget";
    public static final String CONSULTATION_WITH_CUSTOMERS = "Consulted regularly with customers "
            + "for regular updates on user requirements";

    // Description for an entry under category ~education

    public static EntryDescription getTypicalEntryDescription() {
        EntryDescription description = new EntryDescription();
        description.addBullet(FINANCIAL_HACK_AWARD);
        description.addBullet(SE_PRINCIPLE);
        description.addBullet(CONSULTATION_WITH_CUSTOMERS);

        return description;
    }
}
