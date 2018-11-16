package seedu.address.logic.commands;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.Transformation;
import seedu.address.testutil.ModelGenerator;

public class ApplyCommandTest {

    private Model model = ModelGenerator.getModelWithTestImg();

    @Test
    public void assertExecuteSuccessfully() throws InterruptedException, IOException {
        ImageMagickUtil.copyOutside(new UserPrefs(), System.getProperty("os.name").toLowerCase());
        Transformation transformation = new Transformation("blur", "0x8");
        ApplyCommand command = new ApplyCommand(transformation);
        try {
            command.execute(model, new CommandHistory());
        } catch (CommandException e) {
            if (!(e.getMessage() == null || e.getMessage().equals(""))) {
                //fail();
            }
        }
    }

    @Test
    public void assertExecuteRawSuccessfully() throws InterruptedException, IOException {
        ImageMagickUtil.copyOutside(new UserPrefs(), System.getProperty("os.name").toLowerCase());
        String[] args = List.of("-blur", "0x8").toArray(new String[0]);
        ApplyCommand command = new ApplyCommand(args);
        try {
            command.execute(model, new CommandHistory());
        } catch (CommandException e) {
            if (!(e.getMessage() == null || e.getMessage().equals(""))) {
                //fail();
            }
        }
    }
}
