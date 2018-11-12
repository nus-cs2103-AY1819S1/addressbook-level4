package seedu.address.ui.test.openendedtest;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.OpenEndedTestShowAnswerEvent;
import seedu.address.commons.events.ui.OpenEndedTestShowNextQuestionEvent;
import seedu.address.model.card.Card;
import seedu.address.model.test.openendedtest.OpenEndedTest;
import seedu.address.ui.test.TriviaTestPage;

/**
 * The page that shows up when the user is in a open ended test
 */
public class OpenEndedTestPage extends TriviaTestPage {
    private static final String FXML = "test/openendedtest/OpenEndedTestPage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private OpenEndedTest openEndedTest;
    private Card card;
    private String question;
    private String answer;
    private String userAnswer;
    private final String totalNumOfQns;

    @FXML
    private Label questionLabel;

    @FXML
    private Label userAnswerLabel;

    @FXML
    private Label answerLabel;

    @FXML
    private Label topic;

    @FXML
    private Label duration;

    @FXML
    private Label qnsNo;

    @FXML
    private Label totalNumQns;

    public OpenEndedTestPage(OpenEndedTest openEndedTest) {
        super(FXML);
        this.openEndedTest = openEndedTest;
        this.card = openEndedTest.getCurrCard();
        this.question = card.getQuestion().value;
        this.answer = "";
        this.userAnswer = "";
        this.totalNumOfQns = " of " + (openEndedTest.getShuffledCards().size() + 1) + " questions";
        questionLabel.setText(question);
        userAnswerLabel.setText(userAnswer);
        answerLabel.setText(answer);
        totalNumQns.setText(totalNumOfQns);
        qnsNo.setText((openEndedTest.getAttempts().size() + 1) + "");

        topic.setText(openEndedTest.getTopic().topicName);
        duration.textProperty().bind(openEndedTest.getDuration().asString().concat(" s"));

        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleOpenEndedTestShowNextQuestion(OpenEndedTestShowNextQuestionEvent event) {
        this.card = event.getCard();
        this.question = this.card.getQuestion().value;
        this.answer = "";
        this.userAnswer = "";
        questionLabel.setText(question);
        userAnswerLabel.setText(userAnswer);
        answerLabel.setText(answer);
        qnsNo.setText((openEndedTest.getAttempts().size() + 1) + "");
    }

    @Subscribe
    private void handleOpenEndedTestShowAnswer(OpenEndedTestShowAnswerEvent event) {
        this.userAnswer = event.getUserAnswer();
        this.answer = this.card.getAnswer().toString();
        userAnswerLabel.setText(this.userAnswer);
        answerLabel.setText(this.answer);
    }
}
