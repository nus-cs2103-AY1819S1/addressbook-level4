package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_DIR;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILE_DIR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.getModelWithTestImgDirectory;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author chivent
public class LsCommandTest extends DefaultCommandTest {

    private Model model = getModelWithTestImgDirectory();
    private Model expectedModel = getModelWithTestImgDirectory();
    private CommandHistory commandHistory = new CommandHistory();
    private String expectedMessage;

    @Before
    public void generateExpectedMessage() {
        File dir = new File(expectedModel.getCurrDirectory().toString());
        StringBuffer expectedMessage = new StringBuffer();
        File[] fileList = dir.listFiles();

        for (File file : fileList) {
            if (file.isFile()) {

                String mimetype = new MimetypesFileTypeMap().getContentType(file);
                // only list if is image
                if ((mimetype.split("/")[0]).equals("image")) {
                    expectedMessage.append(file.getName()).append("   \n");
                }

            } else if (file.isDirectory()) {
                expectedMessage.append(file.getName()).append("   \n");
            }
        }

        if (expectedMessage.toString().isEmpty()) {
            this.expectedMessage = MESSAGE_EMPTY_DIR;
        } else {
            this.expectedMessage = expectedMessage.toString();
        }
    }

    @Test
    public void execute_command_success() {
        assertExecutionSuccess(expectedMessage);
    }

    @Test
    public void execute_directory_empty() {
        model.updateCurrDirectory(model.getCurrDirectory().resolve("emptydir").normalize());
        File emptydir = model.getCurrDirectory().toFile();
        if (!emptydir.exists()) {
            emptydir.mkdirs();
        }
        assertExecutionSuccess(MESSAGE_EMPTY_DIR);
    }

    @Test
    public void execute_invalid_directory() {
        model.updateCurrDirectory(model.getCurrDirectory().resolve("invalid").normalize());
        assertExecutionSuccess(MESSAGE_INVALID_FILE_DIR);
    }

    /**
     * Executes a {@code LsCommand} and checks that {@code CommandResult}
     * is raised with the {@code expectedMessage}.
     * @param expected expected message shown
     */
    private void assertExecutionSuccess(String expected) {
        LsCommand lsCommand = new LsCommand();
        assertCommandSuccess(lsCommand, model, commandHistory, expected, expectedModel);
    }
}
