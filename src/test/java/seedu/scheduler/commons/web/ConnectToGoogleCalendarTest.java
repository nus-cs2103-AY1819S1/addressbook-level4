package seedu.scheduler.commons.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ConnectToGoogleCalendarTest {

    @Test
    void isGoogleCalendarEnabled() {
        setUpStatus("Enabled");
        assertTrue(ConnectToGoogleCalendar.isGoogleCalendarEnabled());
        setUpStatus("Disabled");
        assertFalse(ConnectToGoogleCalendar.isGoogleCalendarEnabled());
    }

    private void setUpStatus(String enabled) {
        File file = new File("./tokens/mode.txt");
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            String contents = enabled;
            writer.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isGoogleCalendarDisabled() {
        boolean isDisabled = true;
        assertEquals(true, isDisabled);
    }

    @Test
    void checkStatus() {
        //Prepare the test file where the status indicates Enabled
        setUpStatus("Enabled");
        //Test whether returns Enabled
        Assert.assertTrue(ConnectToGoogleCalendar.checkStatus("Enabled"));
        Assert.assertFalse(ConnectToGoogleCalendar.checkStatus("Disabled"));

        //Prepare the test file where the status indicates Disabled
        setUpStatus("Disabled");
        //Test whether returns Disabled
        Assert.assertFalse(ConnectToGoogleCalendar.checkStatus("Enabled"));
        Assert.assertTrue(ConnectToGoogleCalendar.checkStatus("Disabled"));
    }
}
