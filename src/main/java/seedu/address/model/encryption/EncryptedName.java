package seedu.address.model.encryption;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Name;

/**
 * Represents a Expense's name in Expense Tracker in it's encrypted form.
 * Guarantees: immutable;
 */
public class EncryptedName extends EncryptedExpenseField<Name> {
    public EncryptedName(String encryptedString) {
        super(encryptedString);
    }

    public EncryptedName(Name src, String key) throws IllegalValueException {
        super(src, key);
    }

    @Override
    public Name getDecrypted(String key) throws IllegalValueException {
        return new Name(EncryptionUtil.decryptString(getEncryptedString(), key));
    }
}
