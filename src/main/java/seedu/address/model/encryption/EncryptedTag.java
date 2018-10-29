package seedu.address.model.encryption;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Expense's tag in Expense Tracker in it's encrypted form.
 * Guarantees: immutable;
 */
public class EncryptedTag {
    private String encryptedString;

    public EncryptedTag(String encryptedString) {
        requireNonNull(encryptedString);
        this.encryptedString = encryptedString;
    }

    public EncryptedTag(Tag src, String key) throws IllegalValueException {
        requireAllNonNull(src, key);
        this.encryptedString = EncryptionUtil.encryptString(src.tagName, key);
    }

    public Tag getDecrypted(String key) throws IllegalValueException {
        return new Tag(EncryptionUtil.decryptString(encryptedString, key));
    }

    public String getEncryptedString() {
        return encryptedString;
    }
}
