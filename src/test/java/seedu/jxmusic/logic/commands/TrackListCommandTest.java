package seedu.jxmusic.logic.commands;

import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jxmusic.logic.commands.CommandTestUtil.showTrackAtIndex;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_TRACK;
import static seedu.jxmusic.testutil.TypicalTrackList.getTypicalLibrary;

import org.junit.Before;
import org.junit.Test;

import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.UserPrefs;

public class TrackListCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
        expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new TrackListCommand(), model, commandHistory,
                TrackListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTrackAtIndex(model, INDEX_FIRST_TRACK);
        assertCommandSuccess(new TrackListCommand(), model, commandHistory,
                TrackListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
