package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path schedulerFilePath = Paths.get("data", "scheduler.xml");
    private Path toDoListFilePath = Paths.get("data", "toDoList.xml");

    public UserPrefs() {
        setGuiSettings(1200, 675, 0, 0);
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

    public Path getSchedulerFilePath() {
        return schedulerFilePath;
    }

    public void setSchedulerFilePath(Path schedulerFilePath) {
        this.schedulerFilePath = schedulerFilePath;
    }

    public Path getToDoListFilePath() {
        return toDoListFilePath;
    }

    public void setToDoListFilePath(Path toDoListFilePath) {
        this.toDoListFilePath = toDoListFilePath;
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
            && Objects.equals(schedulerFilePath, o.schedulerFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, schedulerFilePath, toDoListFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal scheduler data file location : " + schedulerFilePath);
        sb.append("\nLocal todolist data file locattion: " + toDoListFilePath);
        return sb.toString();
    }

}
