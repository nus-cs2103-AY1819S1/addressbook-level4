package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A handler for the {@code CardInfoPanel} of the UI.
 */
public class CardInfoPanelHandle extends NodeHandle<Node> {

    public static final String QUESTION_ID = "#cardInfoQuestion";
    public static final String TOPIC_FIELD_ID = "#cardInfoTopics";
    public static final String CARD_INFO_ANSWER_ID = "#cardInfoAnswer";
    public static final String CARD_EXPERIENCE_ID = "#cardExperiencePlaceholder";
    public static final String CARD_MOST_RECENT_MISTAKE_ID = "#cardMostRecentMistakePlaceholder";

    private Node cardInfoNode;

    private Label question;
    private TextArea answer;
    private CardExperienceHandle cardExperienceHandle;
    private CardMostRecentMistakeHandle cardMostRecentMistakeHandle;
    private final List<Label> topicLabels;


    public CardInfoPanelHandle(Node cardInfoNode) {
        super(cardInfoNode);
        this.cardInfoNode = cardInfoNode;
        question = getChildNode(QUESTION_ID);
        answer = getChildNode(CARD_INFO_ANSWER_ID);
        cardExperienceHandle = new CardExperienceHandle(getChildNode(CARD_EXPERIENCE_ID));
        cardMostRecentMistakeHandle = new CardMostRecentMistakeHandle(getChildNode(CARD_MOST_RECENT_MISTAKE_ID));

        Region tagsContainer = getChildNode(TOPIC_FIELD_ID);
        topicLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getQuestion() {
        return question.getText();
    }

    public boolean isVisible() {
        return cardInfoNode.isVisible();
    }

    public String getCardInfoQuestion() {
        return question.getText();
    }

    public String getCardInfoAnswer() {
        return answer.getText();
    }

    public CardExperienceHandle getCardExperienceHandle() {
        return cardExperienceHandle;
    }

    public CardMostRecentMistakeHandle getCardMostRecentMistakeHandle() {
        return cardMostRecentMistakeHandle;
    }

    /**
     * obtain the cards associated to the topic.
     */
    public List<String> getCardInfoTopics() {
        return topicLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
