package systemtests;

import static org.junit.Assert.assertEquals;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.CATEGORY_DESC_MODULES;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.TAG_DESC_CS;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_CATEGORY_MODULES;
import static ssp.scheduleplanner.testutil.TypicalTags.CS;

import org.junit.Test;

import ssp.scheduleplanner.logic.commands.AddTagCommand;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Task;

public class AddTagCommandSystemTest extends SchedulePlannerSystemTest {

    @Test
    public void add() {
        Tag toAdd = CS;
        String command = AddTagCommand.COMMAND_WORD + "  " + CATEGORY_DESC_MODULES + TAG_DESC_CS;
        assertCommandSuccess(command, toAdd, VALID_CATEGORY_MODULES);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Task)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Task)
     */
    private void assertCommandSuccess(String command, Tag toAdd, String category) {
        Model expectedModel = getModel();
        expectedModel.addTag(toAdd, category);
        String expectedResultMessage = String.format(AddTagCommand.MESSAGE_SUCCESS, toAdd.getTagName(), category);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Task, String)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddTagCommandSystemTest#assertCommandSuccess(String, Tag, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        //debug
        System.out.println(command);
        assertEquals(new SchedulePlanner(expectedModel.getSchedulePlanner()), getModel().getSchedulePlanner());
    }
}
