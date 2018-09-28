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

public class XmlAdaptedTaskTest {
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
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(BENSON);
        assertEquals(BENSON, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(INVALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_DUE_DATE, VALID_PRIORITY_VALUE,
                VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDueDate_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, INVALID_DUE_DATE, VALID_PRIORITY_VALUE, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = DueDate.MESSAGE_DUEDATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDueDate_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, null, VALID_PRIORITY_VALUE,
                VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPriorityValue_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, INVALID_PRIORITY_VALUE, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = PriorityValue.MESSAGE_PRIORITYVALUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPriorityValue_throwsIllegalValueException() {
        XmlAdaptedTask priorityValue = new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE,
                null, VALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PriorityValue.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, priorityValue::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, INVALID_DESCRIPTION, VALID_LABELS);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, null, VALID_LABELS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidLabels_throwsIllegalValueException() {
        List<XmlAdaptedLabel> invalidLabels = new ArrayList<>(VALID_LABELS);
        invalidLabels.add(new XmlAdaptedLabel(INVALID_LABEL));
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_DUE_DATE, VALID_PRIORITY_VALUE, VALID_DESCRIPTION, invalidLabels);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }
}
