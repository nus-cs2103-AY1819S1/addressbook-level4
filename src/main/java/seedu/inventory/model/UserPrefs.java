package seedu.inventory.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.inventory.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;

    private Path inventoryFilePath = Paths.get("data" , "inventory.xml");

    private Path staffListFilePath = Paths.get("data", "staffList.xml");

    public UserPrefs() {
        setGuiSettings(750, 600, 10, 10);
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

    public Path getInventoryFilePath() {
        return inventoryFilePath;
    }

    public void setInventoryFilePath(Path inventoryFilePath) {
        this.inventoryFilePath = inventoryFilePath;
    }

    public Path getStaffListFilePath() {
        return staffListFilePath;
    }

    public void setStaffListFilePath(Path inventoryFilePath) {
        this.staffListFilePath = staffListFilePath;
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
                && Objects.equals(inventoryFilePath, o.inventoryFilePath)
                && Objects.equals(staffListFilePath, o.staffListFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, inventoryFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + inventoryFilePath);
        sb.append("\nLocal staff data file location : " + staffListFilePath);
        return sb.toString();
    }

}
