package seedu.address.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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
}
