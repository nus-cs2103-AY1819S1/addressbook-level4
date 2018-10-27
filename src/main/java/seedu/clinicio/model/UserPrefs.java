package seedu.clinicio.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.clinicio.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path clinicIoFilePath = Paths.get("data" , "clinicio.xml");

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

    public Path getClinicIoFilePath() {
        return clinicIoFilePath;
    }

    public void setClinicIoFilePath(Path clinicIoFilePath) {
        this.clinicIoFilePath = clinicIoFilePath;
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
                && Objects.equals(clinicIoFilePath, o.clinicIoFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, clinicIoFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + clinicIoFilePath);
        return sb.toString();
    }

}
