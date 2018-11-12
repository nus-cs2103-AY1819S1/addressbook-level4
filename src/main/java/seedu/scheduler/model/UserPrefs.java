package seedu.scheduler.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.scheduler.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path schedulerFilePath = Paths.get("data", "scheduler.xml");

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

    public Path getSchedulerFilePath() {
        return schedulerFilePath;
    }

    public void setSchedulerFilePath(Path schedulerFilePath) {
        this.schedulerFilePath = schedulerFilePath;
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
        return Objects.hash(guiSettings, schedulerFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal scheduler data file location : " + schedulerFilePath);
        return sb.toString();
    }

}
