package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.google.GoogleDlCommand;
import seedu.address.logic.commands.google.GoogleLsCommand;
import seedu.address.logic.commands.google.GoogleUploadCommand;
import seedu.address.model.Model;

//@@author chivent

/**
 * Suite of tests to test failure messages for GoogleCommands.
 * Unable to test success as it requires a Google Account and a connectivity.
 */
public class GoogleFailCommandTests extends DefaultCommandTest {
    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void testGoogleLsFailure() {
        GoogleLsCommand command;
        String msg = GoogleLsCommand.FAILURE_MESSAGE + "\n\n" + GoogleLsCommand.MESSAGE_USAGE;

        //test empty parameter
        command = new GoogleLsCommand("");
        assertCommandFailure(command, model, commandHistory, msg);
        //test /a albums
        command = new GoogleLsCommand("/a");
        assertCommandFailure(command, model, commandHistory, msg);

        //test list from album
        command = new GoogleLsCommand("<albumName>");
        assertCommandFailure(command, model, commandHistory, msg);

        //test invalid type
        command = new GoogleLsCommand("invalid");
        assertCommandFailure(command, model, commandHistory, msg);
    }

    @Test
    public void testGoogleUploadFailure() {
        GoogleUploadCommand command;
        String msg = GoogleUploadCommand.MESSAGE_FAILURE + "\n\n" + GoogleUploadCommand.MESSAGE_USAGE;

        //test empty parameter
        command = new GoogleUploadCommand("");
        assertCommandFailure(command, model, commandHistory, String.format(msg, ""));

        //test all from directory
        command = new GoogleUploadCommand("all");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "all"));

        //test image
        command = new GoogleUploadCommand("<imageName>");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "imageName"));

        //test invalid
        command = new GoogleUploadCommand("invalid");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "nvali"));
    }

    @Test
    public void testGoogleDlFailure() {
        GoogleDlCommand command;
        String msg = GoogleDlCommand.MESSAGE_FAILURE + "\n\n" + GoogleDlCommand.MESSAGE_USAGE;

        //test invalid
        command = new GoogleDlCommand("<");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "<"));

        //test from invalid image, invalid album
        command = new GoogleDlCommand("/a<Al /i<Im");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "I"));

        //test from valid image, invalid album
        command = new GoogleDlCommand("/a<Albu /i<Image>");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "Image"));

        //test from invalid image, valid album
        command = new GoogleDlCommand("/a<Album> /i<I");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "/a<Album> /i<I"));

        //test from image of album
        command = new GoogleDlCommand("/a<Album> /i<Image>");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "Image"));

        //test image
        command = new GoogleDlCommand("/i<imageName>");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "imageName"));

        //test invalid image
        command = new GoogleDlCommand("/i<Invalid");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "Invali"));

        //test album
        command = new GoogleDlCommand("/a<Album>");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "Album"));

        //test invalid album
        command = new GoogleDlCommand("/a<Invalid");
        assertCommandFailure(command, model, commandHistory, String.format(msg, "Invali"));
    }

}
