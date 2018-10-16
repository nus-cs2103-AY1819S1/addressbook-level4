package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * Provides a handle to a card view in the card list panel.
 */
public class CardViewHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String QUESTION_FIELD_ID = "#question";
    private static final String ANSWER_FIELD_ID = "#answer";
    private static final String TOPICS_FIELD_ID = "#topics";

    private final Label idLabel;
    private final Label questionLabel;
    private final Label answerLabel;
    private final List<Label> topicLabels;

    public CardViewHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        questionLabel = getChildNode(QUESTION_FIELD_ID);
        answerLabel = getChildNode(ANSWER_FIELD_ID);

        Region topicsContainer = getChildNode(TOPICS_FIELD_ID);
        topicLabels = topicsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    public String getAnswer() {
        return answerLabel.getText();
    }

    public List<String> getTopics() {
        return topicLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code card}.
     */
    public boolean equals(Card card) {
        return getQuestion().equals(card.getQuestion().value)
                && getAnswer().equals(card.getAnswer().value)
                && ImmutableMultiset.copyOf(getTopics()).equals(ImmutableMultiset.copyOf(card.getTopics().stream()
                .map(topic -> topic.topicName)
                .collect(Collectors.toList())));
    }
}
