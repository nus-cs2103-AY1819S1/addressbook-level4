package seedu.inventory.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.UnrecognizableDataException;
import seedu.inventory.storage.CsvAdaptedData;
import seedu.inventory.testutil.TestUtil;

public class CsvUtilTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.csv");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.csv");
    private static final Path MISSING_DATA_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingDataField.csv");
    private static final Path MISSING_DATA_TYPE_FILE = TEST_DATA_FOLDER.resolve("missingDataType.csv");
    private static final Path INVALID_DATA_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidDataField.csv");
    private static final Path INVALID_DATA_TYPE_FILE = TEST_DATA_FOLDER.resolve("invalidDataType.csv");
    private static final Path INVALID_CONTENT_FILE = TEST_DATA_FOLDER.resolve("invalidContent.csv");
    private static final Path VALID_TEST_FILE = TEST_DATA_FOLDER.resolve("validTest.csv");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempData.csv");
    private static final CsvAdaptedData DATA_TYPE_TO_TRANSFER = new CsvAdaptedDataStub().getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_nullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CsvUtil.getDataFromFile(null, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_nullDataType_nullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CsvUtil.getDataFromFile(VALID_TEST_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        CsvUtil.getDataFromFile(MISSING_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_emptyFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataFromFile(EMPTY_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_missingDataFieldFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataFromFile(MISSING_DATA_FIELD_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_missingDataTypeFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataFromFile(MISSING_DATA_TYPE_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_invalidDataFieldFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataFromFile(INVALID_DATA_FIELD_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_invalidDataTypeFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataFromFile(INVALID_DATA_TYPE_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_invalidContentFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataFromFile(INVALID_CONTENT_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        CsvAdaptedData data = CsvUtil.getDataFromFile(VALID_TEST_FILE, DATA_TYPE_TO_TRANSFER);
        assertEquals(5, data.getContents().size());
    }

    @Test
    public void isDataTypeEqual() {
        assertTrue(CsvUtil.isDataTypeEqual(Arrays.asList("Test"), DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataTypeEqual(Arrays.asList("Test", "AnotherTest"), DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataTypeEqual(Arrays.asList(), DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataTypeEqual(Arrays.asList("AnotherTest"), DATA_TYPE_TO_TRANSFER));
    }

    @Test
    public void isDataFieldsEqual() {
        assertTrue(CsvUtil
                .isDataFieldsEqual(Arrays.asList("firstField", "secondField", "thirdField"), DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil
                .isDataFieldsEqual(Arrays.asList("firstField", "thirdField", "secondField"), DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataFieldsEqual(Arrays.asList("firstField", "secondField"), DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil
                .isDataFieldsEqual(Arrays.asList("firstField", "thirdField", "secondField", "fourthField"),
                        DATA_TYPE_TO_TRANSFER));
    }

    @Test
    public void isDataHeaderRecognizable() {
        assertTrue(CsvUtil.isDataHeaderRecognizable(VALID_TEST_FILE, DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataHeaderRecognizable(EMPTY_FILE, DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataHeaderRecognizable(MISSING_FILE, DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataHeaderRecognizable(MISSING_DATA_TYPE_FILE, DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataHeaderRecognizable(MISSING_DATA_FIELD_FILE, DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataHeaderRecognizable(INVALID_DATA_TYPE_FILE, DATA_TYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataHeaderRecognizable(INVALID_DATA_FIELD_FILE, DATA_TYPE_TO_TRANSFER));
        assertTrue(CsvUtil.isDataHeaderRecognizable(INVALID_CONTENT_FILE, DATA_TYPE_TO_TRANSFER));
    }

    @Test
    public void getDataContentFromFile_emptyFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataContentFromFile(EMPTY_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataContentFromFile_missingFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataContentFromFile(MISSING_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataContentFromFile_missingDataTypeFile_validResult() throws Exception {
        assertEquals(CsvUtil.getDataContentFromFile(MISSING_DATA_TYPE_FILE, DATA_TYPE_TO_TRANSFER).size(), 5);
    }

    @Test
    public void getDataContentFromFile_missingDataFieldFile_validResult() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataContentFromFile(MISSING_DATA_FIELD_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getDataContentFromFile_invalidDataTypeFile_validResult() throws Exception {
        assertEquals(CsvUtil.getDataContentFromFile(INVALID_DATA_TYPE_FILE, DATA_TYPE_TO_TRANSFER).size(), 5);
    }

    @Test
    public void getDataContentFromFile_invalidDataFieldFile_validResult() throws Exception {
        assertEquals(CsvUtil.getDataContentFromFile(INVALID_DATA_FIELD_FILE, DATA_TYPE_TO_TRANSFER).size(), 5);
    }

    @Test
    public void getDataContentFromFile_invalidContentFile_unrecognizableDataException() throws Exception {
        thrown.expect(UnrecognizableDataException.class);
        CsvUtil.getDataContentFromFile(INVALID_CONTENT_FILE, DATA_TYPE_TO_TRANSFER);
    }

    @Test
    public void getContentFromLine() {
        assertEquals(Arrays.asList("one", "two", "three"), CsvUtil.getContentFromLine("one,two,three"));
        assertEquals(Arrays.asList("one, one", "two", "three"),
                CsvUtil.getContentFromLine("\"one, one\",two,three"));
        assertEquals(Arrays.asList("one", "two, two", "three"),
                CsvUtil.getContentFromLine("one,\"two, two\",three"));
        assertEquals(Arrays.asList("one", "two", "three, three"),
                CsvUtil.getContentFromLine("one,two,\"three, three\""));
        assertEquals(Arrays.asList("one, one", "two, two", "three, three"),
                CsvUtil.getContentFromLine("\"one, one\",\"two, two\",\"three, three\""));
        assertEquals(Arrays.asList("one, one", "", "three"),
                CsvUtil.getContentFromLine("\"one, one\",,three"));
        assertEquals(Arrays.asList("one, one", "two", ""),
                CsvUtil.getContentFromLine("\"one, one\",two,"));
        assertEquals(Arrays.asList("one", "two, two", ""),
                CsvUtil.getContentFromLine("one,\"two, two\","));
        assertEquals(Arrays.asList("", "", ""),
                CsvUtil.getContentFromLine(",,"));

    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        CsvAdaptedData dataToWrite = new CsvAdaptedDataStub();
        CsvUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        CsvAdaptedData dataFromFile = CsvUtil.getDataFromFile(TEMP_FILE, DATA_TYPE_TO_TRANSFER);
        assertEquals(dataToWrite, dataFromFile);

        FileUtil.createFile(TEMP_FILE);
        dataToWrite = CsvUtil.getDataFromFile(VALID_TEST_FILE, DATA_TYPE_TO_TRANSFER);
        CsvUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = CsvUtil.getDataFromFile(TEMP_FILE, DATA_TYPE_TO_TRANSFER);
        assertEquals(dataToWrite, dataFromFile);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CsvUtil.saveDataToFile(null, new CsvAdaptedDataStub());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CsvUtil.saveDataToFile(VALID_TEST_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        CsvUtil.saveDataToFile(MISSING_FILE, new CsvAdaptedDataStub());
    }

}

class CsvAdaptedDataStub implements CsvAdaptedData {
    public static final String DATA_TYPE = "Test";
    public static final String[] FIELDS = {"firstField", "secondField", "thirdField"};

    private List<List<String>> contents;

    public CsvAdaptedDataStub(List<List<String>> contents) {
        this.contents = contents;
    }

    public CsvAdaptedDataStub() {
        this.contents = new LinkedList<>();
    }

    @Override
    public List<List<String>> getContents() {
        return contents;
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public String[] getDataFields() {
        return FIELDS;
    }

    @Override
    public CsvAdaptedData getInstance() {
        return new CsvAdaptedDataStub();
    }

    @Override
    public CsvAdaptedData createInstance(List<List<String>> contents) {
        return new CsvAdaptedDataStub(contents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CsvAdaptedDataStub)) {
            return false;
        }
        CsvAdaptedDataStub that = (CsvAdaptedDataStub) o;
        return contents.equals(that.contents);
    }
}
