package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a map of key value tag pairs associated with a person, module or occasion.
 * TODO phase out the old tagging system with the new one.
 */
public class TagMap {
    public static final String MESSAGE_MAP_DUPLICATE_CONSTRAINT = "The following key already exists.";
    public static final String MESSAGE_MAP_INSERT_CONSTRAINT = "The following tagMap cannot be added"
                                                                +
                                                                " as it contains already inserted values";

    private HashMap<TagKey, TagValue> tagMap = new HashMap<>();

    public TagMap() {
        // Do nothing.
    }

    /**
     * An overloaded constructor that enables to create a TagMap with the values
     * from another TagMap.
     *
     * @param oldMap the other TagMap to add.
     */
    public TagMap(TagMap oldMap) {
        requireNonNull(oldMap);
        checkArgument(isSimilar(oldMap), MESSAGE_MAP_INSERT_CONSTRAINT);
        oldMap.getTagMap().forEach((key, value) -> this.tagMap.put(key, value));
    }

    public HashMap<TagKey, TagValue> getTagMap() {
        return this.tagMap;
    }

    /**
     * Puts the designated key value pair into this TagMap
     * if it doesn't exist and both key and value are non-null.
     *
     * @param key The key to be added.
     * @param value The value to be added.
     */
    public void put(TagKey key, TagValue value) {
        requireNonNull(key);
        requireNonNull(value);
        checkArgument(contains(key), MESSAGE_MAP_DUPLICATE_CONSTRAINT);
        tagMap.put(key, value);
    }

    /**
     * Removes the designated key from this TagMap
     * if the key is non-null and exists.
     *
     * @param key The desired key to be removed.
     * @return The value the key maps to.
     */
    public TagValue remove(TagKey key) {
        requireNonNull(key);
        if (this.tagMap.containsKey(key)) {
            return this.tagMap.remove(key);
        }

        return new TagValue();
    }

    /**
     * Two TagMaps are similar if and only if they contain
     * one common key even though the key maps to different
     * values.
     *
     * @param otherMap The other map comparing to.
     * @return the result of whether otherMap isSimilar to this tagMap.
     */
    private boolean isSimilar(TagMap otherMap) {
        Iterator it = otherMap.getTagMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (this.tagMap.containsKey(pair.getKey())) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(TagKey key) {
        return this.tagMap.containsKey(key);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagMap
                    && ((TagMap) other).getTagMap().equals(this.tagMap));
    }

    @Override
    public int hashCode() {
        return tagMap.hashCode();
    }

}
