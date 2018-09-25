package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTasks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.person.DueDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.PriorityValue;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DUE_DATE = "+651234";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_PRIORITY_VALUE = "example.com";
    private static final String INVALID_LABEL = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_DUE_DATE = BENSON.getDueDate().toString();
    private static final String VALID_PRIORITY_VALUE = BENSON.getPriorityValue().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
    private static final List<XmlAdaptedLabel> VALID_LABELS = BENSON.getLabels().stream()
            .map(XmlAdaptedLabel::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedTask person = new XmlAdaptedTask(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTask person =
                new XmlAdaptedTask(INVALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(null, VALID_DUE_DATE, VALID_PRIORITY_VALUE,
                VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedTask person =
                new XmlAdaptedTask(VALID_NAME, INVALID_DUE_DATE, VALID_PRIORITY_VALUE, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = DueDate.MESSAGE_DUEDATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_NAME, null, VALID_PRIORITY_VALUE,
                VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedTask person =
                new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, INVALID_PRIORITY_VALUE, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = PriorityValue.MESSAGE_PRIORITYVALUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, null, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PriorityValue.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, INVALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, null, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedLabel> invalidTags = new ArrayList<>(VALID_LABELS);
        invalidTags.add(new XmlAdaptedLabel(INVALID_LABEL));
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, VALID_DESCRIPTION, invalidTags);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

}
