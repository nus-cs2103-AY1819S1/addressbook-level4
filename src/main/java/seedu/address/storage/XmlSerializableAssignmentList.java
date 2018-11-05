package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AssignmentList;
import seedu.address.model.ReadOnlyAssignmentList;
import seedu.address.model.project.Assignment;

/**
 * An Immutable AssignmentList that is serializable to XML format
 */
@XmlRootElement(name = "assignmentlist")
public class XmlSerializableAssignmentList {

    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Assignments list contains duplicate assignment(s).";

    @XmlElement
    private List<XmlAdaptedAssignment> assignments;

    /**
     * Creates an empty XmlSerializableAssignment.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAssignmentList() {
        assignments = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAssignmentList(ReadOnlyAssignmentList src) {
        this();
        assignments.addAll(src.getAssignmentList()
                .stream().map(XmlAdaptedAssignment::new).collect(Collectors.toList()));
    }

    /**
     * Converts this assignmentlist into the model's {@code AssignmentList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedAssignment}.
     */
    public AssignmentList toModelType() throws IllegalValueException {
        AssignmentList assignmentList = new AssignmentList();
        for (XmlAdaptedAssignment p : assignments) {
            Assignment assignment = p.toModelType();
            if (assignmentList.hasAssignment(assignment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }
            assignmentList.addAssignment(assignment);
        }
        return assignmentList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAssignmentList)) {
            return false;
        }
        return assignments.equals(((XmlSerializableAssignmentList) other).assignments);
    }
}
