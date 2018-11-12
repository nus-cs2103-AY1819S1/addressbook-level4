package seedu.superta.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.NameContainsKeywordsPredicate;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;
import seedu.superta.testutil.SuperTaClientBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasStudent(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredStudentList().remove(0);
    }

    @Test
    public void addStudentToTutorialGroup_tutorialGroupNotFound_failure() {
        thrown.expect(TutorialGroupNotFoundException.class);
        modelManager.addStudent(ALICE);
        modelManager.addStudentToTutorialGroup(getModelTutorialGroup().getId(), ALICE.getStudentId());
    }

    @Test
    public void addStudentToTutorialGroup_studentNotFound_failure() {
        thrown.expect(StudentNotFoundException.class);
        TutorialGroup tg = getModelTutorialGroup();
        modelManager.addTutorialGroup(tg);
        modelManager.addStudentToTutorialGroup(tg.getId(), ALICE.getStudentId());
    }

    @Test
    public void addStudentToTutorialGroup_success() {
        TutorialGroup tg = getModelTutorialGroup();
        modelManager.addTutorialGroup(tg);
        modelManager.addStudent(ALICE);
        modelManager.addStudentToTutorialGroup(tg.getId(), ALICE.getStudentId());
    }

    @Test
    public void deleteTutorialGroup_tutorialGroupNotFound_failure() {
        TutorialGroup tg = getModelTutorialGroup();
        assertFalse(modelManager.hasTutorialGroup(tg.getId()));
        thrown.expect(TutorialGroupNotFoundException.class);
        modelManager.deleteTutorialGroup(tg);
    }

    @Test
    public void deleteTutorialGroup_success() {
        TutorialGroup tg = getModelTutorialGroup();
        modelManager.addTutorialGroup(tg);
        assertTrue(modelManager.hasTutorialGroup(tg.getId()));
        modelManager.deleteTutorialGroup(tg);
        assertFalse(modelManager.hasTutorialGroup(tg.getId()));
    }

    @Test
    public void addAssignment_tutorialGroupNotFound_failure() {
        TutorialGroup tg = getModelTutorialGroup();
        assertFalse(modelManager.hasTutorialGroup(tg.getId()));
        Assignment assignment = getModelAssignment();
        thrown.expect(TutorialGroupNotFoundException.class);
        modelManager.addAssignment(tg.getId(), assignment);
    }

    @Test
    public void addAssignment_success() {
        TutorialGroup tg = getModelTutorialGroup();
        assertTrue(!modelManager.hasTutorialGroup(tg.getId()));

        modelManager.addTutorialGroup(tg);
        Assignment assignment = getModelAssignment();
        modelManager.addAssignment(tg.getId(), assignment);
        assertTrue(modelManager.getTutorialGroup(tg.getId()).get().getAssignment(assignment.getTitle()).isPresent());
    }

    @Test
    public void grade_success() {
        TutorialGroup tg = getModelTutorialGroup();
        Assignment assignment = getModelAssignment();
        Student student = ALICE;
        modelManager.addStudent(student);
        modelManager.addTutorialGroup(tg);
        modelManager.addStudentToTutorialGroup(tg.getId(), student.getStudentId());
        modelManager.addAssignment(tg.getId(), assignment);

        double marks = 35.5;

        Grade grade = new Grade(tg.getId(), assignment.getTitle(), student.getStudentId(), marks);
        modelManager.grade(grade);
        assertTrue(modelManager.getTutorialGroup(tg.getId()).get().getAssignment(assignment.getTitle()).get()
                       .getGradebook().getGradeFor(student.getStudentId()).equals(marks));
    }

    @Test
    public void addFeedback_success() {
        Feedback feedback = new Feedback("Have a nice day");
        modelManager.addStudent(ALICE);
        modelManager.addFeedback(feedback, ALICE.getStudentId());
        // TODO: Add getting feedback / student method.
    }

    @Test
    public void hasTutorialGroup_success() {
        TutorialGroup tg = getModelTutorialGroup();
        assertFalse(modelManager.hasTutorialGroup(tg.getId()));
        modelManager.addTutorialGroup(tg);
        assertTrue(modelManager.hasTutorialGroup(tg.getId()));
    }

    @Test
    public void getTutorialGroup_success() {
        TutorialGroup tg = getModelTutorialGroup();
        assertFalse(modelManager.hasTutorialGroup(tg.getId()));
        modelManager.addTutorialGroup(tg);
        assertTrue(modelManager.getTutorialGroup(tg.getId()).get().equals(tg));
    }

    @Test
    public void getTutorialGroup_tutorialGroupNotFound_failure() {
        assertFalse(modelManager.hasTutorialGroup(getModelTutorialGroup().getId()));
        assertFalse(modelManager.getTutorialGroup(getModelTutorialGroup().getId()).isPresent());
    }

    @Test
    public void updateTutorialGroup_success() {
        TutorialGroup tg = getModelTutorialGroup();
        modelManager.addTutorialGroup(tg);
        String updatedName = "updated_name";
        TutorialGroup clone = new TutorialGroup(tg.getId(), updatedName);
        modelManager.updateTutorialGroup(clone);
        assertTrue(modelManager.getTutorialGroup(tg.getId()).get().getName().equals(updatedName));
    }

    @Test
    public void equals() {
        SuperTaClient superTaClient = new SuperTaClientBuilder().withPerson(ALICE).withPerson(BENSON).build();
        SuperTaClient differentSuperTaClient = new SuperTaClient();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(superTaClient, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(superTaClient, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different superTAClient -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentSuperTaClient, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(superTaClient, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(superTaClient, differentUserPrefs)));
    }

    private TutorialGroup getModelTutorialGroup() {
        return new TutorialGroup("testing_id", "testing_name");
    }

    private Assignment getModelAssignment() {
        return new Assignment(new Title("test assignment"), 40.0);
    }
}
