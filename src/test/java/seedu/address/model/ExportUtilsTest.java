package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;

/**
 *  Tests for the ExportUtils class.
 * @@author arsalanc-v2
 */
public class ExportUtilsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    // Load the CSV file containing expected content for the tests below.
    final File expected = new File("./src/test/java/seedu/address/model/ExpectedExportUtilsTest.csv");

    @Test
    public void writeLine_identicalRow_returnsTrue() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        ExportUtils.writeLine(new FileWriter(actual), "a, b, c");
        assertTrue(FileUtil.areIdenticalFiles(expected, actual));
    }

    @Test
    public void writeLine_emptyRow_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        ExportUtils.writeLine(new FileWriter(actual), "");
        assertFalse(FileUtil.areIdenticalFiles(expected, actual));
    }

    @Test
    public void writeLine_nullLine_throwsIllegalArgumentException() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        thrown.expect(IllegalArgumentException.class);
        ExportUtils.writeLine(new FileWriter(actual), null);
    }

    @Test
    public void writeLine_identicalLength_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        ExportUtils.writeLine(new FileWriter(actual), "x, x, x");
        assertFalse(FileUtil.areIdenticalFiles(expected, actual));
    }

    @Test
    public void writeLine_greaterLength_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        ExportUtils.writeLine(new FileWriter(actual), "a, b, c, d, e, f, g, h");
        assertFalse(FileUtil.areIdenticalFiles(expected, actual));
    }

    @Test
    public void writeLine_lessLength_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        ExportUtils.writeLine(new FileWriter(actual), "a");
        assertFalse(FileUtil.areIdenticalFiles(expected, actual));
    }

    @Test
    public void writeLines_identicalContent_returnsTrue() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        ArrayList<String> testRow = new ArrayList<String>(Arrays.asList("a, b, c"));
        ExportUtils.writeLines(new FileWriter(actual), testRow);
        assertTrue(FileUtil.areIdenticalFiles(expected, actual));
    }

    @Test
    public void writeLines_greaterRows_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        ArrayList<String> testRows = new ArrayList<String>(Arrays.asList("a, b, c", "d, e, f", "g, h, i", "j, k, l"));
        ExportUtils.writeLines(new FileWriter(actual), testRows);
        assertFalse(FileUtil.areIdenticalFiles(expected, actual));
    }

    @Test
    public void writeLines_nullRows_throwsNullPointerException() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        thrown.expect(NullPointerException.class);
        ExportUtils.writeLines(new FileWriter(actual), null);
    }

    @Test
    public void writeLines_emptyRows_throwsIllegalArgumentException() throws IOException {
        final File actual = folder.newFile("ActualExportUtilsTest.csv");
        thrown.expect(IllegalArgumentException.class);
        ExportUtils.writeLines(new FileWriter(actual), new ArrayList<>());
    }
}
