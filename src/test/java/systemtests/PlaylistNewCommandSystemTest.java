//package systemtests;
//
//import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.NAME_DESC_ANIME;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.NAME_DESC_METAL;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_DESC_ALIEZ;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_DESC_EXISTENCE;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_NAME_METAL;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.AMY;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.BOB;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.HOON;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.IDA;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.KEYWORD_MATCHING_SONG;
//
//import org.junit.Test;
//
//import seedu.jxmusic.commons.core.Messages;
//import seedu.jxmusic.commons.core.index.Index;
//import seedu.jxmusic.logic.commands.AddCommand;
//import seedu.jxmusic.model.Model;
//import seedu.jxmusic.model.Name;
//import seedu.jxmusic.model.playlist.Person;
//import seedu.jxmusic.model.tag.Tag;
//import seedu.jxmusic.testutil.PlaylistBuilder;
//import seedu.jxmusic.testutil.PlaylistUtil;
//
//public class AddCommandSystemTest extends LibrarySystemTest {
//
//    @Test
//    public void add() {
//        Model model = getModel();
//
//        /* ------------------------ Perform add operations on the shown unfiltered list -----------------------------
// */
//
//        /* Case: add a playlist without tags to a non-empty jxmusic book, command with leading spaces and trailing
// spaces
//         * -> added
//         */
//        Person toAdd = AMY;
//        String command = "   " + AddCommand.COMMAND_PHRASE + "  " + NAME_DESC_ANIME + "  " + PHONE_DESC_AMY + " "
//                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + TRACK_DESC_ALIEZ + " ";
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: undo adding Amy to the list -> Amy deleted */
//        command = UndoCommand.COMMAND_PHRASE;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: redo adding Amy to the list -> Amy added again */
//        command = RedoCommand.COMMAND_PHRASE;
//        model.addPerson(toAdd);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: add a playlist with all fields same as another playlist in the jxmusic book except name -> added */
//        toAdd = new PlaylistBuilder(AMY).withName(VALID_NAME_METAL).build();
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_METAL + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
//                + TRACK_DESC_ALIEZ;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a playlist with all fields same as another playlist in the jxmusic book except phone and email
//         * -> added
//         */
//        toAdd = new PlaylistBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
//        command = PlaylistUtil.getPlaylistNewCommand(toAdd);
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add to empty jxmusic book -> added */
//        deleteAllPersons();
//        assertCommandSuccess(SFX);
//
//        /* Case: add a playlist with tags, command with parameters in random order -> added */
//        toAdd = BOB;
//        command = AddCommand.COMMAND_PHRASE + TRACK_DESC_ALIEZ + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_METAL
//                + TRACK_DESC_EXISTENCE + EMAIL_DESC_BOB;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a playlist, missing tags -> added */
//        assertCommandSuccess(HOON);
//
//        /* -------------------------- Perform add operation on the shown filtered list ------------------------------
// */
//
//        /* Case: filters the playlist list before adding -> added */
//        showPersonsWithName(KEYWORD_MATCHING_SONG);
//        assertCommandSuccess(IDA);
//
//        /* ------------------------ Perform add operation while a playlist card is selected -------------------------
// -- */
//
//        /* Case: selects first card in the playlist list, add a playlist -> added, card selection remains unchanged */
//        selectPerson(Index.fromOneBased(1));
//        assertCommandSuccess(SFX);
//
//        /* ----------------------------------- Perform invalid add operations ---------------------------------------
// */
//
//        /* Case: add a duplicate playlist -> rejected */
//        command = PlaylistUtil.getPlaylistNewCommand(HOON);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate playlist except with different phone -> rejected */
//        toAdd = new PlaylistBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
//        command = PlaylistUtil.getPlaylistNewCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate playlist except with different email -> rejected */
//        toAdd = new PlaylistBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
//        command = PlaylistUtil.getPlaylistNewCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate playlist except with different jxmusic -> rejected */
//        toAdd = new PlaylistBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
//        command = PlaylistUtil.getPlaylistNewCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate playlist except with different tags -> rejected */
//        command = PlaylistUtil.getPlaylistNewCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: missing name -> rejected */
//        command = AddCommand.COMMAND_PHRASE + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing phone -> rejected */
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_ANIME + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing email -> rejected */
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_ANIME + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing jxmusic -> rejected */
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_ANIME + PHONE_DESC_AMY + EMAIL_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: invalid keyword -> rejected */
//        command = "adds " + PlaylistUtil.getPersonDetails(toAdd);
//        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: invalid name -> rejected */
//        command = AddCommand.COMMAND_PHRASE + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
//
//        /* Case: invalid phone -> rejected */
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_ANIME + INVALID_PHONE_DESC + EMAIL_DESC_AMY +
// ADDRESS_DESC_AMY;
//        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);
//
//        /* Case: invalid email -> rejected */
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_ANIME + PHONE_DESC_AMY + INVALID_EMAIL_DESC +
// ADDRESS_DESC_AMY;
//        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);
//
//        /* Case: invalid jxmusic -> rejected */
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_ANIME + PHONE_DESC_AMY + EMAIL_DESC_AMY +
// INVALID_ADDRESS_DESC;
//        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);
//
//        /* Case: invalid tag -> rejected */
//        command = AddCommand.COMMAND_PHRASE + NAME_DESC_ANIME + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
//                + INVALID_TAG_DESC;
//        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
//    }
//
//    /**
//     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
//     * 1. Command box displays an empty string.<br>
//     * 2. Command box has the default style class.<br>
//     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
//     * {@code toAdd}.<br>
//     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
//     * the current model added with {@code toAdd}.<br>
//     * 5. Browser url and selected card remain unchanged.<br>
//     * 6. Status bar's sync status changes.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(Person toAdd) {
//        assertCommandSuccess(PlaylistUtil.getPlaylistNewCommand(toAdd), toAdd);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
//     * instead.
//     * @see AddCommandSystemTest#assertCommandSuccess(Person)
//     */
//    private void assertCommandSuccess(String command, Person toAdd) {
//        Model expectedModel = getModel();
//        expectedModel.addPerson(toAdd);
//        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
//
//        assertCommandSuccess(command, expectedModel, expectedResultMessage);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
//     * the,<br>
//     * 1. Result display box displays {@code expectedResultMessage}.<br>
//     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
//     * {@code expectedModel}.<br>
//     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays {@code command}.<br>
//     * 2. Command box has the error style class.<br>
//     * 3. Result display box displays {@code expectedResultMessage}.<br>
//     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
//     * 5. Browser url, selected card and status bar remain unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see LibrarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
