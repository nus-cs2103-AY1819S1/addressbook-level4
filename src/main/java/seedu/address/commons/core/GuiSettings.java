package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;
    private Boolean notificationIsEnabled;
    private String favouriteEvent;

    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        notificationIsEnabled = true; // default value is true
        favouriteEvent = null;
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition,
                       boolean notificationIsEnabled, String favouriteEvent) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.notificationIsEnabled = notificationIsEnabled;
        this.favouriteEvent = favouriteEvent;
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }

    public Boolean getNotificationIsEnabled() {
        return notificationIsEnabled;
    }

    public String getFavouriteEvent() {
        return favouriteEvent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates.x, o.windowCoordinates.x)
                && Objects.equals(windowCoordinates.y, o.windowCoordinates.y)
                && Objects.equals(notificationIsEnabled, o.notificationIsEnabled)
                && Objects.equals(favouriteEvent, o.favouriteEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, notificationIsEnabled, favouriteEvent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates + "\n");
        sb.append("Notification : " + notificationIsEnabled + "\n");
        sb.append("Favourite : " + favouriteEvent);
        return sb.toString();
    }
}
