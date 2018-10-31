package seedu.address.commons.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * an Util to handle copying or unzipping the file outside of the resource when packing into jarfile
 * @author lancelotwillow
 */
public class ResourceUtil {

    /**
     * copy a file from the resource in the jar package outside
     * @param url
     * @param file
     * @throws IOException when the file not exists
     */
    public static void copyResourceFileOut(URL url, File file) throws IOException {
        InputStream io = url.openStream();
        OutputStream os = new FileOutputStream(file);
        int read;
        byte[] bytes = new byte[1024];
        while ((read = io.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        io.close();
        os.close();
    }

    /**
     * unzip a folder to the directory the zip file stays
     * @param zfile
     * @throws IOException when the inputStream, outputStream, ZipStream cannot read anything
     */
    public static void unzipFolder(File zfile) throws IOException {
        //get the parent folder
        String parentFolder = zfile.getParent() + "/";
        //initialize the input streams
        FileInputStream fis = new FileInputStream(zfile);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry;
        BufferedOutputStream bos;
        boolean check = false;
        while ((entry = zis.getNextEntry()) != null) {
            //check the folder to be unzipped into exist or not
            if (!check) {
                String path = entry.getName();
                String folderName = path.split("/")[0];
                File unzippedFolder = new File(parentFolder + folderName);
                if (!(unzippedFolder.exists() && unzippedFolder.isDirectory())) {
                    unzippedFolder.mkdir();
                }
                check = true;
            }
            //make directory if the entry is a directory
            if (entry.isDirectory()) {
                File currentFile = new File(parentFolder + entry.getName());
                if (!currentFile.exists()) {
                    currentFile.mkdirs();
                }
            } else {
                //write the file to be unzipped
                FileOutputStream fos = new FileOutputStream(parentFolder + entry.getName());
                bos = new BufferedOutputStream(fos);
                byte[] buf = new byte[2048];
                int len;
                while ((len = zis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                zis.closeEntry();
                bos.close();
            }
        }
        zis.close();
    }
}
