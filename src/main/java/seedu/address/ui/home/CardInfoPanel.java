package seedu.address.ui.home;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;
import seedu.address.model.test.Attempt;
import seedu.address.ui.TipBox;
import seedu.address.ui.UiPart;

/**
 * Displays the information of the card in InfoPanel.
 */
public class CardInfoPanel extends UiPart<Region> {
    private static final String FXML = "home/CardInfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private CardExperience cardExperience;
    private CardMostRecentMistake cardMostRecentMistake;

    @FXML
    private Label cardInfoQuestion;
    @FXML
    private FlowPane cardInfoTopics;
    @FXML
    private TextArea cardInfoAnswer;
    @FXML
    private StackPane researchCommandTipPlaceholder;
    @FXML
    private StackPane cardExperiencePlaceholder;
    @FXML
    private StackPane cardMostRecentMistakePlaceholder;

    public CardInfoPanel() {
        super(FXML);

        TipBox commandTipBox = new TipBox("Research more on a question by entering 'research INDEX'!");
        researchCommandTipPlaceholder.getChildren().add(commandTipBox.getRoot());

        cardExperience = new CardExperience();
        cardExperiencePlaceholder.getChildren().add(cardExperience.getRoot());
        cardMostRecentMistake = new CardMostRecentMistake();
        cardMostRecentMistakePlaceholder.getChildren().add(cardMostRecentMistake.getRoot());
    }

    /**
     * Will be called when a card in {@code CardListPanel} is selected.
     */
    public void loadCardPage(Card card, List<Attempt> attemptsByCard) {
        cleanUp();

        cardInfoQuestion.setText(card.getQuestion().value);
        cardInfoAnswer.setText(card.getAnswer().value);

        card.getTopics().forEach(topic -> cardInfoTopics.getChildren().add(new Label(topic.topicName)));

        cardExperience = new CardExperience(attemptsByCard);
        cardExperiencePlaceholder.getChildren().add(cardExperience.getRoot());

        cardMostRecentMistake = attemptsByCard.stream()
                .filter(attempt -> !attempt.isCorrect())
                .max(Comparator.comparing(Attempt::getTimestamp))
                .map(attempt -> new CardMostRecentMistake(card, attempt))
                .orElseGet(() -> new CardMostRecentMistake(card));
        cardMostRecentMistakePlaceholder.getChildren().add(cardMostRecentMistake.getRoot());
    }

    /**
     * Clear all the information that existed from the previous loaded card.
     */
    private void cleanUp() {
        cardInfoTopics.getChildren().clear();
        cardExperiencePlaceholder.getChildren().clear();
        cardMostRecentMistakePlaceholder.getChildren().clear();
    }
}
