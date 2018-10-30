package seedu.address.commons.util;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

import java.io.*;
import java.net.URL;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * .
 */
public class ResourceUtil {

    /**
     * copy a file from the resource in the jar package outside
     * @param url
     * @param file
     * @throws IOException
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

    public static void unzipFolder(File zfile) throws IOException{
        String Parent = zfile.getParent()+"/";
        FileInputStream fis = new FileInputStream(zfile);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry = null;
        BufferedOutputStream bos = null;
        boolean check = false;
        while ((entry = zis.getNextEntry())!= null) {
            if (!check) {
                String path = entry.getName();
                Logger logger = LogsCenter.getLogger(MainApp.class);
                logger.warning(path);
                String folderName = path.split("/")[0];
                logger.warning(folderName);
                new File(Parent + folderName).mkdir();
                check = true;
            }
            if (entry.isDirectory()) {
                File filePath = new File(Parent+entry.getName());
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
    }
}
