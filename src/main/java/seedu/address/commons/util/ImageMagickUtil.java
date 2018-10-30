package seedu.address.commons.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.canvas.Canvas;
import seedu.address.model.canvas.Layer;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 */
public class ImageMagickUtil {
    //initialize the paths used in the imageMagic
    public static final String IMAGEMAGIC_PATH = ImageMagickUtil.class.getResource("/imageMagic").getPath();
    private static final int LINUX = 1;
    private static final int WINDOWS = 2;
    private static final int MAC = 3;
    //for windows, as the path contains the / before the disk name, remove the first char
    public static final Path SINGLE_COMMAND_TEMPLATE_PATH = Paths.get((
            getPlatform() == WINDOWS ? IMAGEMAGIC_PATH.substring(1) : IMAGEMAGIC_PATH) + "/commandTemplate.json");
    public static final String TMP_PATH = Paths.get((
            getPlatform() == WINDOWS ? IMAGEMAGIC_PATH.substring(1) : IMAGEMAGIC_PATH)) + "/tmp";

    /**
     * get the path of the package location
     * @return
     * @throws NoSuchElementException
     */
    public static String getImageMagicPackagePath() throws NoSuchElementException {
        switch(getPlatform()) {
        case MAC:
            return IMAGEMAGIC_PATH + "/package/mac/";
        case WINDOWS:
            return IMAGEMAGIC_PATH + "/package/win";
        default:
        }
        return "";
    }

    public static String getExecuteImageMagic() throws NoSuchElementException {
        File folder = new File(getImageMagicPackagePath());
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                switch (getPlatform()) {
                case MAC:
                    return file.getAbsolutePath() + "/bin/magick";
                case WINDOWS:
                    return file.getAbsolutePath() + "/magick.exe";
                default:
                }
            }
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
        ArrayList<String> cmds = parseArguments(SINGLE_COMMAND_TEMPLATE_PATH, transformation);
        String modifiedFile = TMP_PATH + "/output.png";
        //create a processbuilder to blur the image
        ArrayList<String> args = new ArrayList<>();
        args.add(ImageMagickUtil.getExecuteImageMagic());
        args.add(path.toAbsolutePath().toString());
        args.add("-" + cmds.get(0));
        for (int i = 1; i < cmds.size(); i++) {
            args.add(cmds.get(i));
        }
        args.add(modifiedFile.toString());
        return runProcessBuilder(args, modifiedFile);
    }

    /**
     * parse the argument passing to the imageMagic to check the validation about the arguments
     * @param filepath
     * @param transformation
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static ArrayList<String> parseArguments(Path filepath, Transformation transformation)
            throws IOException, ParseException {
        ArrayList<String> trans = transformation.toList();
        String operation = trans.get(0);
        List<String> cmds = JsonConvertArgsStorage.retrieveArgumentsTemplate(filepath, operation);
        int num = cmds.size();
        if (num != trans.size() - 1) {
            throw new ParseException("Invalid arguments, should be " + cmds.toArray().toString());
        }
        return trans;
    }

    //@@author j-lum

    /**
     * Given a list of arguments to ImageMagick, calls the actual ImageMagick executable with the output set to the
     * path provided
     * @param args An ArrayList of arguments, the first of which needs to be a legal ImageMagick executable.
     * @param output An URL to the output file.
     * @return
     * @throws IOException
     * @throws InterruptedException
     */

    public static BufferedImage runProcessBuilder(ArrayList<String> args, String output) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(args);
        if (getPlatform() == MAC) {
            Map<String, String> mp = pb.environment();
            mp.put("DYLD_LIBRARY_PATH", ImageMagickUtil.getImageMagicPackagePath() + "ImageMagick-7.0.8/lib/");
        }
        Process process = pb.start();
        process.waitFor();
        List<String> tmp = pb.command();
        System.out.println(tmp);
        FileInputStream is = new FileInputStream(output);
        Image modifiedImage = new Image(is);
        InputStream error = process.getErrorStream();
        for (int i = 0; i < error.available(); i++) {
            System.out.println("" + error.read());
        }
        return SwingFXUtils.fromFXImage(modifiedImage, null);
    }

    /**
     * Creates a ProcessBuilder instance to merge/flatten layers.
     * @param c - A canvas to be processed
     * @return a buffered image with a merged canvas.
     */
    public static BufferedImage processCanvas(Canvas c) throws IOException, InterruptedException {
        ArrayList<String> args = new ArrayList<>();
        String output = TMP_PATH + "/modified.png";
        args.add(getExecuteImageMagic());
        args.add("-background");
        args.add(String.format("%s", c.getBackgroundColor()));
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
        return runProcessBuilder(args, output);
    }
}
