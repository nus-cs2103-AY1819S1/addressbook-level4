package seedu.restaurant.ui.reservation;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.events.ui.reservation.JumpToReservationListRequestEvent;
import seedu.restaurant.commons.events.ui.reservation.ReservationPanelSelectionChangedEvent;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.ui.UiPart;

//@@author m4dkip
/**
 * Panel containing the list of reservations.
 */
public class ReservationListPanel extends UiPart<Region> {

    private static final String FXML = "ReservationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReservationListPanel.class);

    @FXML
    private ListView<Reservation> reservationListView;

    public ReservationListPanel(ObservableList<Reservation> reservationList) {
        super(FXML);
        setConnections(reservationList);
        registerAsAnEventHandler(this);
    }

    @FXML
    private void handleMouseClick() {
        Reservation reservation = reservationListView.getSelectionModel().getSelectedItem();
        logger.fine("Selection in item list panel changed to : '" + reservation + "'");
        raise(new ReservationPanelSelectionChangedEvent(reservation));
    }

    private void setConnections(ObservableList<Reservation> reservationList) {
        reservationListView.setItems(reservationList);
        reservationListView.setCellFactory(listView -> new ReservationListViewCell());
    }

    /**
     * Scrolls to the {@code ReservationCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            reservationListView.scrollTo(index);
            reservationListView.getSelectionModel().clearAndSelect(index);
            raise(new ReservationPanelSelectionChangedEvent(reservationListView.getItems().get(index)));
        });
    }

    @Subscribe
    private void handleJumpToReservationListRequestEvent(JumpToReservationListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reservation} using a {@code ReservationCard}.
     */
    private class ReservationListViewCell extends ListCell<Reservation> {

        @Override
        protected void updateItem(Reservation reservation, boolean empty) {
            super.updateItem(reservation, empty);

            if (empty || reservation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReservationCard(reservation, getIndex() + 1).getRoot());
            }
        }
    }
}
