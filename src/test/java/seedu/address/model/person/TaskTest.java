package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.BOB;

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
        assertTrue(ALICE.isSameTask(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTask(null));

        // different due date and priority value -> returns false
        Task editedAlice = new TaskBuilder(ALICE).withDueDate(VALID_PHONE_BOB).withPriorityValue(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameTask(editedAlice));

        // different name -> returns false
        editedAlice = new TaskBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTask(editedAlice));

        // same name, same due date, different attributes -> returns true
        editedAlice = new TaskBuilder(ALICE).withPriorityValue(VALID_EMAIL_BOB).withDescription(VALID_ADDRESS_BOB)
                .withLabels(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTask(editedAlice));

        // same name, same priority value, different attributes -> returns true
        editedAlice = new TaskBuilder(ALICE).withDueDate(VALID_PHONE_BOB).withDescription(VALID_ADDRESS_BOB)
                .withLabels(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTask(editedAlice));

        // same name, same due date, same priority value, different attributes -> returns true
        editedAlice = new TaskBuilder(ALICE).withDescription(VALID_ADDRESS_BOB).withLabels(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTask(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task aliceCopy = new TaskBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different task -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Task editedAlice = new TaskBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different due date -> returns false
        editedAlice = new TaskBuilder(ALICE).withDueDate(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different priority value -> returns false
        editedAlice = new TaskBuilder(ALICE).withPriorityValue(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new TaskBuilder(ALICE).withDescription(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different labels -> returns false
        editedAlice = new TaskBuilder(ALICE).withLabels(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
