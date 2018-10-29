package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.TopicIsKeywordPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class LearnCommand extends Command {

    public static final String COMMAND_WORD = "learn";

    public static final String MESSAGE_SUCCESS = "Displayed %s cards";

    private Predicate<Card> learnCondition;
    private String topicKeyword;

    public LearnCommand() {
        learnCondition = PREDICATE_SHOW_ALL_CARDS;
        topicKeyword = "all";
    }

    public LearnCommand(TopicIsKeywordPredicate learnCondition) {
        this.learnCondition = learnCondition;
        topicKeyword = learnCondition.getTopicKeyword();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCardList(learnCondition);
        return new CommandResult(String.format(MESSAGE_SUCCESS, topicKeyword));
    }
}
