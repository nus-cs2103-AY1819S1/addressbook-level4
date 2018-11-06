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

    private static int currBatchPointer = 0;

    private GuiSettings guiSettings;
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
        initImageList();
    }

    /**
     * Update the list of images {@code imageList} with the images found in current directory
     * {@code currDirectory}
     */
    public void initImageList() {
        File currFileDir = new File(currDirectory.toString());
        File[] currFiles = currFileDir.listFiles();
        ArrayList<Path> dirImageList = new ArrayList<>();
        try {
            for (File file : currFiles) {
                if (file.isFile()) {
                    String mimetype = new MimetypesFileTypeMap().getContentType(file);
                    // only list if is image
                    if ((mimetype.split("/")[0]).equals("image")) {
                        dirImageList.add(file.toPath());
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Collections.sort(dirImageList);
        imageList = dirImageList;
        currBatchPointer = 0;
    }

    /**
     * Returns the total number of images in {@code imageList}
     */
    public int getTotalImagesInDir() {
        return imageList.size();
    }

    /**
     * Returns the {@code currBatchPointer}
     */
    public int numOfRemainingImagesInDir() {
        return getTotalImagesInDir() - getCurrBatchPointer();
    }

    /**
     * Returns the current batch pointer in {@code UserPrefs}
     */
    public int getCurrBatchPointer() {
        return currBatchPointer;
    }

    /**
     * Update the {@code currBatchPointer} to the next 10 images
     */
    public void updateImageListNextBatch() {
        currBatchPointer += 10;
    }

    /**
     * Update the {@code currBatchPointer} to the prev 10 images
     */
    public void updateImageListPrevBatch() {
        currBatchPointer -= 10;
    }

    public List<Path> getCurrImageListBatch() {
        return imageList.subList(currBatchPointer, Math.min(currBatchPointer + 10, getTotalImagesInDir()));
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

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        return sb.toString();
    }

}
