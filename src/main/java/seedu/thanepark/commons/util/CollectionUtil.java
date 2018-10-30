package seedu.thanepark.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Returns true if {@code itemsToCompare} contains any elements in {@code keyItems}
     * @param itemsToCompare
     * @param keyItems
     * @return
     */
    public static boolean containsAny(Set<?> itemsToCompare, Set<?> keyItems) {
        return Stream.of(itemsToCompare).anyMatch(item -> {
            for (Object keyItem : keyItems) {
                if (item.contains(keyItem)) {
                    return true;
                }
            }
            return false;
        });
    }
}
