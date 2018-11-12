package seedu.superta.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.exceptions.DuplicateStudentException;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;
import seedu.superta.testutil.StudentBuilder;

public class SuperTaClientTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final SuperTaClient superTaClient = new SuperTaClient();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), superTaClient.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        superTaClient.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        SuperTaClient newData = getTypicalSuperTaClient();
        superTaClient.resetData(newData);
        assertEquals(newData, superTaClient);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        SuperTaClientStub newData = new SuperTaClientStub(newStudents);

        thrown.expect(DuplicateStudentException.class);
        superTaClient.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        superTaClient.hasStudent(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(superTaClient.hasStudent(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        superTaClient.addStudent(ALICE);
        assertTrue(superTaClient.hasStudent(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        superTaClient.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(superTaClient.hasStudent(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        superTaClient.getStudentList().remove(0);
    }

    @Test
    public void hasTutorialGroup_sameTutorialGroup_returnsTrue() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        superTaClient.addTutorialGroup(tutorialGroup);
        assertTrue(superTaClient.hasTutorialGroup(tutorialGroup.getId()));
    }

    @Test
    public void getTutorialGroup_getsCorrectTutorialGroup() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        superTaClient.addTutorialGroup(tutorialGroup);
        assertTrue(superTaClient.getTutorialGroup(tutorialGroup.getId()).get().equals(tutorialGroup));
    }

    @Test
    public void getStudentWithId_getsCorrectStudent() {
        superTaClient.addStudent(ALICE);
        assertTrue(superTaClient.getStudentWithId(ALICE.getStudentId()).get().equals(ALICE));
    }

    @Test
    public void addStudentToTutorialGroup_success() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        superTaClient.addTutorialGroup(tutorialGroup);
        superTaClient.addStudentToTutorialGroup(tutorialGroup, ALICE);
        assertTrue(superTaClient.getTutorialGroup(tutorialGroup.getId()).get().getStudents().contains(ALICE));
    }

    @Test
    public void updateTutorialGroup_success() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        String updatedName = "updated_name";
        assertFalse(updatedName.equals(tutorialGroup.getName()));
        TutorialGroup updated = new TutorialGroup(tutorialGroup.getId(), updatedName);
        superTaClient.addTutorialGroup(tutorialGroup);
        superTaClient.updateTutorialGroup(updated);
        assertTrue(superTaClient.getTutorialGroup(tutorialGroup.getId()).get().getName().equals(updatedName));
    }

    @Test
    public void removeTutorialGroup_success() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        superTaClient.addTutorialGroup(tutorialGroup);
        assertTrue(superTaClient.hasTutorialGroup(tutorialGroup.getId()));
        superTaClient.removeTutorialGroup(tutorialGroup);
        assertFalse(superTaClient.hasTutorialGroup(tutorialGroup.getId()));
    }

    @Test
    public void addAssignment_success() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        Assignment assignment = getModelAssignment();
        superTaClient.addTutorialGroup(tutorialGroup);
        superTaClient.addAssignment(tutorialGroup, assignment);
        assertTrue(superTaClient.getTutorialGroup(tutorialGroup.getId()).get().getAssignment(assignment.getTitle())
                       .isPresent());
    }

    @Test
    public void grade_noSuchTutorialGroup_failure() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        Assignment assignment = getModelAssignment();
        Student student = ALICE;

        Grade grade = new Grade(tutorialGroup.getId(), assignment.getTitle(), student.getStudentId(), 40.0);
        superTaClient.addStudent(student);

        thrown.expect(TutorialGroupNotFoundException.class);
        superTaClient.grade(grade);
    }

    @Test
    public void grade_noSuchAssignment_failure() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        Assignment assignment = getModelAssignment();
        Student student = ALICE;

        Grade grade = new Grade(tutorialGroup.getId(), assignment.getTitle(), student.getStudentId(), 40.0);
        superTaClient.addStudent(student);
        superTaClient.addTutorialGroup(tutorialGroup);

        thrown.expect(AssignmentNotFoundException.class);
        superTaClient.grade(grade);
    }

    @Test
    public void grade_noSuchStudentInTutorialGroup_failure() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        Assignment assignment = getModelAssignment();
        tutorialGroup.addAssignment(assignment);
        Student student = ALICE;

        Grade grade = new Grade(tutorialGroup.getId(), assignment.getTitle(), student.getStudentId(), 40.0);
        superTaClient.addStudent(student);
        superTaClient.addTutorialGroup(tutorialGroup);

        thrown.expect(StudentNotFoundException.class);
        superTaClient.grade(grade);
    }

    @Test
    public void grade_success() {
        TutorialGroup tutorialGroup = getModelTutorialGroup();
        Assignment assignment = getModelAssignment();
        tutorialGroup.addAssignment(assignment);
        Student student = ALICE;
        superTaClient.addStudent(student);
        superTaClient.addTutorialGroup(tutorialGroup);
        tutorialGroup.addStudent(student);

        double marks = 40.0;

        Grade grade = new Grade(tutorialGroup.getId(), assignment.getTitle(), student.getStudentId(), marks);
        superTaClient.grade(grade);

        assertTrue(superTaClient.getTutorialGroup(tutorialGroup.getId()).get()
            .getAssignment(assignment.getTitle()).get()
            .getGradebook().getGradeFor(student.getStudentId()).equals(marks));
    }

    @Test
    public void hashCode_isNotNull() {
        assertNotNull(superTaClient.hashCode());
    }

    private TutorialGroup getModelTutorialGroup() {
        return new TutorialGroup("testing_id",
                "Test Tutorial Group");
    }

    private Assignment getModelAssignment() {
        return new Assignment(new Title("testing name"), 40.0);
    }

    /**
     * A stub ReadOnlySuperTaClient whose students list can violate interface constraints.
     */
    private static class SuperTaClientStub implements ReadOnlySuperTaClient {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        SuperTaClientStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableMap<String, TutorialGroup> getTutorialGroupMap() {
            return null;
        }

        @Override
        public ObservableList<TutorialGroup> getTutorialGroupList() {
            return null;
        }

        @Override
        public Optional<TutorialGroup> getTutorialGroup(String id) {
            return Optional.empty();
        }
    }

}
