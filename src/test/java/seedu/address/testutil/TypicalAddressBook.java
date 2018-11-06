package seedu.address.testutil;

import static seedu.address.testutil.TypicalModules.addTypicalModules;
import static seedu.address.testutil.TypicalOccasions.addTypicalOccasions;
import static seedu.address.testutil.TypicalPersons.addTypicalPersons;

import seedu.address.model.AddressBook;

/**
 * A utility class containing three typical lists to be used in tests.
 * @Author waytan
 */
public class TypicalAddressBook {
    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        addTypicalPersons(ab);
        addTypicalModules(ab);
        addTypicalOccasions(ab);
        return ab;
    }
}
