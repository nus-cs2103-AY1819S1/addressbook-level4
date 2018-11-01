//@@author chantca95
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private static final String EXPORTED_FILE_NAME = "exportedCommandTest.csv";
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvTest");
    private static final Path EXPECTED_FILE_NAME = TEST_DATA_FOLDER.resolve("expectedExport.csv");

    private Model model;
    private CommandHistory commandHistory;
    private ExportCommand exportCommand = new ExportCommand(EXPORTED_FILE_NAME);

    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    //@Test
    public void execute_export_success() throws CommandException, IOException {
        setUp();
        CommandResult result = exportCommand.execute(model, commandHistory);
        File produced = new File(EXPORTED_FILE_NAME);
        File expected = EXPECTED_FILE_NAME.toFile();
        boolean isTwoEqual = compareFiles(produced, expected);
        assertTrue(isTwoEqual);
    }

    /**
     * Helper method to check if the exported file is the same as the expected one
     */
    public static boolean compareFiles(File produced, File expected) throws IOException {
        boolean isIdentical = true;
        FileInputStream fis1 = new FileInputStream (produced);
        FileInputStream fis2 = new FileInputStream (expected);
        if (produced.length() == expected.length()) {
            int n = 0;
            byte[] b1;
            byte[] b2;
            while ((n = fis1.available()) > 0) {
                if (n > 80) {
                    n = 80;
                }
                b1 = new byte[n];
                b2 = new byte[n];
                fis1.read(b1);
                fis2.read(b2);
                if (Arrays.equals(b1, b2) == false) {
                    System.out.println(produced + " :\n\n " + new String(b1));
                    System.out.println();
                    System.out.println(expected + " : \n\n" + new String(b2));
                    isIdentical = false;
                }
            }
        } else {
            //if length does not match.
            isIdentical = false;
        }
        fis1.close();
        fis2.close();
        // delete the generated file so that the test can be rerun
        produced.delete();
        return isIdentical;
    }
}
