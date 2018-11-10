package seedu.restaurant.ui.account;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.events.ui.accounts.JumpToAccountListRequestEvent;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.ui.UiPart;

//@@author AZhiKai
/**
 * Panel containing the list of {@code Account}s.
 */
public class AccountListPanel extends UiPart<Region> {

    private static final String FXML = "AccountListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AccountListPanel.class);

    @FXML
    private ListView<Account> accountListView;

    public AccountListPanel(ObservableList<Account> accountList) {
        super(FXML);
        setConnections(accountList);
        registerAsAnEventHandler(this);
    }

    @FXML
    private void handleMouseClick() {
        Account account = accountListView.getSelectionModel().getSelectedItem();
        logger.fine("Selection in account list panel changed to : '" + account + "'");
    }

    private void setConnections(ObservableList<Account> itemList) {
        accountListView.setItems(itemList);
        accountListView.setCellFactory(listView -> new AccountListViewCell());
    }

    /**
     * Scrolls to the {@code AccountCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            accountListView.scrollTo(index);
            accountListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToAccountListRequestEvent(JumpToAccountListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Account} using a {@code AccountCard}.
     */
    private class AccountListViewCell extends ListCell<Account> {

        @Override
        protected void updateItem(Account account, boolean empty) {
            super.updateItem(account, empty);

            if (empty || account == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AccountCard(account, getIndex() + 1).getRoot());
            }
        }
    }
}
