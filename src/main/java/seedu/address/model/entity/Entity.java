package seedu.address.model.entity;

import seedu.address.model.tag.Tag;


import java.util.Set;

/**
 * The underlying interface that is extended by all Persons, events and
 * modules.
 *
 * @author Ahan
 */
public abstract class Entity {
    Set<Tag> tags; // TODO Change from Set to hashMap.

    public Set<Tag> getTags() {
        return tags;
    }
}
