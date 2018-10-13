package seedu.souschef.ui;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.model.UniqueType;

/**
 * Panel containing the list of UniqueType cards.
 * @param <T> A UniqueType subclass.
 */
public abstract class GenericListPanel<T extends UniqueType> extends UiPart<Region> {

    public GenericListPanel(String fxmlFileName) {
        super(fxmlFileName);
    }

    /**
     * To be used in constructor.
     * @param list ObservableList of UniqueType objects.
     */
    protected abstract void setConnections(ObservableList<T> list);

    /**
     * To be used in setConnections().
     */
    protected abstract void setEventHandlerForSelectionChangeEvent();

    /**
     * Scrolls to the {@code Card} at the {@code index} and selects it.
     * To be used in handleJumpToListRequestEvent().
     */
    protected abstract void scrollTo(int index);

    @Subscribe
    protected abstract void handleJumpToListRequestEvent(JumpToListRequestEvent event);

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code UniqueType} using a {@code Card}.
     * Used in setConnections().
     */
    abstract class ListViewCell<T> extends ListCell<T> {
        @Override
        protected void updateItem(T element, boolean empty) {
            super.updateItem(element, empty);
        }
    }
}
