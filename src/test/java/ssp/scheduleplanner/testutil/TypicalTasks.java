package ssp.scheduleplanner.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.logic.commands.CommandTestUtil;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("1")
            .withDate("120823")
            .withTags("friends").build();
    public static final Task BENSON = new TaskBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("2").withDate("130921")
            .withTags("owesMoney", "friends").build();
    public static final Task CARL = new TaskBuilder().withName("Carl Kurz").withDate("110434")
            .withEmail("3").withAddress("wall street").build();
    public static final Task DANIEL = new TaskBuilder().withName("Daniel Meier").withDate("070503")
            .withEmail("1").withAddress("10th street").withTags("friends").build();
    public static final Task ELLE = new TaskBuilder().withName("Elle Meyer").withDate("040602")
            .withEmail("2").withAddress("michegan ave").build();
    public static final Task FIONA = new TaskBuilder().withName("Fiona Kunz").withDate("030512")
            .withEmail("3").withAddress("little tokyo").build();
    public static final Task GEORGE = new TaskBuilder().withName("George Best").withDate("030295")
            .withEmail("1").withAddress("4th street").build();

    // Manually added
    public static final Task HOON = new TaskBuilder().withName("Hoon Meier").withDate("090423")
            .withEmail("1").withAddress("little india").build();
    public static final Task IDA = new TaskBuilder().withName("Ida Mueller").withDate("121193")
            .withEmail("2").withAddress("chicago ave").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task AMY = new TaskBuilder().withName(CommandTestUtil.VALID_NAME_AMY).withDate(CommandTestUtil.VALID_DATE_AMY)
            .withEmail(CommandTestUtil.VALID_EMAIL_AMY).withAddress(CommandTestUtil.VALID_ADDRESS_AMY).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();
    public static final Task BOB = new TaskBuilder().withName(CommandTestUtil.VALID_NAME_BOB).withDate(CommandTestUtil.VALID_DATE_BOB)
            .withEmail(CommandTestUtil.VALID_EMAIL_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND, CommandTestUtil.VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code SchedulePlanner} with all the typical tasks.
     */
    public static SchedulePlanner getTypicalSchedulePlanner() {
        SchedulePlanner ab = new SchedulePlanner();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
