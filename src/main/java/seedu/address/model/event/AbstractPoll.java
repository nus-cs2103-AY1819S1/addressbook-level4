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

public class AbstractPoll {
    private int id;
    private String pollName;
    private HashMap<String, UniquePersonList> pollData;

    /**
     * Adds the vote of a user into an option
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
        pollData.forEach((k, v) -> v.setPerson(target, editedPerson));
    }

    /**
     * Retrieves most popular options by number of votes.
     */
    public LinkedList<String> getPopularOptions() {
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

    /**
     * Returns a string representation of the poll
     */
    public String displayPoll() {
        String title = String.format("Poll %1$s: %2$s", Integer.toString(id), pollName);
        String mostPopularEntries = "";
        if (!pollData.isEmpty()) {
            mostPopularEntries = "Most popular options:\n" + getPopularOptions().toString();
        }
        String data = displayPollData();
        return title + "\n" + mostPopularEntries + "\n\n" + data;
    }

    /**
     * Returns the poll data as a string identifying people by their names.
     */
    public String displayPollData() {
        HashMap<String, List<String>> displayData = new HashMap<>();
        pollData.forEach((k, v) -> {
            List<String> nameList = v.asUnmodifiableObservableList()
                    .stream()
                    .map(person -> person.getName().toString())
                    .collect(Collectors.toList());
            displayData.put(k, nameList);
        });
        String result = "";
        for (Map.Entry<String, List<String>> entry : displayData.entrySet()) {
            result += entry.getKey() + ":\n" + entry.getValue().toString() + "\n";
        }
        return result;
    }
}
