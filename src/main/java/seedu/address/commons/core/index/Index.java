package seedu.address.commons.core.index;

/**
 * Represents a zero-based or one-based index.
 *
 * {@code Index} should be used right from the start (when parsing in a new user input), so that if the current
 * component wants to communicate with another component, it can send an {@code Index} to avoid having to know what
 * base the other component is using for its index. However, after receiving the {@code Index}, that component can
 * convert it back to an int if the index will not be passed to a different component again.
 */
public class Index {
    private int zeroBasedIndex;
    private int zeroBasedIndex2;

    /**
     * Index can only be created by calling {@link Index#fromZeroBased(int)} or
     * {@link Index#fromOneBased(int)}.
     */
    private Index(int zeroBasedIndex) {
        if (zeroBasedIndex < 0) {
            throw new IndexOutOfBoundsException();
        }

        this.zeroBasedIndex = zeroBasedIndex;
    }

    /**
     * Index can only be created by calling {@link Index#fromZeroBased(int)} or
     * {@link Index#fromOneBased(int)}.
     */
    private Index(int zeroBasedIndex, int zeroBasedIndex2) {
        if (zeroBasedIndex < 0 || zeroBasedIndex2 < 0) {
            throw new IndexOutOfBoundsException();
        }

        this.zeroBasedIndex = zeroBasedIndex;
        this.zeroBasedIndex2 = zeroBasedIndex2;
    }

    public int getZeroBased() {
        return zeroBasedIndex;
    }
    public int getZeroBased2() {
        return zeroBasedIndex2;
    }

    public int getOneBased() {
        return zeroBasedIndex + 1;
    }
    public int getOneBased2() {
        return zeroBasedIndex2 + 1;
    }

    /**
     * Creates a new {@code Index} using a zero-based index.
     */
    public static Index fromZeroBased(int zeroBasedIndex) {
        return new Index(zeroBasedIndex);
    }

    /**
     * Creates a new {@code Index} using a zero-based index.
     */
    public static Index fromZeroBased(int zeroBasedIndex, int zeroBasedIndex2) {
        return new Index(zeroBasedIndex, zeroBasedIndex2);
    }

    /**
     * Creates a new {@code Index} using a one-based index.
     */
    public static Index fromOneBased(int oneBasedIndex) {
        return new Index(oneBasedIndex - 1);
    }

    /**
     * Creates a new {@code Index} using a one-based index.
     */
    public static Index fromOneBased(int oneBasedIndex, int oneBasedIndex2) {
        return new Index(oneBasedIndex - 1, oneBasedIndex2 - 1);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Index // instanceof handles nulls
                && zeroBasedIndex == ((Index) other).zeroBasedIndex
                && zeroBasedIndex2 == ((Index) other).zeroBasedIndex2); // state check
    }
}
