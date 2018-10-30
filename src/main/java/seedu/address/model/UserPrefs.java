package seedu.address.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.activation.MimetypesFileTypeMap;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path addressBookFilePath = Paths.get("data" , "addressbook.xml");
    private Path currDirectory = Paths.get(System.getProperty("user.home"));
    private ArrayList<Path> imageList = new ArrayList<>();

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
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

    // @@author benedictcss
    public Path getCurrDirectory() {
        return currDirectory;
    }

    /**
     * Update the current directory {@code currDirectory} with the new directory
     * {@code newCurrDirectory}
     */
    public void updateUserPrefs(Path newCurrDirectory) {
        this.currDirectory = newCurrDirectory;
        updateImageList();
    }

    /**
     * Update the list of images {@code imageList} with the images found in current directory
     * {@code currDirectory}
     */
    public void updateImageList() {
        File currFileDir = new File(currDirectory.toString());
        File[] currFiles = currFileDir.listFiles();
        ArrayList<Path> dirImageList = new ArrayList<>();
        for (File file : currFiles) {
            if (file.isFile()) {
                String mimetype = new MimetypesFileTypeMap().getContentType(file);
                // only list if is image
                if ((mimetype.split("/")[0]).equals("image")) {
                    dirImageList.add(file.toPath());
                }
            }
        }
        Collections.sort(dirImageList);
        imageList = dirImageList;
    }

    /**
     * Update the list of images {@code imageList} with the images found in current directory
     * {@code currDirectory}
     */
    public void updateImageList(ArrayList<Path> dirImageList) {
        imageList = dirImageList;
    }

    /**
     * Get preview image list (first 10 images in imageList)
     */
    public List<Path> returnPreviewImageList() {

        if (imageList.size() > 10) {
            return imageList.subList(0, 10);
        } else {
            return imageList;
        }
    }

    public ArrayList<Path> getAllImages() {
        return imageList;
    }
    // @@author

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
                && Objects.equals(addressBookFilePath, o.addressBookFilePath);
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
        return sb.toString();
    }

}
