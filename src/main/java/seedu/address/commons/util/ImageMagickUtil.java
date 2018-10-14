package seedu.address.commons.util;

//import com.oracle.tools.packager.UnsupportedPlatformException;
//import com.sun.javafx.PlatformUtil;
//@@author j-lum

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;

import com.sun.javafx.PlatformUtil;

//import seedu.address.model.VersionedAddressBook;

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 */
public class ImageMagickUtil {
    public static final String TMPPATH =
            "/Users/Lancelot/Desktop/CS2103T/project/main/src/main/java/seedu/address/storage/tmp/";
    /**
     * @return path an string to the location of the ImageMagick executable for a supported platform.
     */
    public static String getImageMagickPath() /*throws UnsupportedPlatformException*/ {
        /*if(PlatformUtil.isLinux()){
            return "convert";
        }else
        */
        if (PlatformUtil.isMac()) {
            return "convert";
        } else if (PlatformUtil.isWindows()) {
            return "convert";
        } else {
            //TODO: make a new exception that allows us to specify an error message.
            //*throw new UnsupportedPlatformException();*//*
            return "cry deeply";
        }
    }

    /**
     * author lancelotwillow
     * method used to download and store the package
     */
    public static void storePackage() {
        String urlString = getImageMagicUrl();
        try {
            final String fileLocation =
                    getImageMagicPackgaePath();
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
    private static String getImageMagicPackgaePath() throws NoSuchElementException {
        if (PlatformUtil.isMac()) {
            return "/Users/Lancelot/Desktop/CS2103T/project/main/src/main/resources/imageMagic/package/mac/";
        } else if (PlatformUtil.isWindows()) {
            return "/Users/Lancelot/Desktop/CS2103T/project/main/src/main/resources/imageMagic/package/win/";
        } else if (PlatformUtil.isLinux()) {
            return "/Users/Lancelot/Desktop/CS2103T/project/main/src/main/resources/imageMagic/package/linux";
        } else {
            throw new NoSuchElementException("unrecongnized OS");
        }
    }

    /**
     * @author lancelotwillow
     * get the url for the portable zip file of the imagemagic
     * @return
     */
    private static String getImageMagicUrl() {
        if (PlatformUtil.isLinux()) {
            return "convert";
        } else if (PlatformUtil.isMac()) {
            return "https://www.imagemagick.org/download/binaries/ImageMagick-x86_64-apple-darwin17.7.0.tar.gz";
        } else if (PlatformUtil.isWindows()) {
            return "https://imagemagick.org/download/binaries/ImageMagick-7.0.8-12-Q16-x64-dll.exe";
        } else {
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
            String path = getImageMagicPackgaePath();
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
            String path = getImageMagicPackgaePath();
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
        File folder = new File(getImageMagicPackgaePath());
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

    public static String getExecuteImagicMagic() throws NoSuchElementException {
        File folder = new File(getImageMagicPackgaePath());
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                return file.getAbsolutePath() + "/bin/magick";
            }
        }
        throw new NoSuchElementException("cannot find the file");
    }
}
