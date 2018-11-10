package seedu.address.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.commons.util.ImageMagickUtilTest;
import seedu.address.logic.CommandHistory;
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
        } catch (Exception e) {
            Logger logger = LogsCenter.getLogger(ImageMagickUtilTest.class);
            logger.warning(e.getMessage());
        }
    }
}
