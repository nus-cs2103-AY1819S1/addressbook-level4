package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalModules.TYPICAL_MODULE_ONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.testutil.Assert;

public class XmlAdaptedModuleTest {
    private static final String INVALID_MODULECODE = "C%S!";
    private static final String INVALID_MODULETITLE = "+651234";
    private static final String INVALID_ACADEMICYEAR = " ";
    private static final String INVALID_SEMESTER = "5";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_MODULECODE = TYPICAL_MODULE_ONE.getModuleCode().toString();
    private static final String VALID_MODULETITLE = TYPICAL_MODULE_ONE.getModuleTitle().toString();
    private static final String VALID_ACADEMICYEAR = TYPICAL_MODULE_ONE.getAcademicYear().toString();
    private static final String VALID_SEMESTER = TYPICAL_MODULE_ONE.getSemester().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = TYPICAL_MODULE_ONE.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        XmlAdaptedModule module = new XmlAdaptedModule(TYPICAL_MODULE_ONE);
        assertEquals(TYPICAL_MODULE_ONE, module.toModelType());
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        XmlAdaptedModule module =
                new XmlAdaptedModule(INVALID_MODULECODE, VALID_MODULETITLE, VALID_ACADEMICYEAR,
                        VALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        XmlAdaptedModule module = new XmlAdaptedModule(null, VALID_MODULETITLE, VALID_ACADEMICYEAR,
                        VALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = String
                .format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidModuleTitle_throwsIllegalValueException() {
        XmlAdaptedModule module =
                new XmlAdaptedModule(VALID_MODULECODE, INVALID_MODULETITLE, VALID_ACADEMICYEAR,
                        VALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullModuleTitle_throwsIllegalValueException() {
        XmlAdaptedModule module =
                new XmlAdaptedModule(VALID_MODULECODE, null, VALID_ACADEMICYEAR,
                        VALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = String
                .format(MISSING_FIELD_MESSAGE_FORMAT, ModuleTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidAcademicYear_throwsIllegalValueException() {
        XmlAdaptedModule module =
                new XmlAdaptedModule(VALID_MODULECODE, VALID_MODULETITLE, INVALID_ACADEMICYEAR,
                        VALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = AcademicYear.MESSAGE_ACADEMICYEAR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullAcademicYear_throwsIllegalValueException() {
        XmlAdaptedModule module =
                new XmlAdaptedModule(VALID_MODULECODE, VALID_MODULETITLE, null,
                        VALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = String
                .format(MISSING_FIELD_MESSAGE_FORMAT, AcademicYear.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidSemester_throwsIllegalValueException() {
        XmlAdaptedModule module =
                new XmlAdaptedModule(VALID_MODULECODE, VALID_MODULETITLE, VALID_ACADEMICYEAR,
                        INVALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = Semester.MESSAGE_SEMESTER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullSemester_throwsIllegalValueException() {
        XmlAdaptedModule module =
                new XmlAdaptedModule(VALID_MODULECODE, VALID_MODULETITLE, VALID_ACADEMICYEAR,
                        null, new ArrayList<XmlAdaptedPerson>(), VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Semester.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedModule module =
                new XmlAdaptedModule(VALID_MODULECODE, VALID_MODULETITLE, VALID_ACADEMICYEAR,
                        INVALID_SEMESTER, new ArrayList<XmlAdaptedPerson>(), invalidTags);
        Assert.assertThrows(IllegalValueException.class, module::toModelType);
    }
}
