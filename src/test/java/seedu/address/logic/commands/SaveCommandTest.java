package seedu.address.logic.commands;

import static junit.framework.TestCase.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;


public class SaveCommandTest {

    private Model model = ModelGenerator.getModelWithTestImg();

    @Test
    public void saveImageSuccessfully() throws CommandException {
        String fileName = "test.jpg";
        SaveCommand command = new SaveCommand();
        try {
            command.execute(model, new CommandHistory());
            command = new SaveCommand(fileName);
            File file = new File(model.getCurrDirectory().toString() + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            command.execute(model, new CommandHistory());
            if (!file.exists()) {
                fail();
            }
            file.delete();
        } catch (CommandException e) {
            if (e.getMessage() != null) {
                throw e;
            }
        }
    }

    @Test
    public void saveImageUnsuccessfully() throws CommandException {
        String fileName = "test.jpg";
        SaveCommand command = new SaveCommand(fileName);
        File file = new File(model.getCurrDirectory().toString() + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            command.execute(model, new CommandHistory());
            assertCommandFailure(command, model, new CommandHistory(), Messages.MESSAGE_DUPLICATED_IMAGE);
            file.delete();
        } catch (CommandException e) {
            if (e.getMessage() != null) {
                throw e;
            }
        }
    }
}
