package seedu.address.model.encryption;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Category;

public class EncryptedCategory extends EncryptedExpenseField<Category> {

    public EncryptedCategory(String encryptedString) {
        super(encryptedString);
    }

    public EncryptedCategory(Category src, String key) throws IllegalValueException {
        super(src, key);
    }

    @Override
    public Category getDecrypted(String key) throws IllegalValueException {
        return new Category(EncryptionUtil.decryptString(getEncryptedString(), key));
    }
}
