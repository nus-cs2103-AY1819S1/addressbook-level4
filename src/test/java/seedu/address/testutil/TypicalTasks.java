package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111").withPriorityValue("alice@example.com")
            .withDueDate("94351253")
            .withLabels("friends").build();
    public static final Task BENSON = new TaskBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withPriorityValue("johnd@example.com").withDueDate("98765432")
            .withLabels("owesMoney", "friends").build();
    public static final Task CARL = new TaskBuilder().withName("Carl Kurz").withDueDate("95352563")
            .withPriorityValue("heinz@example.com").withDescription("wall street").build();
    public static final Task DANIEL = new TaskBuilder().withName("Daniel Meier").withDueDate("87652533")
            .withPriorityValue("cornelia@example.com").withDescription("10th street").withLabels("friends").build();
    public static final Task ELLE = new TaskBuilder().withName("Elle Meyer").withDueDate("9482224")
            .withPriorityValue("werner@example.com").withDescription("michegan ave").build();
    public static final Task FIONA = new TaskBuilder().withName("Fiona Kunz").withDueDate("9482427")
            .withPriorityValue("lydia@example.com").withDescription("little tokyo").build();
    public static final Task GEORGE = new TaskBuilder().withName("George Best").withDueDate("9482442")
            .withPriorityValue("anna@example.com").withDescription("4th street").build();

    // Manually added
    public static final Task HOON = new TaskBuilder().withName("Hoon Meier").withDueDate("8482424")
            .withPriorityValue("stefan@example.com").withDescription("little india").build();
    public static final Task IDA = new TaskBuilder().withName("Ida Mueller").withDueDate("8482131")
            .withPriorityValue("hans@example.com").withDescription("chicago ave").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task AMY = new TaskBuilder().withName(VALID_NAME_AMY).withDueDate(VALID_PHONE_AMY)
            .withPriorityValue(VALID_EMAIL_AMY).withDescription(VALID_ADDRESS_AMY).withLabels(VALID_TAG_FRIEND).build();
    public static final Task BOB = new TaskBuilder().withName(VALID_NAME_BOB).withDueDate(VALID_PHONE_BOB)
            .withPriorityValue(VALID_EMAIL_BOB).withDescription(VALID_ADDRESS_BOB).withLabels(VALID_TAG_HUSBAND,
                    VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code TaskManager} with all the typical persons.
     */
    public static TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
