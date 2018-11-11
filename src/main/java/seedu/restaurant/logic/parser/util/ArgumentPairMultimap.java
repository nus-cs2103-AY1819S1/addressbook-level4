package seedu.restaurant.logic.parser.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores mapping of 2 consecutive argument values found in the entire argument string.
 * Each key is an odd-indexed argument value (eg first, third, fifth ... argument) and must be mapped to its subsequent
 * even-indexed argument value (eg second, forth, ... argument).
 * Keys are unique, but two keys may map to duplicate values.
 */
public class ArgumentPairMultimap {

    /** Odd arguments mapped to its following argument **/
    private final Map<String, String> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with previous argument in this map.
     * Assumes the map does not contain a mapping for the key when a new value is added into the map.
     *
     * @param firstArg   Argument key with which its following argument is associated
     * @param secondArg  Argument value to be associated with the previous argument key
     */
    public void put(String firstArg, String secondArg) {
        argMultimap.put(firstArg, secondArg);
    }

    /**
     * Returns a defensive copy of {@code argMultimap}.
     */
    public Map<String, String> getArgMultimap() {
        return new HashMap<>(argMultimap);
    }

    /**
     * Returns true if this ArgumentPairMultimap equals {@code other}.
     */
    public boolean equals(Object other) {
        return this == other
                || (other instanceof ArgumentPairMultimap
                    && this.argMultimap.equals(((ArgumentPairMultimap) other).argMultimap));
    }
}
