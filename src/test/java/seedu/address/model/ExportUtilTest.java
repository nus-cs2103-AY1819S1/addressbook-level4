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
import seedu.address.model.util.ExportUtil;

/**
 *  Tests for the ExportUtil class.
 * @@author arsalanc-v2
 */
public class ExportUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    // Load the CSV file containing expected content for the tests below.
    final File expected = new File("./src/test/java/seedu/address/model/ExpectedExportUtilTest.csv");

    @Test
    public void writeLine_identicalRow_returnsTrue() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        ExportUtil.writeLine(new FileWriter(actual), "a, b, c");
        assertTrue(FileUtil.areFilesIdentical(expected, actual));
    }

    @Test
    public void writeLine_emptyRow_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        ExportUtil.writeLine(new FileWriter(actual), "");
        assertFalse(FileUtil.areFilesIdentical(expected, actual));
    }

    @Test
    public void writeLine_nullRow_throwsIllegalArgumentException() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        thrown.expect(IllegalArgumentException.class);
        ExportUtil.writeLine(new FileWriter(actual), null);
    }

    @Test
    public void writeLine_identicalLength_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        ExportUtil.writeLine(new FileWriter(actual), "x, x, x");
        assertFalse(FileUtil.areFilesIdentical(expected, actual));
    }

    @Test
    public void writeLine_greaterLength_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        ExportUtil.writeLine(new FileWriter(actual), "a, b, c, d, e, f, g, h");
        assertFalse(FileUtil.areFilesIdentical(expected, actual));
    }

    @Test
    public void writeLine_lessLength_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        ExportUtil.writeLine(new FileWriter(actual), "a");
        assertFalse(FileUtil.areFilesIdentical(expected, actual));
    }

    @Test
    public void writeLines_identicalContent_returnsTrue() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        ArrayList<String> testRow = new ArrayList<String>(Arrays.asList("a, b, c"));
        ExportUtil.writeLines(new FileWriter(actual), testRow);
        assertTrue(FileUtil.areFilesIdentical(expected, actual));
    }

    @Test
    public void writeLines_greaterRows_returnsFalse() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        ArrayList<String> testRows = new ArrayList<String>(Arrays.asList("a, b, c", "d, e, f", "g, h, i", "j, k, l"));
        ExportUtil.writeLines(new FileWriter(actual), testRows);
        assertFalse(FileUtil.areFilesIdentical(expected, actual));
    }

    @Test
    public void writeLines_nullRows_throwsNullPointerException() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        thrown.expect(NullPointerException.class);
        ExportUtil.writeLines(new FileWriter(actual), null);
    }

    @Test
    public void writeLines_emptyRows_throwsIllegalArgumentException() throws IOException {
        final File actual = folder.newFile("ActualExportUtilTest.csv");
        thrown.expect(IllegalArgumentException.class);
        ExportUtil.writeLines(new FileWriter(actual), new ArrayList<>());
    }
}
