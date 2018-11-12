package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCards.Q_CAPITAL_SG;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_GIT_COMMIT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TriviaBundleBuilder;

public class VersionedTriviaBundleTest {
    private final ReadOnlyTriviaBundle triviaBundleWithEarthRoundQ = new TriviaBundleBuilder()
            .withCard(Q_EARTH_ROUND).build();
    private final ReadOnlyTriviaBundle triviaBundleWithGitCommitQ = new TriviaBundleBuilder()
            .withCard(Q_GIT_COMMIT).build();
    private final ReadOnlyTriviaBundle triviaBundleWithCapitalSgQ = new TriviaBundleBuilder()
            .withCard(Q_CAPITAL_SG).build();
    private final ReadOnlyTriviaBundle emptyTriviaBundle = new TriviaBundleBuilder().build();

    @Test
    public void commit_singleTriviaBundle_noStatesRemovedCurrentStateSaved() {
        VersionedTriviaBundle versionedTriviaBundle = prepareAddressBookList(emptyTriviaBundle);

        versionedTriviaBundle.commit();
        assertAddressBookListStatus(versionedTriviaBundle,
                Collections.singletonList(emptyTriviaBundle),
                emptyTriviaBundle,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTriviaBundlePointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTriviaBundle versionedTriviaBundle = prepareAddressBookList(
                emptyTriviaBundle, triviaBundleWithEarthRoundQ, triviaBundleWithGitCommitQ);

        versionedTriviaBundle.commit();
        assertAddressBookListStatus(versionedTriviaBundle,
                Arrays.asList(emptyTriviaBundle, triviaBundleWithEarthRoundQ, triviaBundleWithGitCommitQ),
                triviaBundleWithGitCommitQ,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTriviaBundlePointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTriviaBundle versionedTriviaBundle = prepareAddressBookList(
                emptyTriviaBundle, triviaBundleWithEarthRoundQ, triviaBundleWithGitCommitQ);
        shiftCurrentStatePointerLeftwards(versionedTriviaBundle, 2);

        versionedTriviaBundle.commit();
        assertAddressBookListStatus(versionedTriviaBundle,
                Collections.singletonList(emptyTriviaBundle),
                emptyTriviaBundle,
                Collections.emptyList());
    }

    /**
     * Asserts that {@code versionedTriviaBundle} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTriviaBundle#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTriviaBundle#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedTriviaBundle versionedTriviaBundle,
                                             List<ReadOnlyTriviaBundle> expectedStatesBeforePointer,
                                             ReadOnlyTriviaBundle expectedCurrentState,
                                             List<ReadOnlyTriviaBundle> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TriviaBundle(versionedTriviaBundle), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTriviaBundle.canUndo()) {
            versionedTriviaBundle.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTriviaBundle expectedTriviaBundle : expectedStatesBeforePointer) {
            assertEquals(expectedTriviaBundle, new TriviaBundle(versionedTriviaBundle));
            versionedTriviaBundle.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTriviaBundle expectedTriviaBundle: expectedStatesAfterPointer) {
            versionedTriviaBundle.redo();
            assertEquals(expectedTriviaBundle, new TriviaBundle(versionedTriviaBundle));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTriviaBundle.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTriviaBundle.undo());
    }

    /**
     * Creates and returns a {@code VersionedTriviaBundle} with the {@code triviaBundleStates} added into it, and the
     * {@code VersionedTriviaBundle#currentStatePointer} at the end of list.
     */
    private VersionedTriviaBundle prepareAddressBookList(ReadOnlyTriviaBundle... triviaBundleStates) {
        assertFalse(triviaBundleStates.length == 0);

        VersionedTriviaBundle versionedTriviaBundle = new VersionedTriviaBundle(triviaBundleStates[0]);
        for (int i = 1; i < triviaBundleStates.length; i++) {
            versionedTriviaBundle.resetData(triviaBundleStates[i]);
            versionedTriviaBundle.commit();
        }

        return versionedTriviaBundle;
    }

    /**
     * Shifts the {@code versionedTriviaBundle#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTriviaBundle versionedTriviaBundle, int count) {
        for (int i = 0; i < count; i++) {
            versionedTriviaBundle.undo();
        }
    }
}
