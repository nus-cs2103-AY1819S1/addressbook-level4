package seedu.address.storage;

import static org.simplejavamail.converter.EmailConverter.emlToEmail;
import static seedu.address.commons.util.FileUtil.readFromFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.EmailModel;

//@@author EatOrBeEaten
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
    public void saveEmail(EmailModel emailModel) throws IOException {
        Path fileName = Paths.get(dirPath.toString(), emailModel.getEmail().getSubject().concat(".eml"));
        String toSave = EmailConverter.emailToEML(emailModel.getEmail());
        FileUtil.createIfMissing(fileName);
        FileUtil.writeToFile(fileName, toSave);
    }

    @Override
    public Email loadEmail(String emailName) throws IOException {
        String fileName = emailName + ".eml";
        Path pathToLoad = Paths.get(dirPath.toString(), fileName);
        String emlString = readFromFile(pathToLoad);
        Email loadedEmail = emlToEmail(emlString);
        return loadedEmail;
    }

}
