package systemtests;

import static seedu.scheduler.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.scheduler.logic.RepeatEventGenerator;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.util.SampleSchedulerDataUtil;
import seedu.scheduler.testutil.TestUtil;

public class SampleDataTest extends SchedulerSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected Scheduler getInitialData() {
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
    public void scheduler_dataFileDoesNotExist_loadSampleData() {
        List<Event> expectedList = new ArrayList<>();
        for (Event sampleEvent : SampleSchedulerDataUtil.getSampleEvents()) {
            expectedList.addAll(RepeatEventGenerator.getInstance().generateAllRepeatedEvents(sampleEvent));
        }
        assertListMatching(getEventListPanel(), expectedList);
    }
}
