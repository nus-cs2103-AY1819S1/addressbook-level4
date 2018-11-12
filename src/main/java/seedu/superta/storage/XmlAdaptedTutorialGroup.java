package seedu.superta.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.UniqueAssignmentList;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.attendance.UniqueSessionList;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.student.UniqueStudentList;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * Class for XML-Adapted Tutorial Group
 */
public class XmlAdaptedTutorialGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial Group's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String id;
    @XmlElement
    private List<String> studentIds = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAssignment> assignments = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedSession> sessions = new ArrayList<>();

    public XmlAdaptedTutorialGroup() {}

    public XmlAdaptedTutorialGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public XmlAdaptedTutorialGroup(TutorialGroup source) {
        name = source.getName();
        id = source.getId();
        studentIds = source.getStudents().asUnmodifiableObservableList().stream()
            .map(st -> st.getStudentId().toString())
            .collect(Collectors.toList());
        source.getAssignments().asUnmodifiableObservableList()
            .stream()
            .map(XmlAdaptedAssignment::new)
            .forEach(assignments::add);
        source.getSessions().asUnmodifiableObservableList()
            .stream()
            .map(XmlAdaptedSession::new)
            .forEach(sessions::add);
    }

    /**
     * Requires the data store so that we don't recreate students.
     */
    public TutorialGroup toModelType(SuperTaClient superTaClient) throws IllegalValueException {
        final List<Student> modelStudents = new ArrayList<>();
        for (String id : studentIds) {
            Student student = superTaClient.getStudentWithId(new StudentId(id)).get();
            modelStudents.add(student);
        }
        UniqueStudentList studentList = new UniqueStudentList();
        studentList.setStudents(modelStudents);

        final List<Assignment> modelAssignments = new ArrayList<>();
        for (XmlAdaptedAssignment ass : assignments) {
            modelAssignments.add(ass.toModelType());
        }
        UniqueAssignmentList uniqueAssignmentList = new UniqueAssignmentList(modelAssignments);

        final List<Session> modelSessions = new ArrayList<>();
        for (XmlAdaptedSession ses : sessions) {
            modelSessions.add(ses.toModelType());
        }
        UniqueSessionList uniqueSessionList = new UniqueSessionList(modelSessions);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ID"));
        }

        // TODO: Replace last argument with actual assignment syntax
        return new TutorialGroup(id, name, studentList, uniqueAssignmentList, uniqueSessionList);
    }

}
