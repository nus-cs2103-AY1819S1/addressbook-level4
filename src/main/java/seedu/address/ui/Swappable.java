package seedu.address.ui;

//@@author snajef
/**
 * Interface used for swapping out panel views in the UI.
 * Every class that implements the Swappable interface should
 * also extend the UiPart<Region> class to allow for this functionality to work.
 *
 * A possible better implementation would involve the UiPart<> class as an interface,
 * and the Swappable interface extending it.
 * TODO?
 *
 * @author Darien Chong
 *
 */
public interface Swappable {
    public void refreshView();
}
