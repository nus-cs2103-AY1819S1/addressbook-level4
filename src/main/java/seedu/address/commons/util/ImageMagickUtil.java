package seedu.address.commons.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.canvas.Canvas;
import seedu.address.model.canvas.Layer;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 * @author lancelotwillow
 */
public class ImageMagickUtil {
    //initialize the paths used in the imageMagic
    private static final int LINUX = 1;
    private static final int WINDOWS = 2;
    private static final int MAC = 3;
    private static final String osName = System.getProperty("os.name").toLowerCase();
    private static String convertExecutablePath = "";
    private static String imageMagickPath = ImageMagickUtil.class.getResource("/imageMagic").getPath();
    private static String tmpPath = imageMagickPath + "/tmp";
    private static String commandSaveFolder;

    /**
     * get the path of the package location
     * @return
     * @throws NoSuchElementException
     */
    public static URL getImageMagickZipUrl(String osName) throws NoSuchElementException {
        switch (getPlatform(osName)) {
        case MAC:
            return ImageMagickUtil.class.getResource("/imageMagic/package/mac/ImageMagick-7.0.8.zip");
        case WINDOWS:
            return ImageMagickUtil.class.getResource("/imageMagic/package/win/ImageMagick-7.0.8-14.zip");
        default:
            return ImageMagickUtil.class.getResource("/imageMagic/package/mac/ImageMagick-7.0.8.zip");
        }
    }

    public static String getCommandSaveFolder() {
        return commandSaveFolder;
    }

    //these two methods are used for tesing only
    public static void setTemporaryCommandForder(String folder) {
        commandSaveFolder = folder;
    }

    public static Path getTempFolderPath() {
        return Paths.get(tmpPath);
    }

    /**
     * get the platform;
     * @return
     */
    public static int getPlatform(String osName) {
        if (osName.contains("win")) {
            return WINDOWS;
        } else if (osName.contains("mac")) {
            return MAC;
        } else if (osName.contains("nux") || osName.contains("ubuntu")) {
            return LINUX;
        }
        return 0;
    }

    /**
     * with a path and the transmission, return the bufferedimage processed.
     * @param path
     * @param transformation
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws InterruptedException
     */
    public static BufferedImage processImage(Path path, Transformation transformation, boolean isRaw)
            throws ParseException, IOException, InterruptedException, IllegalArgumentException,
            IllegalOperationException {
        String modifiedFile = tmpPath + "/output.png";
        //create a processbuilder to blur the image
        ArrayList<String> args = new ArrayList<>();
        args.add(ImageMagickUtil.getConvertExecutablePath());
        args.add(path.toAbsolutePath().toString());
        args.add("-background");
        args.add("rgba(0,0,0,0)"); //HARDFIX!
        if (!isRaw) {
            ArrayList<String> cmds = parseOperationArguments(transformation);
            args.add("-" + cmds.remove(0));
            args.addAll(cmds);
        } else {
            ArrayList<String> cmds = transformation.toList();
            cmds.remove(0);
            args.addAll(cmds);
        }
        args.add(modifiedFile);
        return runProcessBuilder(args, modifiedFile);
    }

    /**
     * parse the argument passing to the imageMagic to check the validation about the arguments
     * @param transformation
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static ArrayList<String> parseOperationArguments(Transformation transformation)
            throws IOException, ParseException, IllegalArgumentException {
        String operation = transformation.getOperation();
        if (!operation.startsWith("@")) {
            return parseBuildInOperation(transformation);
        } else {
            return parseCustomisedOperation(transformation);
        }
    }

    /**
     * .
     * @param transformation
     * @return
     * @throws ParseException
     * @throws IOException
     */
    private static ArrayList<String> parseBuildInOperation(Transformation transformation)
            throws ParseException, IOException {
        ArrayList<String> trans = transformation.toList();
        String operation = transformation.getOperation();
        URL fileUrl = ImageMagickUtil.class.getResource("/imageMagic/commandTemplates/" + operation + ".json");
        if (fileUrl == null) {
            throw new ParseException("Operation is invalid");
        }
        //in order to get the template of the argument.
        List<String> cmds = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation, "arg");
        int num = cmds.size();
        String template = cmds.toString();
        if (num != trans.size() - 1) {
            throw new IllegalArgumentException("Invalid arguments, the arguments should be "
                    + operation + " " + template.substring(1, template.length() - 1));
        }
        //get the pattern for the argument, check validation.
        List<String> patterns = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation, "pattern");
        for (int i = 0; i < patterns.size(); i++) {
            if (!trans.get(i + 1).matches(patterns.get(i))) {
                throw new IllegalArgumentException("Invalid arguments, the arguments should be:"
                        + operation + " " + template.substring(1, template.length() - 1));
            }
        }
        return trans;
    }

    /**
     * .
     * @param transformation
     * @return
     * @throws ParseException
     * @throws IOException
     */
    private static ArrayList<String> parseCustomisedOperation(Transformation transformation)
            throws ParseException, IOException {
        String operation = transformation.getOperation().substring(1);
        File file = new File(commandSaveFolder + "/" + operation + ".json");
        if (!file.exists()) {
            throw new ParseException("Operation is invalid");
        }
        return new ArrayList<>(JsonConvertArgsStorage.retrieveCommandArguments(file));
    }

    /**
     * Given a list of arguments to ImageMagick, calls the actual ImageMagick executable with the output set to the
     * path provided
     * @param args An ArrayList of arguments, the first of which needs to be a legal ImageMagick executable.
     * @param output An URL to the output file.
     * @return
     * @throws IOException
     * @throws InterruptedException
     */

    public static BufferedImage runProcessBuilder(ArrayList<String> args, String output)
            throws IOException, InterruptedException, IllegalArgumentException, IllegalOperationException {
        ProcessBuilder pb = new ProcessBuilder(args);
        if (getPlatform(osName) == MAC) {
            Map<String, String> mp = pb.environment();
            mp.put("DYLD_LIBRARY_PATH", imageMagickPath + "/ImageMagick-7.0.8/lib/");
        }
        if (getPlatform(osName) == LINUX || getPlatform(osName) == 0) {
            throw new IllegalOperationException("Unsupported OS!");
        }
        Process process = pb.start();
        process.waitFor();
        if (process.exitValue() != 0) {
            throw new IllegalArgumentException("Process fails");
        }
        FileInputStream is = new FileInputStream(output);
        Image modifiedImage = new Image(is);
        return SwingFXUtils.fromFXImage(modifiedImage, null);
    }

    /**
     * copy the imageMagick outside of the jarfile in order to call it.
     * @author lancelotwillow
     * @param userPrefs
     * @throws IOException
     * @throws InterruptedException
     */
    public static void copyOutside(UserPrefs userPrefs, String osName) throws IOException, InterruptedException {
        URL zipUrl = getImageMagickZipUrl(osName);
        Path currentPath = userPrefs.getCurrDirectory();
        File zipFile = new File(currentPath.toString() + "/temp.zip");
        File tempFolder = new File(userPrefs.getCurrDirectory() + "/tempFolder");
        tempFolder.mkdir();
        ResourceUtil.copyResourceFileOut(zipUrl, zipFile);
        switch (getPlatform(osName)) {
        case MAC:
            Process process = new ProcessBuilder(
                    "tar", "zxvf", zipFile.getPath(), "-C", currentPath.toString()).start();
            process.waitFor();
            //remove the __MACOSX folder in the mac
            new ProcessBuilder("rm", "-rf", currentPath.toString() + "/__MACOSX").start();
            convertExecutablePath = currentPath.toString() + "/ImageMagick-7.0.8/bin/convert";
            break;
        case WINDOWS:
            ResourceUtil.unzipFolder(zipFile);
            convertExecutablePath = currentPath.toString() + "/ImageMagick-7.0.8-14-portable-Q16-x64/convert.exe";
            break;
        default:
        }
        imageMagickPath = currentPath.toString();
        tmpPath = tempFolder.getPath();
        commandSaveFolder = currentPath.toString() + "/PiconsoCommands";
        File commandFolder = new File(commandSaveFolder);
        if (!(commandFolder.exists() && commandFolder.isDirectory())) {
            commandFolder.mkdir();
        }
        zipFile.delete();
    }

    public static String getConvertExecutablePath() {
        if (convertExecutablePath != null) {
            return convertExecutablePath;
        }
        throw new NoSuchElementException("The ImageMagick binaries cannot be found!");
    }

    //@@author j-lum
    /**
     * Creates a ProcessBuilder instance to merge/flatten layers.
     * @param c - A canvas to be processed
     * @return a buffered image with a merged canvas.
     */
    public static BufferedImage processCanvas(Canvas c) throws IOException, InterruptedException,
            IllegalOperationException {
        ArrayList<String> args = new ArrayList<>();
        String output = tmpPath + "/modified.png";
        args.add(getConvertExecutablePath());
        boolean pageSizeDefined = false;

        for (Layer l: c.getLayers()) {
            if (!c.isCanvasAuto() && !pageSizeDefined) {
                args.add("-page");
                args.add(String.format("%dx%d+%d+%d", c.getWidth(), c.getHeight(), l.getX(), l.getY()));
                args.add(String.format("%s", l.getImage().getCurrentPath()));
                pageSizeDefined = true;
            }
            args.add("-page");
            args.add(String.format("+%d+%d", l.getX(), l.getY()));
            args.add(String.format("%s", l.getImage().getCurrentPath()));
        }

        args.add("-background");
        args.add(String.format("%s", c.getBackgroundColor()));

        if (c.isCanvasAuto()) {
            args.add("-layers");
            args.add("merge");
        } else {
            args.add("-flatten");
        }
        args.add(output);
        System.out.println(args);
        return runProcessBuilder(args, output);
    }

    /**
     * Saves the canvas to an output file
     * @param c - A canvas to be processed
     * *//*
    public static void saveCanvas(Canvas c, Path outDirectory, String fileName)
            throws IOException, InterruptedException, UnsupportedPlatformException {
        ArrayList<String> args = new ArrayList<>();
        String output = outDirectory + "/" + fileName;
        args.add(getConvertExecutablePath());
        args.add("-size");
        args.add(String.format("%dx%d", c.getWidth(), c.getHeight()));
        args.add("-background");
        args.add(c.getBackgroundColor());
        for (Layer l: c.getLayers()) {
            args.add("-page");
            args.add(String.format("+%d+%d", l.getX(), l.getY()));
            args.add(String.format("%s", l.getImage().getCurrentPath()));
        }
        if (c.isCanvasAuto()) {
            args.add("-layers");
            args.add(" merge");
        } else {
            args.add("-flatten");
        }
        args.add(output);
        System.out.println(output);
        runProcessBuilder(args, output);
    }
*/

    /**
     * Given any canvas, renders it to the target panel.
     * @param c - Canvas to render
     * @param logger - an instance of the logger
     * @param target - the name of the ImagePanel to target
     */
    public static void render(Canvas c, Logger logger, String target) {
        try {
            EventsCenter.getInstance().post(
                    new ChangeImageEvent(
                            SwingFXUtils.toFXImage(
                                    ImageMagickUtil.processCanvas(c), null), target));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        } catch (InterruptedException | IllegalOperationException e) {
            logger.severe(e.getMessage());
        }

    }

}
