package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 *  Answers the current trivia question and continues to the next question
 */
public class TriviaAnsCommand extends Command {
    public static final String COMMAND_WORD = "answer";

    public static final String COMMAND_EXIT = "triviaExit";

    public static final String COMMAND_SHOW = "triviaShow";

    public static final String MESSAGE_SUCCESS = "Correct!";

    public static final String MESSAGE_WRONG = "Wrong!";

    public static final String MESSAGE_FAILURE = "Please use the trivia command to get a question.";

    public static final String MESSAGE_NEXT = "Next question: ";

    public static final String MESSAGE_EXIT = "Type triviaExit to end trivia.";

    public static final String MESSAGE_END = "Trivia ended! Type trivia to play again!";

    public static final String MESSAGE_USAGE = "Please input your answer. "
            + "Type triviaShow to show your current question.";

    private String question;

    private final String answer;

    private boolean correct;

    public TriviaAnsCommand(String answer) {
        this.answer = answer;
    }

    private String getQuestion() {
        return question;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Word triviaQ = model.getTrivia();
        question = triviaQ.getMeaning().fullMeaning;
        ArrayList<Word> triviaList = model.getTriviaList();
        String messageOutput = "";

        if (answer.equals(COMMAND_EXIT)) {
            model.toggleTriviaMode();
            return new CommandResult(MESSAGE_END);
        }

        if (answer.equals(COMMAND_SHOW)) {
            return new CommandResult("Question: " + this.getQuestion());
        }

        if (triviaQ == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        correct = triviaQ.getName().toString().toLowerCase().equals(answer.toLowerCase());
        model.clearTrivia();

        if (correct) {
            messageOutput = MESSAGE_SUCCESS;
            model.updateScore();
        }

        if (!correct) {
            messageOutput = MESSAGE_WRONG;
        }

        messageOutput += " Score: " + Integer.toString(model.currentScore()) + "/" + Integer.toString(model.maxScore())
                + " \n";

        if (triviaList.size() < 1) {
            model.toggleTriviaMode();
            messageOutput += MESSAGE_END;
        } else {
            triviaQ = model.getTrivia();
            messageOutput += MESSAGE_NEXT + triviaQ.getMeaning().toString() + "\n" + MESSAGE_EXIT;
        }

        return new CommandResult(messageOutput);
    }
}
