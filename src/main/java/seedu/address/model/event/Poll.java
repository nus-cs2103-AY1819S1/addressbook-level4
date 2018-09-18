package seedu.address.model.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import seedu.address.model.person.Person;

/**
 * Represents a poll associated with an event.
 */
public class Poll {
    private String pollName;
    private HashMap<String, LinkedList<Person>> pollData;

    /**
     * Creates a new Poll object
     * @param pollName The name of the poll
     */
    public Poll(String pollName) {
        this.pollName = pollName;
        pollData = new HashMap<>();
    }

    /**
     * Add an option into the poll
     * @param option The string representing the option to be added
     */
    public void addOption(String option) {
        LinkedList<Person> personList = new LinkedList<>();
        pollData.put(option, personList);
    }

    /**
     * Adds the vote of a user into an option
     */
    public void addVote(String option, Person person) {
        pollData.get(option).add(person);
    }

    /**
     * Retrieves most popular options by number of votes.
     */
    public LinkedList<String> getHighest() {
        TreeMap<Integer, LinkedList<String>> frequency = new TreeMap<>();
        pollData.forEach((k, v) -> {
            if (!frequency.containsKey(v.size())) {
                LinkedList<String> options = new LinkedList<>();
                options.add(k);
                frequency.put(v.size(), options);
            } else {
                frequency.get(v.size()).add(k);
            }
        });
        return frequency.lastEntry().getValue();
    }
}
