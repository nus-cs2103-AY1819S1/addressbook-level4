package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import seedu.address.model.tag.exceptions.HasOverlapException;

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
        checkArgument(hasAnyOverlap(oldMap), MESSAGE_MAP_INSERT_CONSTRAINT);
        oldMap.getTagMap().forEach((key, value) -> tagMap.put(key, value));
    }

    /**
     * An overloaded constructor that enables to create a TagMap with the values
     * from another raw Map of TagKey and TagValue pairs.
     *
     * @param oldMap The other raw map to insert into this TagMap.
     */
    public TagMap(Map<TagKey, TagValue> oldMap) {
        requireNonNull(oldMap);
        checkArgument(hasAnyOverlap(oldMap), MESSAGE_MAP_INSERT_CONSTRAINT);
        oldMap.forEach((key, value) -> tagMap.put(key, value));
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
     * Adds all tags within the TagMap toAdd to this TagMap's
     * HashMap.
     *
     * @param toAdd The other TagMap to append to this TagMap.
     */
    public void addAll(TagMap toAdd) {
        requireNonNull(toAdd);
        if (hasAnyOverlap(toAdd)) {
            throw new HasOverlapException();
        }

        Iterator it = toAdd.getTagMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            tagMap.put((TagKey) pair.getKey(), (TagValue) pair.getValue());
        }
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
        if (tagMap.containsKey(key)) {
            return tagMap.remove(key);
        }

        return new TagValue();
    }

    /**
     * Two TagMaps have an overlap if and only if they contain
     * one common key even though the key maps to different
     * values.
     *
     * @param otherMap The other map comparing to.
     * @return the result of whether otherMap has any overlap to this tagMap.
     */
    private boolean hasAnyOverlap(TagMap otherMap) {
        Iterator it = otherMap.getTagMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (tagMap.containsKey(pair.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff otherMap contains at least one TagKey
     * whose hash is the same as a TagKey in this TagMap.
     *
     * @param otherMap The other map to compare to.
     * @return A boolean symbolizing whether there is any overlap.
     */
    public boolean hasAnyOverlap(Map<TagKey, TagValue> otherMap) {
        Iterator it = otherMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (tagMap.containsKey(pair.getKey())) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(TagKey key) {
        return tagMap.containsKey(key);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagMap
                    && ((TagMap) other).getTagMap().equals(tagMap));
    }

    @Override
    public int hashCode() {
        return tagMap.hashCode();
    }

}
