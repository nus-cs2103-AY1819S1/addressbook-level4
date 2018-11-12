package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TabChangeEvent;
import seedu.address.commons.events.ui.CloseTriviaTestViewEvent;
import seedu.address.commons.events.ui.ShowTriviaTestViewEvent;
import seedu.address.commons.events.ui.ToggleTabEvent;
import seedu.address.logic.Logic;
import seedu.address.ui.home.Homepage;
import seedu.address.ui.review.ReviewPage;
import seedu.address.ui.test.TriviaTestPlaceholderPage;

/**
 * A Ui class that is responsible for storing the placeholders for all the pages that exist in the MainDisplay.
 */
public class MainDisplay extends UiPart<Region> {

    private static final String FXML = "MainDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Homepage homepage;
    private final TriviaTestPlaceholderPage triviaTestPlaceholderPage;
    private final ChangeListener<Tab> changeTabListener = (observableValue, oldValue, newValue) -> {
        EventsCenter.getInstance().post(new TabChangeEvent(newValue.getText()));
    };
    private final ReviewPage reviewPage;

    @FXML
    private TabPane tabContainer;
    @FXML
    private Tab learnTab;
    @FXML
    private Tab testTab;
    @FXML
    private Tab reviewTab;
    @FXML
    private StackPane homepagePlaceholder;
    @FXML
    private StackPane triviaTestPlaceholder;
    @FXML
    private StackPane reviewPlaceholder;

    public MainDisplay(Logic logic) {
        super(FXML);
        configureView();

        homepage = new Homepage(logic);
        homepagePlaceholder.getChildren().add(homepage.getRoot());

        triviaTestPlaceholderPage = new TriviaTestPlaceholderPage();
        triviaTestPlaceholder.getChildren().add(triviaTestPlaceholderPage.getRoot());
        addTabListener();

        reviewPage = new ReviewPage();
        reviewPlaceholder.getChildren().add(reviewPage.getRoot());

        registerAsAnEventHandler(this);
    }

    /**
     * Add tab change listener.
     */
    private void addTabListener() {
        tabContainer.getSelectionModel().selectedItemProperty().addListener(changeTabListener);
    }

    /**
     * Remove the tab change listener.
     */
    private void removeTabListener() {
        tabContainer.getSelectionModel().selectedItemProperty().removeListener(changeTabListener);
    }

    /**
     * Restore all the pages to its original state. Currently only homepage is required for this restoration.
     */
    private void resetToOriginalState() {
        homepage.resetToOriginalState();
    }

    /**
     * creates the view configuration of the tabs
     */
    private void configureView() {
        //list of tabs configured
        createTab(learnTab, "Learn", homepagePlaceholder);
        createTab(testTab, "Test", triviaTestPlaceholder);
        createTab(reviewTab, "Review", reviewPlaceholder);

        logger.info("View has been configured");
    }

    /**
     * Creates a tab with the following parameters
     * @param tab
     * @param title
     * @param containerPane
     */
    private void createTab(Tab tab, String title, StackPane containerPane) {
        Label label = new Label(title);

        //set the tab's outlook into a border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setRotate(90.0);
        borderPane.setBottom(label);

        try {
            tab.setContent(containerPane);
            logger.info("tab created");
        } catch (Exception e) {
            throw new IllegalArgumentException("Tab not added");
        }

    }

    public void releaseResources() {
        homepage.releaseResources();
    }

    @Subscribe
    public void handleToggleTabEvent(ToggleTabEvent event) {
        if (event.getToToggleTo().equals("learn")) {
            tabContainer.getSelectionModel().select(learnTab);
        } else if (event.getToToggleTo().equals("test")) {
            tabContainer.getSelectionModel().select(testTab);
        } else if (event.getToToggleTo().equals("review")) {
            tabContainer.getSelectionModel().select(reviewTab);
        }
    }

    @Subscribe
    private void handleShowTriviaTestViewEvent(ShowTriviaTestViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        removeTabListener();
        tabContainer.getSelectionModel().select(testTab);
        learnTab.setDisable(true);
        reviewTab.setDisable(true);
        addTabListener();
    }

    @Subscribe
    private void handleCloseTriviaTestViewEvent(CloseTriviaTestViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        learnTab.setDisable(false);
        reviewTab.setDisable(false);
        resetToOriginalState();
    }
}
