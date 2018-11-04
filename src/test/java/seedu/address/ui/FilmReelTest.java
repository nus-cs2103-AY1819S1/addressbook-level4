package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalImages.FULL_LIST;
import static seedu.address.testutil.TypicalImages.IMAGE_LIST;
import static seedu.address.testutil.TypicalImages.SECOND_IMAGE_LIST;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.FilmCardHandle;
import guitests.guihandles.FilmReelHandle;
import javafx.collections.FXCollections;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FilmReelSelectionChangeEvent;
import seedu.address.commons.events.ui.UpdateFilmReelEvent;

//@@author chivent
public class FilmReelTest extends GuiUnitTest {

    private static final int FIRST_SELECTION = 0;
    private static final int SECOND_SELECTION = 1;
    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private FilmReelHandle filmReelHandle;
    private List<Path> paths = new ArrayList<>();
    private List<Path> secondPaths = new ArrayList<>();
    private List<Path> fullPaths = new ArrayList<>();

    private final Logger logger = LogsCenter.getLogger(getClass());

    @Before
    public void setUpResources() {
        paths = setUpPaths(IMAGE_LIST);
        secondPaths = setUpPaths(SECOND_IMAGE_LIST);
        fullPaths = setUpPaths(FULL_LIST);
    }

    @Test
    public void display() {
        initUi();
        assertCardListDisplay(paths);
    }

    @Test
    public void handleUpdateFilmReelEvent() {
        initUi();

        if (!paths.isEmpty() && !secondPaths.isEmpty()) {
            postNow(new UpdateFilmReelEvent(secondPaths));
            assertCardListDisplay(secondPaths);

            postNow(new UpdateFilmReelEvent(paths));
            assertCardListDisplay(paths);
        }
    }

    @Test
    public void handleFilmReelSelectionChangeEvent() {
        initUi();

        if (!paths.isEmpty() && !secondPaths.isEmpty()) {
            //Check none selected by default
            assertFalse(filmReelHandle.getHandleToSelectedCard().isPresent());

            //Check index 1 is selected
            postNow(new FilmReelSelectionChangeEvent(SECOND_SELECTION));
            assertEqualCardHandles(filmReelHandle.getFilmCardHandle(SECOND_SELECTION),
                    filmReelHandle.getHandleToSelectedCard());

            //Check none selected after refresh
            postNow(new UpdateFilmReelEvent(secondPaths));
            assertFalse(filmReelHandle.getHandleToSelectedCard().isPresent());

            //Check index 0 is selected
            postNow(new FilmReelSelectionChangeEvent(FIRST_SELECTION));
            assertEqualCardHandles(filmReelHandle.getFilmCardHandle(FIRST_SELECTION),
                    filmReelHandle.getHandleToSelectedCard());
        }
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code FilmReel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        if (!fullPaths.isEmpty()) {
            List<Path> longList = FXCollections.observableArrayList();
            longList.addAll(fullPaths);
            longList.addAll(fullPaths);
            longList.addAll(fullPaths);
            assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
                postNow(new UpdateFilmReelEvent(longList));
                guiRobot.interact(longList::clear);
            }, "Creation and deletion of image cards exceeded time limit");
        }
    }

    /**
     * Check that card handles are equal to each other
     * @param expected expected {@code FilmCardHandle} Optional
     * @param actual actual {@code FilmCardHandle} Optional
     */
    private void assertEqualCardHandles(Optional<FilmCardHandle> expected, Optional<FilmCardHandle> actual) {
        assertTrue(expected.isPresent());
        assertTrue(actual.isPresent());
        assertEquals(expected.get().toString(), actual.get().toString());
    }

    /**
     * Check that card displays are equal to as expected path list
     * @param pathList array to check against
     */
    private void assertCardListDisplay(List<Path> pathList) {
        for (int i = 0; i < pathList.size(); i++) {
            FilmCardHandle filmCard = filmReelHandle.getFilmCardHandle(i).get();
            assertNotNull(filmCard);
            assertEquals(Integer.toString(i + 1), filmCard.getTitle());
            assertEquals(pathList.get(i).toString(), filmCard.getPath());
        }
    }

    private List<Path> setUpPaths(String[] imagePaths) {
        List<Path> temp = new ArrayList<>();
        for (String s: IMAGE_LIST) {
            try {
                Path path = Paths.get("src", "test", "resources", "testimgs", s);
                assertNotNull(s + "exists.", path);
                temp.add(path);
            } catch (InvalidPathException ex) {
                logger.info(String.format("%s could not be found, skipping", s));
            }
        }

        return temp;
    }
    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi() {
        FilmReel filmReel = new FilmReel();
        uiPartRule.setUiPart(filmReel);
        filmReelHandle = new FilmReelHandle(filmReel.getRoot());
        postNow(new UpdateFilmReelEvent(paths));
    }
}
