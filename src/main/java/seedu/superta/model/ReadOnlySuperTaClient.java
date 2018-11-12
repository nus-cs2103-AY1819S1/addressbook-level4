package seedu.superta.model;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * Unmodifiable view of a SuperTA client.
 */
public interface ReadOnlySuperTaClient {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the tutorial group list.
     */
    ObservableMap<String, TutorialGroup> getTutorialGroupMap();

    /**
     * Returns an unmodifiable list view of the tutorial group.
     */
    ObservableList<TutorialGroup> getTutorialGroupList();

    /**
     * Returns a tutorial group.
     */
    Optional<TutorialGroup> getTutorialGroup(String id);


}
