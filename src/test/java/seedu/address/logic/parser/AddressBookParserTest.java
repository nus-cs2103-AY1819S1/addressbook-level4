package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_GIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.testutil.ImportFileUtil.getImportCommand;
import static seedu.address.testutil.MatchTestUtil.completeMatchTest;
import static seedu.address.testutil.MatchTestUtil.startMatchTest;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCardDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.LearnCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.MatchTestCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.card.QuestionContainsKeywordsPredicate;
import seedu.address.model.portation.ImportFile;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.CardUtil;
import seedu.address.testutil.EditCardDescriptorBuilder;
import seedu.address.testutil.ImportFileUtil;
import seedu.address.testutil.TypicalCards;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private AddressBookParser parser;

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), TypicalCards.getTypicalTriviaBundle(), new TriviaResults(),
                new UserPrefs());
        parser = new AddressBookParser();
    }

    @After
    public void cleanUp() {
        if (model.isInTestingState()) {
            model.stopTriviaTest();
        }
    }

    @Test
    public void parseCommand_add() throws Exception {
        Card card = new CardBuilder().build();
        AddCommand command = (AddCommand) parseCommand(CardUtil.getAddCommand(card));
        assertEquals(new AddCommand(card), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Card card = new CardBuilder().build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(card).build();
        EditCommand command = (EditCommand) parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CARD.getOneBased() + " " + CardUtil.getEditCardDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_CARD, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        // In LEARN state
        assertTrue(parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);

        // In Match Test state
        startMatchTest(model, new Topic(VALID_TOPIC_PHYSICS));
        assertTrue(parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new QuestionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parseCommand(LearnCommand.COMMAND_WORD) instanceof LearnCommand);
        assertTrue(parseCommand(LearnCommand.COMMAND_WORD + " Physics") instanceof LearnCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        ImportFile typicalFile = ImportFileUtil.getTypicalImportFile();
        ImportCommand command = (ImportCommand) parseCommand(getImportCommand(typicalFile.toString()));
        assertEquals(new ImportCommand(typicalFile), command);
    }

    @Test
    public void unableToParseMatchCommand_duringNormalState() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parseCommand("1 2");
    }

    @Test
    public void parseCommand_matchTest() throws Exception {
        assertTrue(parseCommand(MatchTestCommand.COMMAND_WORD + " " + VALID_TOPIC_PHYSICS)
                instanceof MatchTestCommand);
        assertTrue(parseCommand(MatchTestCommand.COMMAND_WORD + " " + VALID_TOPIC_GIT)
                instanceof MatchTestCommand);
    }

    @Test
    public void parseCommand_match() throws Exception {
        model.startTriviaTest(new MatchTest(new Topic(VALID_TOPIC_PHYSICS),
                model.getTriviaBundle()));
        assertTrue(parseCommand("1 2") instanceof MatchCommand);
        assertTrue(parseCommand("3 4")
                .equals(new MatchCommand(Index.fromOneBased(3), Index.fromOneBased(4))));
    }

    @Test
    public void parseCommand_restrictCommandsDuringTestResult() throws Exception {
        completeMatchTest(model, new Topic(VALID_TOPIC_PHYSICS));

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parseCommand("list");
    }

    @Test
    public void parseCommand_exitFromTestResult() throws Exception {
        completeMatchTest(model, new Topic(VALID_TOPIC_PHYSICS));

        assertTrue(parseCommand("exit") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parseCommand("unknownCommand");
    }

    private Command parseCommand(String input) throws ParseException {
        return parser.parseCommand(input, model.getAppState());
    }
}
