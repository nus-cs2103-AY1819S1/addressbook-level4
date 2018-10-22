package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;


/**
 * @author lancelotwillow
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
            MainApp.MAIN_PATH + "/src/main/java/seedu/address/storage/commandTemplate.json");
    public static final Path SINGLE_COMMAND_PATH = Paths.get(
            MainApp.MAIN_PATH + "/src/main/java/seedu/address/storage/commandTemplate.json");
    private Path filepath;
    private Transformation transformation;
    private List<String> cmds;
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
        this.cmds = parseArguments(filepath, transformation);
    }

    private static boolean isFileExist(Path filepath) {
        return new File(filepath.toString()).exists();
    }

    /**
     * the method to parse the argument and return a list of commands to be passed to the process builder
     * @param filepath
     * @param transformation
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static List<String> parseArguments(Path filepath, Transformation transformation)
            throws IOException, ParseException {
        List<String> trans = transformation.toList();
        String operation = trans.get(0);
        List<String> cmds = JsonConvertArgsStorage.retrieveArgumentsTemplate(filepath, operation);
        int num = cmds.size();
        if (num != trans.size() - 1) {
            throw new ParseException("Invalid arguments, should be " + cmds.toArray().toString());
        }
        return trans;
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
        File tmp = new File(ImageMagickUtil.TMPPATH);
        try {
            File outputfile = new File(tmp + "/origin.png");
            //store the original image
            BufferedImage displayedImage = model.getCurrentOriginalImage();
            ImageIO.write(displayedImage, "png", outputfile);
            //File modifiedFile = new File(tmp + "/modified.png");
            processImage();
            //get the modified image
            FileInputStream inputstream = new FileInputStream(tmp + "/modified.png");
            Image modifiedImage = new Image(inputstream);
            model.updateCurrentpreviewImage(modifiedImage, transformation);
            EventsCenter.getInstance().post(new ChangeImageEvent(modifiedImage, "preview"));
            outputfile.delete();
            return new CommandResult("process " + cmds.get(0) + " done");
        } catch (IOException | InterruptedException e) {
            throw new CommandException(e.toString());
        }
    }

    /**
     * doing the process of the image
     * @throws IOException
     * @throws InterruptedException
     */
    private void processImage() throws IOException, InterruptedException {
        File tmp = new File(ImageMagickUtil.TMPPATH);
        File outputfile = new File(tmp + "/origin.png");
        File modifiedFile = new File(tmp + "/modified.png");
        //create a processbuilder to blur the image
        ProcessBuilder pb;
        List<String> args = new ArrayList<>();
        args.add(ImageMagickUtil.getExecuteImageMagic());
        args.add(outputfile.getAbsolutePath());
        args.add("-" + cmds.get(0));
        for (int i = 1; i < cmds.size(); i++) {
            args.add(cmds.get(i));
        }
        args.add(modifiedFile.getAbsolutePath());
        pb = new ProcessBuilder(args);
        //set the environment of the processbuilder
        Map<String, String> mp = pb.environment();
        mp.put("DYLD_LIBRARY_PATH", ImageMagickUtil.getImageMagicPackagePath() + "ImageMagick-7.0.8/lib/");
        Process process = pb.start();
        process.waitFor();
    }
}
