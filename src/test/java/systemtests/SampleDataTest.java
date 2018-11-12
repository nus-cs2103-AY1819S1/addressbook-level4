package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.OrderBook;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.order.Order;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends OrderBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected OrderBook getInitialOrdersData() {
        return null;
    }

    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected DeliverymenList getInitialDeliverymenData() {
        return null;
    }
    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void orderBook_dataFileDoesNotExist_loadSampleData() {
        /* Login */
        String loginCommand = LoginCommand.COMMAND_WORD + " ";
        String command = loginCommand + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;
        executeCommand(command);
        setUpOrderListPanel();

        Order[] expectedList = SampleDataUtil.getSampleOrders();
        assertListMatching(getOrderListPanel(), expectedList);
    }
}
