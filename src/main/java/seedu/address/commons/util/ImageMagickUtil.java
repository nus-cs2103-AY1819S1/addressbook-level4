package seedu.address.commons.util;

//import com.oracle.tools.packager.UnsupportedPlatformException;
//import com.sun.javafx.PlatformUtil;
//@@author j-lum

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

import javax.crypto.Mac;

//import com.sun.javafx.PlatformUtil;

//import seedu.address.model.VersionedAddressBook;

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 */
public class ImageMagickUtil {
    private static final int LINUX = 1;
    private static final int WINDOWS = 2;
    private static final int MAC = 3;
    public static final String IMAGEMAGIC_PATH = ImageMagickUtil.class.getResource("/imageMagic").getPath();
    public static final Path SINGLE_COMMAND_TEMPLATE_PATH = Paths.get(
            (getPlatform() == WINDOWS ? IMAGEMAGIC_PATH.substring(1) : IMAGEMAGIC_PATH) + "/commandTemplate.json");
    //public static final Path SINGLE_COMMAND_TEMPLATE_PATH = Paths.get("D:/Projects/main/out/production/resources/imageMagic/commandTemplate.json");
    public static final String TMPPATH = IMAGEMAGIC_PATH + "/tmp";

    /**
     * @return path an string to the location of the ImageMagick executable for a supported platform.
     */
    public static String getImageMagickPath() /*throws UnsupportedPlatformException*/ {
        /*if(PlatformUtil.isLinux()){
            return "convert";
        }else
        if (PlatformUtil.isMac()) {
            return "convert";
        } else if (PlatformUtil.isWindows()) {
            return "convert";
        } else {
            //TODO: make a new exception that allows us to specify an error message.
            //*throw new UnsupportedPlatformException();*//*
            return "cry deeply";
        }
        */
        return "";
    }

    /**
     * author lancelotwillow
     * method used to download and store the package
     */
    public static void storePackage() {
        String urlString = getImageMagicUrl();
        try {
            final String fileLocation =
                    getImageMagicPackagePath();
            URL url = new URL(urlString);
            Path path = Paths.get(fileLocation);
            InputStream stream = url.openStream();
            Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

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
        }
        return "";
    }

    /**
     * @author lancelotwillow
     * get the url for the portable zip file of the imagemagic
     * @return
     */
    private static String getImageMagicUrl() {
        switch(getPlatform()) {
        case LINUX:
            return "convert";
        case WINDOWS:
            return "https://imagemagick.org/download/binaries/ImageMagick-7.0.8-12-Q16-x64-dll.exe";
        case MAC:
            return "https://www.imagemagick.org/download/binaries/ImageMagick-x86_64-apple-darwin17.7.0.tar.gz";
        default:
            return "";
        }
    }

    /**
     * @author lancelotwillow
     * check whether there are any package inside
     * @return
     */
    public static boolean hasPackage() {
        try {
            String path = getImageMagicPackagePath();
            File file = new File(path);
            return file.isDirectory() && file.list().length > 0;
        } catch (NoSuchElementException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * @author lancelotwillow
     * check whether there is any exectuable
     * @return
     */
    private static boolean hasExecutable() {
        try {
            String path = getImageMagicPackagePath();
            File file = new File(path);
            File[] fileList = file.listFiles();
            for (File current : fileList) {
                if (current.isDirectory()) {
                    File bin = new File(current.getAbsolutePath() + "/bin/magick");
                    return bin.exists();
                }
            }
            return false;
        } catch (NoSuchElementException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * @author lancelotwillow
     * the method is to unzip the package
     */
    public static void unzipPacakge() throws IOException, InterruptedException {
        File folder = new File(getImageMagicPackagePath());
        File[] listOfFiles = folder.listFiles();
        File zipfile = listOfFiles[0];
        for (File file : listOfFiles) {
            if (hasExecutable()) {
                return;
            }
            if (!zipfile.getName().endsWith(".tar.gz") && file.getName().endsWith(".tar.gz")) {
                zipfile = file;
            }
        }
        if (!zipfile.getName().endsWith(".tar.gz")) {
            System.err.println("cannot find the package");
            return;
        }
        do {
            System.out.println(zipfile.getAbsolutePath());
            ProcessBuilder pb = new ProcessBuilder(
                    folder.getAbsolutePath() + "/../../unzipPackage.sh");
            Process process = pb.start();
            //process.wait(10);
            System.out.println("");
        } while (!hasExecutable());
        ProcessBuilder pb = new ProcessBuilder(
                folder.getAbsolutePath() + "/../../init.sh");
        //process.waitFor();
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
        List<String> cmds = parseArguments(SINGLE_COMMAND_TEMPLATE_PATH, transformation);
        File modifiedFile = new File(TMPPATH + "/modified.png");
        //create a processbuilder to blur the image
        ProcessBuilder pb;
        List<String> args = new ArrayList<>();
        args.add(ImageMagickUtil.getExecuteImageMagic());
        args.add(path.toAbsolutePath().toString());
        args.add("-" + cmds.get(0));
        for (int i = 1; i < cmds.size(); i++) {
            args.add(cmds.get(i));
        }
        args.add(modifiedFile.getAbsolutePath());
        pb = new ProcessBuilder(args);
        //set the environment of the processbuilder
        if(getPlatform() == MAC) {
            Map<String, String> mp = pb.environment();
            mp.put("DYLD_LIBRARY_PATH", ImageMagickUtil.getImageMagicPackagePath() + "ImageMagick-7.0.8/lib/");
        }
        Process process = pb.start();
        process.waitFor();
        FileInputStream inputstream = new FileInputStream(TMPPATH + "/modified.png");
        Image modifiedImage = new Image(inputstream);
        return SwingFXUtils.fromFXImage(modifiedImage, null);
    }

    /**
     * parse the argument passing to the imageMagic to check the validation about the arguments
     * @param filepath
     * @param transformation
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static List<String> parseArguments(Path filepath, Transformation transformation)
            throws IOException, ParseException {
        int i;
        List<String> trans = transformation.toList();
        String operation = trans.get(0);
        List<String> cmds = JsonConvertArgsStorage.retrieveArgumentsTemplate(filepath, operation);
        int num = cmds.size();
        if (num != trans.size() - 1) {
            throw new ParseException("Invalid arguments, should be " + cmds.toArray().toString());
        }
        return trans;
    }
}
