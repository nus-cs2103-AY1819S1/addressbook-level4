package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.common.Mark;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {

    public static final String DEFAULT_NAME = "A1";
    public static final String DEFAULT_DEADLINE = "01/01/0001";
    public static final String DEFAULT_WEIGHT = "50";
    public static final String DEFAULT_MAXMARK = "100";
    public static final String DEFAULT_UID = null;

    private AssignmentName name;
    private Deadline deadline;
    private Weight weight;
    private Mark maxMark;
    private String uniqueId;

    public AssignmentBuilder() {
        name = new AssignmentName(DEFAULT_NAME);
        deadline = new Deadline(DEFAULT_DEADLINE);
        weight = new Weight(DEFAULT_WEIGHT);
        maxMark = new Mark(DEFAULT_MAXMARK);
        uniqueId = DEFAULT_UID;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        name = assignmentToCopy.getName();
        deadline = assignmentToCopy.getDeadline();
        weight = assignmentToCopy.getWeight();
        maxMark = assignmentToCopy.getMaxMark();
        uniqueId = assignmentToCopy.getUniqueId();
    }

    /**
     * Sets the {@code AssignmentName} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withName(String name) {
        this.name = new AssignmentName(name);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withDeadline(String date) {
        this.deadline = new Deadline(date);
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }

    /**
     * Sets the {@code maxMark} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withMaxMark(String mark) {
        this.maxMark = new Mark(mark);
        return this;
    }

    /**
     * Sets the {@code maxMark} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withUniqueId(String id) {
        this.uniqueId = id;
        return this;
    }

    /**
     * Return the {@code Assignment} that we are building.
     */
    public Assignment build() {
        if (uniqueId == null) {
            return new Assignment(name, weight, deadline, maxMark);
        } else {
            return new Assignment(name, weight, deadline, maxMark, uniqueId);
        }
    }

}
