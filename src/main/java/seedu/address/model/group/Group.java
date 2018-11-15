package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.model.Entity;
import seedu.address.model.UniqueList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

//@@author Happytreat
/**
 * Represents a Group in the address book.
 * Guarantees: Field values are validated, immutable.
 */
public class Group extends Entity {

    public static final String MESSAGE_GROUP_NO_DESCRIPTION =
            "No group description has been inputted.";

    /**Groups must have unique names.*/
    private final Name name;

    // Data fields
    private final String description;
    private UniqueList<Person> groupMembers;

    /**
     * Every field must be present and not null.
     */
    public Group(Name name, String description) {
        requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
        groupMembers = new UniqueList<>();
    }

    public Group(Name name, String description, UniqueList<Person> groupMembers) {
        requireAllNonNull(name, description, groupMembers);
        this.name = name;
        this.description = description;
        this.groupMembers = groupMembers;
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        if (description.equals("")) {
            return MESSAGE_GROUP_NO_DESCRIPTION;
        }
        return description;
    }

    public ObservableList<Person> getGroupMembers() {
        return groupMembers.asUnmodifiableObservableList();
    }

    /**
     * Returns all member of a group as a String.
     */
    public String printMembers() {
        Iterator<Person> itr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        int count = 1;
        while (itr.hasNext()) {
            builder.append(count).append(". ").append(itr.next().getName().fullName).append("\n");
            count += 1;
        }
        return builder.toString();
    }
    //@@author nigelngyy
    /**
     * Returns the string representation of the integer variable "day" used in listAvailableTimeslots
     * and listRankedAvailableTimeslots
     */
    private String dayToString(int day) {
        switch (day) {
        case 1:
            return "Monday";

        case 2:
            return "Tuesday";

        case 3:
            return "Wednesday";

        case 4:
            return "Thursday";

        case 5:
            return "Friday";

        case 6:
            return "Saturday";

        case 7:
            return "Sunday";

        default:
            return "Invalid day";
        }
    }

    /**
     * Returns all time slots where everyone among the group is available at as a String
     * in ascending order in terms of timing
     */
    public String listAllAvailableTimeslots() {
        int numOfPeople = groupMembers.getSize();
        if (numOfPeople == 0) {
            return "There are no members in the group";
        }

        Iterator<Person> personItr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        TreeSet<Integer> availableSlots = new TreeSet<>();
        boolean isFirstPerson = true;
        while (personItr.hasNext()) {
            Person currPerson = personItr.next();
            boolean[][] isFree = currPerson.getTimetable().getTimetable().getBooleanTimetableData();
            if (isFirstPerson) {
                for (int i = 1; i <= 7; i++) {
                    for (int j = 1; j <= 16; j++) {
                        if (isFree[i][j]) {
                            availableSlots.add(i * 100 + j);
                        }
                    }
                }
                isFirstPerson = false;
            } else {
                for (int i = 1; i <= 7; i++) {
                    for (int j = 1; j <= 16; j++) {
                        int currTimeslot = i * 100 + j;
                        if (availableSlots.contains(currTimeslot) && !isFree[i][j]) {
                            availableSlots.remove(currTimeslot);
                        }
                    }
                }
            }
        }
        if (availableSlots.size() == 0) {
            return "There are no slots at which everyone is available";
        }
        Iterator<Integer> slotsItr = availableSlots.iterator();
        while (slotsItr.hasNext()) {
            int currTimeslot2 = slotsItr.next();
            int day = currTimeslot2 / 100;
            int timing = (currTimeslot2 % 100 + 7) * 100;
            builder.append("Day: ");
            builder.append(dayToString(day));
            builder.append(" ").append("Time: ").append(Integer.toString(timing)).append("\n");
        }
        return builder.toString();
    }

    /**
     * Returns the time slots among the group as a String in descending order with respect to number of
     * people available and then ascending order in terms of timing, with the parameter being the
     * minimum number of people required to be available
     */
    public String listRankedAvailableTimeslots(int numberRequired) {
        int numOfPeople = groupMembers.getSize();
        if (numOfPeople == 0) {
            return "There are no members in the group";
        }
        Iterator<Person> personItr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        TreeMap<Integer, Integer> availableSlots = new TreeMap<>();
        while (personItr.hasNext()) {
            Person currPerson = personItr.next();
            boolean[][] isFree = currPerson.getTimetable().getTimetable().getBooleanTimetableData();
            for (int i = 1; i <= 7; i++) {
                for (int j = 1; j <= 16; j++) {
                    if (isFree[i][j]) {
                        int slot = i * 100 + j;
                        if (availableSlots.containsKey(slot)) {
                            int count = availableSlots.get(slot) + 1;
                            availableSlots.put(slot, count);
                        } else {
                            availableSlots.put(slot, 1);
                        }
                    }
                }
            }
        }
        if (availableSlots.size() == 0) {
            return "There are no slots with at least " + numberRequired + " person(s) available";
        }
        Map<Integer, Integer> sortedSlots = availableSlots.entrySet().stream()
                                                          .sorted(Collections.reverseOrder
                                                                  (Comparator.comparing(Entry::getValue)))
                                                          .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (
                                                                  e1, e2) -> e1, LinkedHashMap::new));
        int prev = 0;
        for (Integer key : sortedSlots.keySet()) {
            int currTimeslot = key;
            int day = currTimeslot / 100;
            int timing = (currTimeslot % 100 + 7) * 100;
            int availablePersons = sortedSlots.get(key);
            if (prev == 0 && availablePersons < numberRequired) {
                return "There are no slots with at least " + numberRequired + " person(s) available";
            }
            if (availablePersons < numberRequired) {
                return builder.toString();
            }
            if (availablePersons != prev) {
                builder.append("Number of people available: " + availablePersons + "\n");
                prev = availablePersons;
            }
            builder.append("Day: ");
            builder.append(dayToString(day));
            builder.append(" ").append("Time: ").append(Integer.toString(timing)).append("\n");
        }
        return builder.toString();
    }
    //@@author

    /**
     * Returns true if both groups of the same name.
     * For group, isSame is the same function as equals
     * since groups are uniquely identified by their names.
     */
    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName());
    }

    /**
     * Same as isSame because Groups are uniquely identified by their names.
     * TO NOTE: Used when deleting groups.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nDescription: ")
                .append(getDescription())
                .append("\nNumber of Members: ")
                .append(groupMembers.asUnmodifiableObservableList().size());
        return builder.toString();
    }
}
