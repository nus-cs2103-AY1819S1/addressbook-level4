//package seedu.address.logic.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.ModelGenerator.getModelWithTestImgDirectory;
//
//import org.junit.Test;
//
//import seedu.address.logic.CommandHistory;
//import seedu.address.model.Model;
//
//public class LoginCommandTest {
//    private Model model = getModelWithTestImgDirectory();
//    private Model expectedModel = getModelWithTestImgDirectory();
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Test
//    public void execute_command_success() {
//        LoginCommand loginCommand = new LoginCommand();
//        String expected = "we";
//        assertCommandSuccess(loginCommand, model, commandHistory, expected, expectedModel);
//    }
//
//}
