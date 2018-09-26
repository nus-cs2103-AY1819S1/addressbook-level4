package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.simplejavamail.converter.EmailConverter;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.EmailModel;

/**
 * A class to access Email directory in the hard disk
 */
public class EmailDirStorage implements EmailStorage {

    private Path dirPath;

    public EmailDirStorage(Path dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public Path getEmailPath() {
        return dirPath;
    }

    @Override
    public void saveEmail(EmailModel email) throws IOException {
        Path fileName = Paths.get(dirPath.toString(), email.getEmail().getSubject().concat(".eml"));
        String toSave = EmailConverter.emailToEML(email.getEmail());
        FileUtil.createIfMissing(fileName);
        FileUtil.writeToFile(fileName, toSave);
    }

}
