package seedu.address.model.encryption;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Date;

public class EncryptedDate extends EncryptedExpenseField<Date> {
    public EncryptedDate(String encryptedString) {
        super(encryptedString);
    }

    public EncryptedDate(Date src, String key) throws IllegalValueException {
        super(src, key);
    }

    @Override
    public Date getDecrypted(String key) throws IllegalValueException {
        return new Date(EncryptionUtil.decryptString(getEncryptedString(), key));
    }
}
