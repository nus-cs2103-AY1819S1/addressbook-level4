package seedu.address.model.encryption;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.ExpenseField;

public abstract class EncryptedExpenseField<T extends ExpenseField> {
    private String encryptedString;

    public EncryptedExpenseField(String encryptedString) {
        this.encryptedString = encryptedString;
    }

    public EncryptedExpenseField(T src, String key) throws IllegalValueException {
        this.encryptedString = EncryptionUtil.encryptString(src.toString(), key);
    }

    public abstract T getDecrypted(String key) throws IllegalValueException;

    public String getEncryptedString() {
        return encryptedString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other.getClass().equals(this.getClass()) // instanceof handles nulls
                && encryptedString.equals(((EncryptedExpenseField) other).encryptedString)); // state check
    }
}
