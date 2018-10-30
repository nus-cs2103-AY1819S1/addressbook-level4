package seedu.address.commons.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 * @author lancelotwillow
 */
public class ImageMagickUtil {
    //initialize the paths used in the imageMagic
    public static final URL SINGLE_COMMAND_TEMPLATE_PATH =
            ImageMagickUtil.class.getResource("/imageMagic/commandTemplate.json");
    private static final int LINUX = 1;
    private static final int WINDOWS = 2;
    private static final int MAC = 3;
    private static String ectPath = "";
    public static String imageMagickPath = ImageMagickUtil.class.getResource("/imageMagic").getPath();
    public static String tmpPath = imageMagickPath + "/tmp";
    public static String commandSaveFolder;

    /**
     * get the path of the package location
     * @return
     * @throws NoSuchElementException
     */
    public static URL getImageMagickZipUrl() throws NoSuchElementException {
        switch (getPlatform()) {
        case MAC:
            return ImageMagickUtil.class.getResource("/imageMagic/package/mac/ImageMagick-7.0.8.zip");
        case WINDOWS:
            return ImageMagickUtil.class.getResource("/imageMagic/package/win/ImageMagick-7.0.8-14.zip");
        default:
            return ImageMagickUtil.class.getResource("/imageMagic/package/mac/ImageMagick-7.0.8.zip");
        }
    }

    public static String getExecuteImageMagick() throws NoSuchElementException {
        if (ectPath != null) {
            return ectPath;
        }
        throw new NoSuchElementException("cannot find the file");
    }

    /**
     * get the platform;
     * @return
     */
    public static int getPlatform() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return WINDOWS;
        } else if (os.contains("mac")) {
            return MAC;
        } else if (os.contains("nux") || os.contains("ubuntu")) {
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
    public static BufferedImage processImage(Path path, Transformation transformation)
            throws ParseException, IOException, InterruptedException {
        List<String> cmds = parseArguments(transformation);
        File modifiedFile = new File(tmpPath + "/modified.png");
        //create a process builder to blur the image
        ProcessBuilder pb;
        List<String> args = new ArrayList<>();
        args.add(ImageMagickUtil.getExecuteImageMagick());
        args.add(path.toAbsolutePath().toString());
        args.add("-" + cmds.get(0));
        for (int i = 1; i < cmds.size(); i++) {
            args.add(cmds.get(i));
        }
        args.add(modifiedFile.getAbsolutePath());
        pb = new ProcessBuilder(args);
        //set the environment of the process builder if the current OS is OSX
        if (getPlatform() == MAC) {
            Map<String, String> mp = pb.environment();
            mp.put("DYLD_LIBRARY_PATH", imageMagickPath + "/ImageMagick-7.0.8/lib/");
        }
        Process process = pb.start();
        process.waitFor();

        FileInputStream inputStream = new FileInputStream(tmpPath + "/modified.png");
        Image modifiedImage = new Image(inputStream);
        return SwingFXUtils.fromFXImage(modifiedImage, null);
    }

    /**
     * parse the argument passing to the imageMagic to check the validation about the arguments
     * @param transformation
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static List<String> parseArguments(Transformation transformation)
            throws IOException, ParseException {
        List<String> trans = transformation.toList();
        String operation = trans.get(0);
        if (!operation.startsWith("@")) {
            URL fileUrl = ImageMagickUtil.class.getResource("/imageMagic/commandTemplates/" + operation + ".json");
            if (fileUrl == null) {
                throw new ParseException("Invalid argument, cannot find");
            }
            List<String> cmds = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation);
            int num = cmds.size();
            if (num != trans.size() - 1) {
                throw new ParseException("Invalid arguments, should be " + cmds.toArray().toString());
            }
            return trans;
        } else {
            operation = operation.substring(1);
            File file = new File(commandSaveFolder + "/" + operation + ".json");
            if (!file.exists()) {
                throw new ParseException("Invalid argument, cannot find");
            }
            List<String> cmds = JsonConvertArgsStorage.retrieveCommandArguments(file, operation);
            return cmds;
        }
    }

    /**
     * copy the imageMagick outside of the jarfile in order to call it.
     * @param userPrefs
     * @throws IOException
     * @throws InterruptedException
     */
    public static void copyOutside(UserPrefs userPrefs) throws IOException, InterruptedException {
        URL zipUrl = getImageMagickZipUrl();
        Path currentPath = userPrefs.getCurrDirectory();
        File zipFile = new File(currentPath.toString() + "/temp.zip");
        File tempFolder = new File(userPrefs.getCurrDirectory() + "/tempFolder");
        tempFolder.mkdir();
        ResourceUtil.copyResourceFileOut(zipUrl, zipFile);
        switch (getPlatform()) {
        case MAC:
            Process process = new ProcessBuilder(
                    "tar", "zxvf", zipFile.getPath(), "-C", currentPath.toString()).start();
            process.waitFor();
            //remove the __MACOSX folder in the mac
            new ProcessBuilder("rm", "-rf", currentPath.toString() + "/__MACOSX").start();
            ectPath = currentPath.toString() + "/ImageMagick-7.0.8/bin/magick";
            break;
        case WINDOWS:
            ResourceUtil.unzipFolder(zipFile);
            ectPath = currentPath.toString() + "/ImageMagick-7.0.8-14-portable-Q16-x64/magick.exe";
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

}
