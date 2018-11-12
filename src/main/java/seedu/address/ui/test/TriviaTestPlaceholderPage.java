package seedu.address.ui.test;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CloseTriviaTestViewEvent;
import seedu.address.commons.events.ui.ShowTriviaTestResultEvent;
import seedu.address.commons.events.ui.ShowTriviaTestViewEvent;
import seedu.address.ui.UiPart;

/**
 * A class to display the different TriviaTest that 3VIA has.
 */
public class TriviaTestPlaceholderPage extends UiPart<Region> {

    private static final String FXML = "test/TriviaTestPlaceholderPage.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private TriviaTestPage triviaTestPage;
    private TriviaTestResultPage triviaTestResultPage;
    private TriviaTestMenuPage triviaTestMenuPage;

    @FXML
    private StackPane triviaTestPlaceholderPage;
    @FXML
    private StackPane triviaTestMenuPagePlaceholder;
    @FXML
    private StackPane triviaTestPagePlaceholder;
    @FXML
    private StackPane triviaTestResultPagePlaceholder;

    public TriviaTestPlaceholderPage() {
        super(FXML);

        triviaTestMenuPage = new TriviaTestMenuPage();
        triviaTestMenuPagePlaceholder.getChildren().add(triviaTestMenuPage.getRoot());

        bindNodesVisibilityProperty(triviaTestPlaceholderPage);
        resetToOriginalState();
        registerAsAnEventHandler(this);
    }

    /**
     * Display only the trivia test menu page.
     */
    private void resetToOriginalState() {
        changeToScene(triviaTestMenuPagePlaceholder);
    }

    @Subscribe
    private void handleShowTriviaTestViewEvent(ShowTriviaTestViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        triviaTestPage = event.getTriviaTestPage().get();
        triviaTestPagePlaceholder.getChildren().add(triviaTestPage.getRoot());
        changeToScene(triviaTestPagePlaceholder);
    }

    @Subscribe
    private void handleShowTriviaTestResultPage(ShowTriviaTestResultEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        triviaTestResultPage = event.getTriviaTestResultPage().get();
        triviaTestResultPagePlaceholder.getChildren().add(triviaTestResultPage.getRoot());
        changeToScene(triviaTestResultPagePlaceholder);
    }

    @Subscribe
    private void handleCloseTriviaTestViewEvent(CloseTriviaTestViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        triviaTestPagePlaceholder.getChildren().clear();
        triviaTestResultPagePlaceholder.getChildren().clear();
        changeToScene(triviaTestMenuPagePlaceholder);
    }

    /**
     * Change to the matchTest scene specified in the parameter.
     */
    private void changeToScene(Node changeTo) {
        triviaTestPlaceholderPage.getChildren().forEach(node -> {
            if (node.equals(changeTo)) {
                node.setVisible(true);
            } else {
                node.setVisible(false);
            }
        });
    }
}
