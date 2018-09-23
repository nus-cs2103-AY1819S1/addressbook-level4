package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's block in the address book.
 */
public class Block {
    public static final String MESSAGE_BLOCK_CONSTRAINTS =
            "Block can contain either numbers of alphabets, and it should not be blank.";

    public final String value;

    /**
     * Constructs a {@code Block}.
     */
    public Block (String block) {
        requireNonNull(block);
        checkArgument(isValidBlock(block), MESSAGE_BLOCK_CONSTRAINTS);
        this.value = block;
    }

    /**
     * Returns true if given char is a valid block.
     */
    public static boolean isValidBlock(String test) {
        char testBlock = test.charAt(0);
        return Character.isLetterOrDigit(testBlock);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Block // instanceof handles null
                && value.equals(((Block) other).value));
    }
}
