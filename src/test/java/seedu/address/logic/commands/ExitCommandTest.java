package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_FROM_APP;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_FROM_TEST;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.TypicalCards;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ExitCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), TypicalCards.getTypicalTriviaBundle(),
                new TriviaResults(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @After
    public void cleanUp() {
        if (model.isInTestingState()) {
            model.stopTriviaTest();
        }
    }

    @Test
    public void execute_exit_success() {
        // during NORMAL AppState
        CommandResult result = new ExitCommand().execute(model, commandHistory);
        assertEquals(MESSAGE_EXIT_FROM_APP, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);

        // during TEST/MATCH_TEST AppState
        MatchTest matchTest = new MatchTest(new Topic(VALID_TOPIC_PHYSICS), model.getTriviaBundle());
        model.startTriviaTest(matchTest);

        assertTrue(model.isInTestingState());
        result = new ExitCommand().execute(model, commandHistory);
        assertEquals(MESSAGE_EXIT_FROM_TEST, result.feedbackToUser);
    }
}
