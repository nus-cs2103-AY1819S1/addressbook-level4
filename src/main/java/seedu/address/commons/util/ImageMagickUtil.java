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
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 */
public class ImageMagickUtil {
    //initialize the paths used in the imageMagic
    public static String IMAGEMAGIC_PATH = ImageMagickUtil.class.getResource("/imageMagic").getPath();
    private static final int LINUX = 1;
    private static final int WINDOWS = 2;
    private static final int MAC = 3;
    private static String ECT_PATH = "";
    //for windows, as the path contains the / before the disk name, remove the first char
    public static final URL SINGLE_COMMAND_TEMPLATE_PATH =
            ImageMagickUtil.class.getResource("/imageMagic/commandTemplate.json");
    public static String TMP_PATH = IMAGEMAGIC_PATH + "/tmp";

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
            return ImageMagickUtil.class.getResource(
                   "/imageMagic/package/win/ImageMagick-7.0.8-14-portable-Q16-x64.zip");
        default:
            return ImageMagickUtil.class.getResource("/imageMagic/package/mac/ImageMagick-7.0.8.zip");
        }
    }

    public static String getExecuteImageMagic() throws NoSuchElementException {
        File folder = new File(getImageMagicPackagePath());
        if (folder.exists()) {
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
        } else {
            return ECT_PATH;
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
        Logger logger = LogsCenter.getLogger(MainApp.class);
        List<String> cmds = parseArguments(SINGLE_COMMAND_TEMPLATE_PATH, transformation);
        File modifiedFile = new File(TMP_PATH + "/modified.png");
        //create a processbuilder to blur the image
        ProcessBuilder pb;
        List<String> args = new ArrayList<>();
        args.add(ImageMagickUtil.getExecuteImageMagic());
        logger.info(String.format("calling: %s", getExecuteImageMagic()));
        args.add(path.toAbsolutePath().toString());
        args.add("-" + cmds.get(0));
        for (int i = 1; i < cmds.size(); i++) {
            args.add(cmds.get(i));
        }
        args.add(modifiedFile.getAbsolutePath());
        pb = new ProcessBuilder(args);
        //set the environment of the processbuilder
        if (getPlatform() == MAC) {
            Map<String, String> mp = pb.environment();
            mp.put("DYLD_LIBRARY_PATH", IMAGEMAGIC_PATH + "/ImageMagick-7.0.8/lib/");
        }
        Process process = pb.start();
        process.waitFor();
        String s = process.getErrorStream().toString();
        logger.warning(pb.environment().get("DYLD_LIBRARY_PATH"));
        logger.warning(String.join(" " , pb.command()));
        /*
        if(s.length() > 1) {
            throw new ParseException(s);
        }
        */
        FileInputStream inputstream = new FileInputStream(TMP_PATH + "/modified.png");
        Image modifiedImage = new Image(inputstream);
        return SwingFXUtils.fromFXImage(modifiedImage, null);
    }

    /**
     * parse the argument passing to the imageMagic to check the validation about the arguments
     * @param fileUrl
     * @param transformation
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static List<String> parseArguments(URL fileUrl, Transformation transformation)
            throws IOException, ParseException {
        List<String> trans = transformation.toList();
        String operation = trans.get(0);
        List<String> cmds = JsonConvertArgsStorage.retrieveArgumentsTemplate(fileUrl, operation);
        int num = cmds.size();
        if (num != trans.size() - 1) {
            throw new ParseException("Invalid arguments, should be " + cmds.toArray().toString());
        }
        return trans;
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
            Process p = new ProcessBuilder("tar", "zxvf", zipFile.getPath(), "-C", currentPath.toString()).start();
            p.waitFor();
            //remove the __MACOSX folder in the mac
            Process p2 = new ProcessBuilder("rm", "-rf", currentPath.toString() + "/__MACOSX").start();
            break;
        case WINDOWS:
            p = new ProcessBuilder("tar", "zxvf", zipFile.getPath(), "-C", currentPath.toString()).start();
            p.waitFor();
            break;
        default:
        }
        IMAGEMAGIC_PATH = currentPath.toString();
        ECT_PATH = currentPath.toString() + "/ImageMagick-7.0.8/bin/magick";
        TMP_PATH = tempFolder.getPath();
        zipFile.delete();
        //p.waitFor();
        /*
        File zfile = file1;
        String Parent = zfile.getParent()+"/";
        FileInputStream fis = new FileInputStream(zfile);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry = null;
        BufferedOutputStream bos = null;
        while ((entry=zis.getNextEntry())!= null) {
            if (entry.isDirectory()) {
                File filePath=new File(Parent+entry.getName());
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
            } else {
                FileOutputStream fos = new FileOutputStream(Parent+entry.getName());
                bos=new BufferedOutputStream(fos);
                byte buf[] = new byte[1024];
                int len;
                while ((len = zis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                zis.closeEntry();
                bos.close();
            }
        }
        zis.close();
        */
    }

}
