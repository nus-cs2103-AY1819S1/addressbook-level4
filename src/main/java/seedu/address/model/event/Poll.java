//@@author theJrLinguist
package seedu.address.model.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a poll associated with an event.
 */
public class Poll extends AbstractPoll {
    private int id;
    private String pollName;
    private HashMap<String, UniquePersonList> pollData;

    /**
     * Creates a new Poll object
     * @param pollName The name of the poll
     */
    public Poll(int id, String pollName) {
        this.id = id;
        this.pollName = pollName;
        pollData = new HashMap<>();
    }

    /**
     * Creates a new poll object with the poll data.
     */
    public Poll(int id, String pollName, HashMap<String, UniquePersonList> pollData) {
        this.id = id;
        this.pollName = pollName;
        this.pollData = pollData;
    }

    public int getId() {
        return id;
    }

    public String getPollName() {
        return pollName;
    }

    public HashMap<String, UniquePersonList> getPollData() {
        return pollData;
    }

    /**
     * Add an option into the poll
     * @param option The string representing the option to be added
     */
    public void addOption(String option) {
        UniquePersonList personList = new UniquePersonList();
        pollData.put(option, personList);
    }
}
