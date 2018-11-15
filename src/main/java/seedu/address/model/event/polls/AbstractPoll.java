//@@author theJrLinguist
package seedu.address.model.event.polls;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * An abstract poll class.
 */
public abstract class AbstractPoll {
    private static final Logger logger = LogsCenter.getLogger(AbstractPoll.class);
    private static final String POLL_HEADER = "Poll %1$s: %2$s";
    private static final String MOST_POPULAR_OPTIONS_HEADER = "Most popular options:\n";
    protected int id;
    protected String pollName;
    protected HashMap<String, UniquePersonList> pollData;

    /**
     * Constructor which is used by subclasses.
     * @param id the unique id of the poll in the event.
     * @param pollName the name of the poll.
     * @param pollData the data of the poll.
     */
    AbstractPoll(int id, String pollName, HashMap<String, UniquePersonList> pollData) {
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
     * Adds the vote of a user into an option.
     * A DuplicatePersonException is thrown if the person has already voted.
     */
    public void addVote(String option, Person person) throws IllegalArgumentException, DuplicatePersonException {
        if (!pollData.containsKey(option)) {
            throw new IllegalArgumentException();
        }
        pollData.get(option).add(person);
    }

    /**
     * Updates the person in the poll votes.
     */
    public void updatePerson(Person target, Person editedPerson) {
        for (Map.Entry<String, UniquePersonList> entry : pollData.entrySet()) {
            if (entry.getValue().contains(target)) {
                try {
                    entry.getValue().setPerson(target, editedPerson);
                } catch (PersonNotFoundException e) {
                    logger.info("Person is not in voter list.");
                } catch (DuplicatePersonException e) {
                    logger.info("The same person is already in the voter list.");
                }
            }
        }
    }

    /**
     * Retrieves most popular options by number of votes.
     * Returns an empty list if no users have voted.
     */
    private LinkedList<String> getPopularOptions() {
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
        if (frequency.lastEntry().getKey().equals(0)) {
            return new LinkedList<>();
        }
        return frequency.lastEntry().getValue();
    }

    /**
     * Returns a string representation of the poll.
     */
    public String displayPoll() {
        String title = String.format(POLL_HEADER, Integer.toString(id), pollName);
        String mostPopularEntries = "";
        if (!pollData.isEmpty()) {
            mostPopularEntries = MOST_POPULAR_OPTIONS_HEADER + getPopularOptions().toString();
        }
        String data = displayPollData();
        return title + "\n" + mostPopularEntries + "\n\n" + data;
    }

    /**
     * Returns the poll data as a string identifying people by their names.
     */
    private String displayPollData() {
        HashMap<String, List<String>> displayData = new HashMap<>();
        pollData.forEach((k, v) -> {
            List<String> nameList = v.asUnmodifiableObservableList()
                    .stream()
                    .map(person -> person.getName().toString())
                    .collect(Collectors.toList());
            displayData.put(k, nameList);
        });
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : displayData.entrySet()) {
            result.append(entry.getKey() + ":\n" + entry.getValue().toString() + "\n");
        }
        return result.toString();
    }

    /**
     * Returns a copy of the poll data.
     */
    protected HashMap<String, UniquePersonList> copyData() {
        HashMap<String, UniquePersonList> dataCopy = new HashMap<>();
        for (Map.Entry<String, UniquePersonList> entry : pollData.entrySet()) {
            UniquePersonList newPersonList = new UniquePersonList();
            for (Person person : entry.getValue()) {
                newPersonList.add(person);
            }
            dataCopy.put(entry.getKey(), newPersonList);
        }
        return dataCopy;
    }

    /**
     * Deletes a person from the voter lists in the poll data.
     */
    public void deletePerson(Person target) {
        for (Map.Entry<String, UniquePersonList> entry : pollData.entrySet()) {
            if (entry.getValue().contains(target)) {
                entry.getValue().remove(target);
            }
        }
    }

    /**
     * Returns a deep copy of the AbstractPoll.
     */
    public abstract AbstractPoll copy();

    /**
     * Returns true if both polls have the same identity and name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AbstractPoll)) {
            return false;
        }

        AbstractPoll otherPoll = (AbstractPoll) other;
        return otherPoll.getId() == getId()
                && otherPoll.getPollName().equals(getPollName());
    }
}
