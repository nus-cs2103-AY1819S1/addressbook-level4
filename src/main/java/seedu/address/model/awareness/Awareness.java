package seedu.address.model.awareness;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;
import java.util.TreeMap;

import seedu.address.model.entry.ResumeEntry;

/**
 * This class represents all the Awareness information held by the application.
 * Currently, this includes:
 *     - A dictionary that maps slang to the corresponding full phrases. Slang includes acronyms.
 *         - Example: "cs" --> "computer science"
 *     - A tree set of all full phrases.
 */
public class Awareness {

    /** The dictionary manages the mappings between slang and full phrases. */
    private Dictionary dictionary;

    /** Maps an Event name to a Resume Entry, if any */
    private TreeMap<String, ResumeEntry> nameToEntryMappings;

    public Awareness(Dictionary dictionary, TreeMap<String, ResumeEntry> nameToEntryMappings) {
        requireAllNonNull(dictionary, nameToEntryMappings);

        this.dictionary = dictionary;
        this.nameToEntryMappings = nameToEntryMappings;

    }

    /** Constructor to create an empty Awareness object */
    public Awareness() {
        this(new Dictionary(), new TreeMap<String, ResumeEntry>());
    }

    /**
     * Given an expression, returns a possible Event name. An Event with this name may not actually exist.
     * For example, given the expression "cs", "computer science" is returned as an Event name.
     * However, "computer science" may not be an actual Event.
     *
     * Similarly, given the expression "sg cs innov", "singapore computer science innoventure workshop" may be returned.
     * This may be an actual Event.
     *
     * @param expression an expression is a String containing a combination of slang, partial phrases and full phrases.
     * @return the name of a possible Event, derived using the given expression and user defined contextual
     * information. If NO possible Event name is found, the original expression is returned.
     */
    public String getPossibleEventName(String expression) {
        return dictionary.getPossibleEventName(expression);
    }

    /**
     * Returns an Optional containing a ResumeEntry that matches the given possible event name.
     * Returns an empty Optional if no matching ResumeEntry is found.
     */
    public Optional<ResumeEntry> getContextualResumeEntry(String possibleEventName) {
        return Optional.ofNullable(nameToEntryMappings.get(possibleEventName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Awareness)) {
            return false;
        }

        Awareness otherAwareness = (Awareness) other;

        return dictionary.equals(otherAwareness.dictionary)
                 && nameToEntryMappings.equals(otherAwareness.nameToEntryMappings);
    }

}
