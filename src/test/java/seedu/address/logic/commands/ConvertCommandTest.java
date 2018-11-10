package seedu.address.logic.commands;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.Transformation;
import seedu.address.testutil.ModelGenerator;

public class ConvertCommandTest {

    private Model model = ModelGenerator.getModelWithTestImg();

    @Test
    public void assertExecuteSuccessfully() throws InterruptedException, IOException {
        ImageMagickUtil.copyOutside(new UserPrefs(), System.getProperty("os.name").toLowerCase());
        Transformation transformation = new Transformation("blur", "0x8");
        ConvertCommand command = new ConvertCommand(transformation);
        try {
            command.execute(model, new CommandHistory());
        } catch (CommandException e) {
            if (!(e.getMessage() == null || e.getMessage().equals(""))) {
                fail();
            }
        }
    }
}
