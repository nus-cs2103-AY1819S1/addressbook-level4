package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.BudgetBook;
import seedu.address.testutil.TypicalCcas;

//@@author ericyjw
public class XmlSerializableBudgetBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableBudgetBookTest");
    private static final Path TYPICAL_CCAS_FILE = TEST_DATA_FOLDER.resolve("typicalCcaBudgetBook.xml");
    private static final Path INVALID_CCA_FILE = TEST_DATA_FOLDER.resolve("invalidCcaBudgetBook.xml");
    private static final Path DUPLICATE_CCA_FILE = TEST_DATA_FOLDER.resolve("duplicateCcaBudgetBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalCcasFile_success() throws Exception {
        XmlSerializableBudgetBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_CCAS_FILE,
            XmlSerializableBudgetBook.class);
        BudgetBook budgetBookFromFile = dataFromFile.toModelType();
        BudgetBook typicalCcasBudgetBook = TypicalCcas.getTypicalBudgetBook();
        assertEquals(typicalCcasBudgetBook, budgetBookFromFile);
    }

    @Test
    public void toModelType_invalidCcaFile_throwsIllegalValueException() throws Exception {
        XmlSerializableBudgetBook dataFromFile = XmlUtil.getDataFromFile(INVALID_CCA_FILE,
            XmlSerializableBudgetBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateCcas_throwsIllegalValueException() throws Exception {
        XmlSerializableBudgetBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_CCA_FILE,
            XmlSerializableBudgetBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableBudgetBook.MESSAGE_DUPLICATE_CCA);
        dataFromFile.toModelType();
    }

}
