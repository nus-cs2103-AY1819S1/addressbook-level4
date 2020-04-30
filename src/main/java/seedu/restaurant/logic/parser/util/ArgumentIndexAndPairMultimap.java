package seedu.restaurant.logic.parser.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores mapping of indices to their respective argument pairs.
 * Each key must be associated with exactly two arguments forming a pair.
 * Keys are unique, but two keys may map to duplicate values for argument pairs.
 */
public class ArgumentIndexAndPairMultimap {

    /** Indices mapped to their respective argument pairs **/
    private final Map<Integer, Map<Prefix, String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with an integer {@code index} key in this map.
     * Assumes the map does not contain a mapping for the key when a new value
     *
     * @param index   Index key with which the specified argument pair value is to be associated
     * @param prefix   Prefix key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Integer index, Prefix prefix, String argValue) {
        Map<Prefix, String> argValues = getAllValues(index);
        argValues.put(prefix, argValue);
        argMultimap.put(index, argValues);
    }

    /**
     * Returns the value of {@code index}.
     */
    public Map<Prefix, String> getValue(int index) {
        return argMultimap.get(index);
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public Map<Prefix, String> getAllValues(Integer integer) {
        if (!argMultimap.containsKey(integer)) {
            return new HashMap<>();
        }
        return new HashMap<>(argMultimap.get(integer));
    }

    /**
     * Returns true if the argMultimap contains the {@code index} as a key.
     * @param index Index key
     */
    public boolean contains(int index) {
        return argMultimap.containsKey(index);
    }

    /**
     * Returns true if this ArgumentPairMultimap equals {@code other}.
     */
    public boolean equals(Object other) {
        return this == other
                || (other instanceof ArgumentIndexAndPairMultimap
                    && this.argMultimap.equals(((ArgumentIndexAndPairMultimap) other).argMultimap));
    }

}
