package seedu.superta.testutil;

import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.UniqueAssignmentList;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.attendance.UniqueSessionList;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.UniqueStudentList;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * A utility class to help with building TutorialGroup objects.
 */
public class TutorialGroupBuilder {
    public static final String DEFAULT_NAME = "CS1101S Studio 04A";
    public static final String DEFAULT_ID = "04a";

    private String name;
    private String id;
    private UniqueStudentList students;
    private UniqueAssignmentList assignments;
    private UniqueSessionList sessions;

    public TutorialGroupBuilder() {
        name = DEFAULT_NAME;
        id = DEFAULT_ID;
        students = new UniqueStudentList();
        assignments = new UniqueAssignmentList();
        sessions = new UniqueSessionList();
    }

    /**
     * Initializes the TutorialGroupBuilder with the data of {@code source}.
     * @param source The source TutorialGroup.
     */
    public TutorialGroupBuilder(TutorialGroup source) {
        name = source.getName();
        id = source.getId();
        students = source.getStudents().clone();
        assignments = source.getAssignments().clone();
        sessions = source.getSessions().clone();
    }

    /**
     * Sets the name of the {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the ID of the {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Adds a {@code Student} to this {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder addStudent(Student student) {
        students.add(student);
        return this;
    }

    /**
     * Removes a {@code Student} from this {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder removeStudent(Student student) {
        students.remove(student);
        return this;
    }

    /**
     * Adds an {@code Assignment} from this {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder addAssignment(Assignment assignment) {
        assignments.add(assignment);
        return this;
    }

    /**
     * Removes an {@code Assignment} from this {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
        return this;
    }

    /**
     * Adds a {@code Session} to this {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder createAttendanceSession(Session session) {
        sessions.add(session);
        return this;
    }

    /**
     * Removes a {@code Session} from this {@code TutorialGroup} we are building.
     */
    public TutorialGroupBuilder removeAttendanceSession(Session session) {
        sessions.remove(session);
        return this;
    }

    public TutorialGroup build() {
        return new TutorialGroup(id, name, students, assignments, sessions);
    }
}
