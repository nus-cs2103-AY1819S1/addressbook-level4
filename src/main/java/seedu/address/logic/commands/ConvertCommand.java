package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.embed.swing.SwingFXUtils;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.events.ui.TransformationEvent;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;


/**
 * @@author lancelotwillow
 * the class to execute the convert command that do the modification of the image
 */
public class ConvertCommand extends Command {

    public static final String COMMAND_WORD = "convert";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": do the operation to the image.\n"
            + "Parameters: operationName argument1 argument2 ...\n"
            + "Example: " + COMMAND_WORD + " blur 1x8";
    //the path of the json file containing the arguments of the convert command
    public static final Path SINGLE_COMMAND_TEMPLATE_PATH = Paths.get(
            MainApp.MAIN_PATH + "/src/main/resources/imageMagic/commandTemplate.json");
    private Path filepath;
    private Transformation transformation;
    /**
     * the constructor take the path of the JSON file of the detail of the convert operation
     * @param filepath the path to the JSON file
     * @param transformation contains the operation to be processed to the image
     */
    public ConvertCommand(Path filepath, Transformation transformation) throws ParseException, IOException {
        if (!isFileExist(filepath)) {
            throw new ParseException("no file found");
        }
        this.filepath = filepath;
        this.transformation = transformation;
    }

    private static boolean isFileExist(Path filepath) {
        return new File(filepath.toString()).exists();
    }

    /**
     * build a new processbuilder and initialize witht the commands need to the convert command
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.addTransformation(transformation);
            BufferedImage modifiedImage = ImageMagickUtil.processImage(model.getCurrentPreviewImagePath(),
                    transformation);
            model.updateCurrentPreviewImage(modifiedImage, transformation);
            EventsCenter.getInstance().post(
                    new ChangeImageEvent(
                            SwingFXUtils.toFXImage(model.getCurrentPreviewImage().getImage(), null), "preview"));
            EventsCenter.getInstance().post(new TransformationEvent(transformation.toString()));
        } catch (Exception e) {
            throw new CommandException(e.toString());
        }
        return new CommandResult("process is done");
    }
}
