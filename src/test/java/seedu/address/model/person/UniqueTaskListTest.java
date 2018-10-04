package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LABEL_HUSBAND;
import static seedu.address.testutil.TypicalTasks.A_TASK;
import static seedu.address.testutil.TypicalTasks.Z_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicateTaskException;
import seedu.address.model.person.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.contains(null);
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(A_TASK));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(A_TASK);
        assertTrue(uniqueTaskList.contains(A_TASK));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(A_TASK);
        Task editedAlice = new TaskBuilder(A_TASK).withDescription(VALID_DESCRIPTION_BOB)
                .withLabels(VALID_LABEL_HUSBAND)
                .build();
        assertTrue(uniqueTaskList.contains(editedAlice));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.add(null);
    }

    @Test
    public void add_duplicateTask_throwsDuplicatePersonException() {
        uniqueTaskList.add(A_TASK);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.add(A_TASK);
    }

    @Test
    public void setPerson_nullTargetTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTask(null, A_TASK);
    }

    @Test
    public void setTask_nullEditedTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTask(A_TASK, null);
    }

    @Test
    public void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        uniqueTaskList.setTask(A_TASK, A_TASK);
    }

    @Test
    public void setTask_editedTaskIsSameTask_success() {
        uniqueTaskList.add(A_TASK);
        uniqueTaskList.setTask(A_TASK, A_TASK);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(A_TASK);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasSameIdentity_success() {
        uniqueTaskList.add(A_TASK);
        Task editedAlice = new TaskBuilder(A_TASK).withDescription(VALID_DESCRIPTION_BOB)
                .withLabels(VALID_LABEL_HUSBAND)
                .build();
        uniqueTaskList.setTask(A_TASK, editedAlice);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(editedAlice);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasDifferentIdentity_success() {
        uniqueTaskList.add(A_TASK);
        uniqueTaskList.setTask(A_TASK, Z_TASK);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(Z_TASK);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        uniqueTaskList.add(A_TASK);
        uniqueTaskList.add(Z_TASK);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.setTask(A_TASK, Z_TASK);
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.remove(null);
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        uniqueTaskList.remove(A_TASK);
    }

    @Test
    public void remove_existingTask_removesTask() {
        uniqueTaskList.add(A_TASK);
        uniqueTaskList.remove(A_TASK);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullUniqueTaskList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTasks((UniqueTaskList) null);
    }

    @Test
    public void setTasks_uniqueTaskList_replacesOwnListWithProvidedUniqueTaskList() {
        uniqueTaskList.add(A_TASK);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(Z_TASK);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTasks((List<Task>) null);
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(A_TASK);
        List<Task> personList = Collections.singletonList(Z_TASK);
        uniqueTaskList.setTasks(personList);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(Z_TASK);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setTasks_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(A_TASK, A_TASK);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.setTasks(listWithDuplicateTasks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asUnmodifiableObservableList().remove(0);
    }
}
