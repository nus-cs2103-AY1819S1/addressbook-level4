package seedu.clinicio.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.commons.util.XmlUtil;

import seedu.clinicio.model.ClinicIo;

import seedu.clinicio.testutil.TypicalPersons;

public class XmlSerializableClinicIoTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableClinicIoTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsClinicIo.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonClinicIo.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonClinicIo.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableClinicIo dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableClinicIo.class);
        ClinicIo clinicIoFromFile = dataFromFile.toModelType(); // actual
        ClinicIo typicalPersonsClinicIo = TypicalPersons.getTypicalClinicIo(); // test util
        assertEquals(clinicIoFromFile, typicalPersonsClinicIo);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableClinicIo dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableClinicIo.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableClinicIo dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableClinicIo.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableClinicIo.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
