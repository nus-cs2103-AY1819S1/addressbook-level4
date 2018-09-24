package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;

import seedu.address.commons.util.FileUtil;

/**
 * A class to access Email directory in the hard disk
 */
public class EmailDirStorage implements EmailStorage {

    private Path dirPath;

    public EmailDirStorage(Path dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public Path getEmailPath() { return dirPath; }

    @Override
    public void saveEmail(Email email) throws IOException {
        String fileName = email.getSubject().concat(Integer.toString(email.hashCode())).concat(".eml");
        String toSave = EmailConverter.emailToEML(email);
        dirPath.resolve(fileName);
        FileUtil.writeToFile(dirPath, toSave);
    }

}
