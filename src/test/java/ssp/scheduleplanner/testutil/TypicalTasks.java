package ssp.scheduleplanner.testutil;

import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder().withName("Alice Pauline")
            .withVenue("123, Jurong West Ave 6, #08-111").withPriority("1")
            .withDate("120823")
            .withTags("friends").build();
    public static final Task BENSON = new TaskBuilder().withName("Benson Meier")
            .withVenue("311, Clementi Ave 2, #02-25")
            .withPriority("1").withDate("130921")
            .withTags("owesMoney", "friends").build();
    public static final Task CARL = new TaskBuilder().withName("Carl Kurz")
            .withDate("131018")
            .withPriority("2")
            .withVenue("wall street")
            .withTags("owesMoney").build();
    public static final Task DANIEL = new TaskBuilder().withName("Daniel Meier")
            .withDate("120823")
            .withPriority("1")
            .withVenue("10th street")
            .withTags("friends").build();
    public static final Task ELLE = new TaskBuilder().withName("Elle Meyer")
            .withDate("141018")
            .withPriority("1")
            .withVenue("michegan ave").build();
    public static final Task FIONA = new TaskBuilder().withName("Fiona Kunz")
            .withDate("131018")
            .withPriority("2")
            .withVenue("little tokyo").build();
    public static final Task GEORGE = new TaskBuilder().withName("George Best")
            .withDate("141018")
            .withPriority("3")
            .withVenue("4th street").build();

    // Manually added
    public static final Task HOON = new TaskBuilder().withName("Hoon Meier").withDate("090423")
            .withPriority("1").withVenue("little india").build();
    public static final Task IDA = new TaskBuilder().withName("Ida Mueller").withDate("121193")
            .withPriority("1").withVenue("chicago ave").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task AMY = new TaskBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withPriority(VALID_EMAIL_AMY).withVenue(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Task BOB = new TaskBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withPriority(VALID_EMAIL_BOB).withVenue(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code SchedulePlanner} with all the typical tasks.
     */
    public static SchedulePlanner getTypicalSchedulePlanner() {
        SchedulePlanner sp = new SchedulePlanner();
        //debug
        System.out.println("sp has Others: " + sp.hasCategory("Others"));
        for (Task task : getTypicalTasks()) {
            sp.addTask(task);
        }
        return sp;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
