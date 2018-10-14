package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


//author lancelotwillow
/**
 * give an example for the convert commadnd
 */
public class ExampleCommand extends Command {

    public static final String COMMAND_WORD = "example";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": give the example for processing transmission for the image.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Example Person: %1$s";

    private final Index targetIndex;

    public ExampleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        File tmp = new File(ImageMagickUtil.TMPPATH);
        try {
            //this is the test image, as the time get the image from url,
            //change it to buffered image, it would throw error
            FileInputStream inputstream = new FileInputStream(
                    "/Users/Lancelot/Desktop/CS2103T/project/main/src/main/java/seedu/address/storage/tmp/test.png");
            Image exampleImage = new Image(inputstream);
            //post the image to the scene
            EventsCenter.getInstance().post(new ChangeImageEvent(exampleImage, "original"));
            File outputfile = new File(tmp + "/origin.png");
            BufferedImage image = SwingFXUtils.fromFXImage(exampleImage, null);
            //store the original image
            ImageIO.write(image, "png", outputfile);
            File modifiedFile = new File(tmp + "/modified.png");
            //create a processbuilder to blur the image
            ProcessBuilder pb = new ProcessBuilder(ImageMagickUtil.getExecuteImagicMagic(),
                    outputfile.getAbsolutePath(), "-blur", "0x8", modifiedFile.getAbsolutePath());
            //set the environment of the processbuilder
            Map<String, String> mp = pb.environment();
            mp.put("DYLD_LIBRARY_PATH", "/Users/Lancelot/Desktop/CS2103T"
                    + "/project/main/src/main/resources/imageMagic/package/mac/ImageMagick-7.0.8/lib/");
            Process process = pb.start();
            process.waitFor();
            //get the modified image
            inputstream = new FileInputStream("/Users/Lancelot/Desktop/CS2103T/project/"
                    + "main/src/main/java/seedu/address/storage/tmp/modified.png");
            Image modifiedImage = new Image(inputstream);
            EventsCenter.getInstance().post(new ChangeImageEvent(modifiedImage, "preview"));
            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
        } catch (IOException | InterruptedException e) {
            throw new CommandException(e.toString());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExampleCommand
                && targetIndex.equals(((ExampleCommand) other).targetIndex)); // state check
    }
}
