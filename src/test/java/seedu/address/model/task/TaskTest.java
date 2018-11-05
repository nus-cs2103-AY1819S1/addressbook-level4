package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LABEL_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_VALUE_BOB;
import static seedu.address.testutil.TypicalTasks.A_TASK;
import static seedu.address.testutil.TypicalTasks.Z_TASK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        task.getLabels().remove(0);
    }

    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(A_TASK.isSameTask(A_TASK));

        // null -> returns false
        assertFalse(A_TASK.isSameTask(null));

        // different due date and priority value -> returns false
        Task editedAlice = new TaskBuilder(A_TASK)
                .withDueDate(VALID_DUEDATE_BOB)
                .withPriorityValue(VALID_PRIORITY_VALUE_BOB)
                .build();
        assertFalse(A_TASK.isSameTask(editedAlice));

        // different name -> returns false
        editedAlice = new TaskBuilder(A_TASK)
                .withName(VALID_NAME_BOB)
                .build();
        assertFalse(A_TASK.isSameTask(editedAlice));

        // same name, same due date, different attributes -> returns true
        editedAlice = new TaskBuilder(A_TASK)
                .withPriorityValue(VALID_PRIORITY_VALUE_BOB)
                .withDescription(VALID_DESCRIPTION_BOB)
                .withLabels(VALID_LABEL_HUSBAND)
                .build();
        assertTrue(A_TASK.isSameTask(editedAlice));

        // same name, same priority value, different attributes -> returns true
        editedAlice = new TaskBuilder(A_TASK)
                .withDueDate(VALID_DUEDATE_BOB)
                .withDescription(VALID_DESCRIPTION_BOB)
                .withLabels(VALID_LABEL_HUSBAND)
                .build();
        assertTrue(A_TASK.isSameTask(editedAlice));

        // same name, same due date, same priority value, different attributes -> returns true
        editedAlice = new TaskBuilder(A_TASK)
                .withDescription(VALID_DESCRIPTION_BOB)
                .withLabels(VALID_LABEL_HUSBAND)
                .build();
        assertTrue(A_TASK.isSameTask(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task aliceCopy = new TaskBuilder(A_TASK).build();
        assertTrue(A_TASK.equals(aliceCopy));

        // same object -> returns true
        assertTrue(A_TASK.equals(A_TASK));

        // null -> returns false
        assertFalse(A_TASK.equals(null));

        // different type -> returns false
        assertFalse(A_TASK.equals(5));

        // different task -> returns false
        assertFalse(A_TASK.equals(Z_TASK));

        // different name -> returns false
        Task editedAlice = new TaskBuilder(A_TASK).withName(VALID_NAME_BOB).build();
        assertFalse(A_TASK.equals(editedAlice));

        // different due date -> returns false
        editedAlice = new TaskBuilder(A_TASK).withDueDate(VALID_DUEDATE_BOB).build();
        assertFalse(A_TASK.equals(editedAlice));

        // different priority value -> returns false
        editedAlice = new TaskBuilder(A_TASK).withPriorityValue(VALID_PRIORITY_VALUE_BOB).build();
        assertFalse(A_TASK.equals(editedAlice));

        // different description -> returns false
        editedAlice = new TaskBuilder(A_TASK).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(A_TASK.equals(editedAlice));

        // different labels -> returns false
        editedAlice = new TaskBuilder(A_TASK).withLabels(VALID_LABEL_HUSBAND).build();
        assertFalse(A_TASK.equals(editedAlice));

        // different name -> returns false
        editedAlice = new TaskBuilder(A_TASK).withStatus(Status.COMPLETED).build();
        assertFalse(A_TASK.equals(editedAlice));
    }
}
