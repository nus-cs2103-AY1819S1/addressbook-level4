package seedu.address.model.entry;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * API of Taggable Object
 */
public interface Taggable {
    Set<Tag> getTags();
}
