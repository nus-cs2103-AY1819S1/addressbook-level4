package seedu.address.model.encryption;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.model.expense.Name;

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
