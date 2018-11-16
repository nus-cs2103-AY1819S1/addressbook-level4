package seedu.address.logic.commands;

import static junit.framework.TestCase.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorageTest;
import seedu.address.testutil.ModelGenerator;

public class CreateApplyCommandTest {

    @Test
    public void createOperationsSuccessfully() throws CommandException {
        if (ImageMagickUtil.getCommandSaveFolder() == null) {
            ImageMagickUtil.setTemporaryCommandForder(JsonConvertArgsStorageTest.TEST_DATA_FOLDER.toString());
        }
        Transformation transformation = new Transformation("blur", "0x8");
        List<Transformation> list = new ArrayList<>();
        list.add(transformation);
        CreateApplyCommand command = new CreateApplyCommand("newOperation", list);
        command.execute(ModelGenerator.getDefaultModel(), null);
        //test the case with multiple transformations
        list.add(transformation);
        command = new CreateApplyCommand("newOperation", list);
        command.execute(ModelGenerator.getDefaultModel(), null);
        if (!new File(JsonConvertArgsStorageTest.TEST_DATA_FOLDER.toString()).exists()) {
            fail();
        }
    }

    @Test
    public void createOperationsUnsuccessfully() throws CommandException {
        if (ImageMagickUtil.getCommandSaveFolder() == null) {
            ImageMagickUtil.setTemporaryCommandForder(JsonConvertArgsStorageTest.TEST_DATA_FOLDER.toString());
        }
        Transformation transformation = new Transformation("blur", "0x8");
        Transformation transformation2 = new Transformation("contrast", "0x8");
        List<Transformation> list = new ArrayList<>();
        list.add(transformation);
        list.add(transformation2);
        CreateApplyCommand command = new CreateApplyCommand("newOperation", list);
        assertCommandFailure(command, null, new CommandHistory(), Messages.MESSAGE_INVALID_OPERATION_ARGUMENTS);
        //test with different failure cases
        list.remove(transformation2);
        Transformation transformation3 = new Transformation("resize", "fake");
        list.add(transformation3);
        command = new CreateApplyCommand("newOperation", list);
        assertCommandFailure(command, null, new CommandHistory(), Messages.MESSAGE_INVALID_OPERATION_ARGUMENTS);
        try {
            new CreateApplyCommand("newOperation", new ArrayList<>());
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Empty")) {
                fail();
            }
        }

    }
}
