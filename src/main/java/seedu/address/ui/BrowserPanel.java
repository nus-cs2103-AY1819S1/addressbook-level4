package seedu.address.ui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.DisplayProfileEvent;
import seedu.address.commons.events.model.DisplayWeekScheduleEvent;
import seedu.address.commons.events.model.UserLoggedOutEvent;
import seedu.address.commons.events.ui.PanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RestaurantPanelSelectionChangedEvent;
import seedu.address.model.accounting.Debt;
import seedu.address.model.friend.Friendship;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.timetable.Date;
import seedu.address.model.user.User;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String RESTAURANT_PAGE = "browseRestaurant.html";
    public static final String DEBT_PAGE = "browseDebt.html";
    public static final String USER_PAGE = "displayProfile.html";
    public static final String JIO_PAGE = "browseJio.html";
    public static final String GROUP_PAGE = "browseGroup.html";
    public static final String DEFAULT_PAGE = "default.html";
    public static final String NOT_LOGGED_IN_PAGE = "browseNotLoggedIn.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    public static final String SCHEDULE_PAGE = "displayWeekSchedule.html";
    public static final String FRIEND_PAGE = "browseFriend.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads a browseRestaurant HTML file with a background that matches the general theme.
     */
    private void loadRestaurantPage(Restaurant restaurant) {

        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + RESTAURANT_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // replace the template with person stuff
        Object[] params = new Object[] {
                restaurant.getName(),
                restaurant.getPhone(),
                restaurant.getTags().stream().map(u -> u.tagName).collect(Collectors.joining(", ")),
                restaurant.getAddress(),
                restaurant.getReviews().getRestaurantRatingValue(),
                restaurant.getReviews().getUserReviewList().stream()
                        .map(userReview -> userReview.getUsername()
                                + "'s Rating: " + userReview.getRating() + "<br />"
                                + "Review: " + userReview.getWrittenReview() + "<br />")
                        .collect(Collectors.joining("<p></p>"))
        };
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });
    }


    /**
     * Loads a displayWeekSchedule HTML file with a background that matches the general theme.
     */
    private void loadDisplayWeekSchedulePage(String header, List<Date> dates) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + SCHEDULE_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb2 = new StringBuilder();
        // Creating the string of dates.

        dates.stream()
                .map(date -> "<li>" + date.toString() + "</li>")
                .forEach(sb2::append);

        // replace the template with dates.
        Object[] params = new Object[] {header,
                sb2.toString() };

        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });

    }
    /**
     * Loads a browseDebt HTML file with a background that matches the general theme.
     * All the data field will display on the browser panel,
     * including the creditor, debtor, amount, status and id.
     */
    private void loadDebtPage(Debt debt) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + DEBT_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // replace the template with person stuff
        Object[] params = new Object[] {
                debt.getCreditor().getUsername(),
                debt.getDebtor().getUsername(),
                debt.getAmount().toDouble(),
                debt.getDebtStatus(),
                debt.getDebtId()
        };
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });
    }

    /**
     * Loads a browseFriend HTML file with a background that matches the general theme.
     */
    private void loadFriendPage(Friendship friendship) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + FRIEND_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        User friendUser = friendship.getFriendUser();
        // replace the template with person stuff
        Object[] params = new Object[] {
                friendUser.getUsername(),
                friendUser.getName(),
                friendUser.getPhone(),
                friendUser.getEmail()
        };
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });
    }

    /**
     * Loads a displayProfile HTML file with a background that matches the general me.
     */
    private void loadUserProfilePage(User user) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + USER_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object[] params = new Object[] {
                user.getUsername(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getRestaurantReviews().stream()
                        .map(restaurantReview -> restaurantReview.getRestaurantName() + "<br />"
                        + "Rating Given: " + restaurantReview.getRating() + "<br />"
                        + "Review Given: " + restaurantReview.getWrittenReview() + "<br />")
                        .collect(Collectors.joining("<p></p>"))
        };
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });
    }



    /**
     * Loads a browseJio HTML file with a background that matches the general theme.
     */
    private void loadJioPage(Jio jio) {

        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + JIO_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // replace the template with person stuff
        Object[] params = new Object[] {
                jio.getName(),
                jio.getDate(),
                jio.getAddress(),
                jio.getPeople().stream().map(u -> u.toString()).collect(Collectors.joining("<p></p>"))
        };
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });
    }

    /**
     * Loads a browseGroup HTML file with a background that matches the general theme.
     */
    private void loadGroupPage(Group group) {

        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + GROUP_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // replace the template with person stuff
        Object[] params = new Object[] {
                group.getGroupName(),
                group.getAcceptedUsers().stream().map(u -> u.getName().toString() + " ["
                        + u.getUsername().toString() + "]")
                        .collect(Collectors.joining("<p></p>")),
                group.getPendingUsers().stream().map(u -> u.getName().toString() + " ["
                        + u.getUsername().toString() + "]")
                        .collect(Collectors.joining("<p></p>")),
        };
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Loads a Not logged in HTML file with a background that matches the general theme.
     */
    public void loadNotLoggedInPage() {

        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(MainApp.class
                    .getResourceAsStream(FXML_FILE_FOLDER + NOT_LOGGED_IN_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // replace the template with person stuff
        Object[] params = new Object[] {"You are not logged in."};
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
            browser.getEngine().loadContent(html);
        });
    }

    @Subscribe
    private void handleUserLoggedOutEvent(UserLoggedOutEvent event) {
        loadNotLoggedInPage();
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleRestaurantPanelSelectionChangedEvent(RestaurantPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() == null) {
            loadDefaultPage();
            return;
        }
        loadRestaurantPage(event.getNewSelection());
    }

    @Subscribe
    private void handlePanelSelectionChangedEvent(PanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() == null) {
            loadDefaultPage();
            return;
        }
        if (event.getNewSelection() instanceof Debt) {
            loadDebtPage((Debt) event.getNewSelection());
        } else if (event.getNewSelection() instanceof Jio) {
            loadJioPage((Jio) event.getNewSelection());
        } else if (event.getNewSelection() instanceof Group) {
            loadGroupPage((Group) event.getNewSelection());
        } else if (event.getNewSelection() instanceof Friendship) {
            loadFriendPage((Friendship) event.getNewSelection());
        }
    }

    @Subscribe
    private void handleDisplayProfileEvent(DisplayProfileEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadUserProfilePage(event.getCurrentUser());
    }

    @Subscribe
    private void handleDisplayWeekScheduleEvent(DisplayWeekScheduleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDisplayWeekSchedulePage(event.getHeader(), event.getDisplayedWeekSchedule());
    }


}
