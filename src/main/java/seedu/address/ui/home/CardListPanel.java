package seedu.address.ui.home;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ExtraInformationDisplay;
import seedu.address.commons.events.ui.CardPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ExtraInformationDisplayChangeEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.card.Card;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of cards.
 */
public class CardListPanel extends UiPart<Region> {
    private static final String FXML = "/home/CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    @FXML
    private ListView<Card> cardListView;

    public CardListPanel(ObservableList<Card> cardList) {
        super(FXML);
        setConnections(cardList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Card> cardList) {
        cardListView.setItems(cardList);
        cardListView.setCellFactory(listView -> new CardListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        cardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in card list panel changed to : '" + newValue + "'");
                        raise(new CardPanelSelectionChangedEvent(newValue));
                        raise(new ExtraInformationDisplayChangeEvent(ExtraInformationDisplay.BROWSER));
                    }
                });
    }


    /**
     * Scrolls to the {@code CardView} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            cardListView.scrollTo(index);
            cardListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code CardView}.
     */
    class CardListViewCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CardView(card, getIndex() + 1).getRoot());
            }
        }
    }
}
