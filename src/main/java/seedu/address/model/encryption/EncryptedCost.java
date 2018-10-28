package seedu.address.model.encryption;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Cost;

public class EncryptedCost extends EncryptedExpenseField<Cost> {
    public EncryptedCost(String encryptedString) {
        super(encryptedString);
    }

    public EncryptedCost(Cost src, String key) throws IllegalValueException {
        super(src, key);
    }

    @Override
    public Cost getDecrypted(String key) throws IllegalValueException {
        return new Cost(EncryptionUtil.decryptString(getEncryptedString(), key));
    }
}
