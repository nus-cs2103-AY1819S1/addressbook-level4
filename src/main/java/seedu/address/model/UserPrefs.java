package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Password;
import seedu.address.model.person.User;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path addressBookFilePath = Paths.get("data" , "addressbook.xml");
    private Path assignmentListFilePath = Paths.get("data", "assignmentlist.xml");
    private Password adminPassword;
    private Path archiveListFilePath = Paths.get("data", "archivelist.xml");

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
        adminPassword = User.ADMIN_DEFUALT_PASSWORD;
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getAssignmentListFilePath() {
        return assignmentListFilePath;
    }

    public void setAssignmentListFilePath(Path assignmentListFilePath) {
        this.assignmentListFilePath = assignmentListFilePath;
    }

    public Password getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(Password p) {
        adminPassword = p;
    }

    public Path getArchiveListFilePath() {
        return archiveListFilePath;
    }

    public void setArchiveListFilePath(Path archiveListFilePath) {
        this.archiveListFilePath = archiveListFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && adminPassword.equals(o.adminPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nAdmin Password: " + adminPassword);
        return sb.toString();
    }

}
