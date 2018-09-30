package seedu.address.model.entity;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * The underlying interface that is extended by all Persons, events and
 * modules.
 *
 * @author Ahan
 */
public abstract class Entity {
    private Set<Tag> tags; // TODO Change from Set to hashMap.

    public Set<Tag> getTags() {
        return tags;
    }
}
