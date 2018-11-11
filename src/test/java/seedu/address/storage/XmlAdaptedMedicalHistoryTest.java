package seedu.address.storage;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.Condition;
import seedu.address.testutil.Assert;

public class XmlAdaptedMedicalHistoryTest {
    private ArrayList<XmlAdaptedAllergy> allergies = new ArrayList<>();
    private ArrayList<XmlAdaptedCondition> conditions = new ArrayList<>();

    @Test
    public void toModelType_invalidAllergy_throwsIllegalValueException() {
        XmlAdaptedAllergy xmlAllergy = new XmlAdaptedAllergy("~~~");
        String expectedMessage = Allergy.MESSAGE_ALLERGY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlAllergy::toModelType);
    }

    @Test
    public void toModelType_invalidCondition_throwsIllegalValueException() {
        XmlAdaptedCondition xmlCondition = new XmlAdaptedCondition("~~~");
        String expectedMessage = Condition.MESSAGE_CONDITION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlCondition::toModelType);
    }

    @Test
    public void toModelType_invalidAllergyInMedicalHistory_throwsIllegalValueException() {
        XmlAdaptedAllergy xmlAllergy = new XmlAdaptedAllergy("~~~");
        allergies.add(xmlAllergy);
        ArrayList<XmlAdaptedCondition> emptyConditions = new ArrayList<>();
        XmlAdaptedMedicalHistory xmlMedicalHistory = new XmlAdaptedMedicalHistory(allergies, emptyConditions);
        String expectedMessage = Allergy.MESSAGE_ALLERGY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlMedicalHistory::toModelType);
    }

    @Test
    public void toModelType_invalidConditionInMedicalHistory_throwsIllegalValueException() {
        XmlAdaptedCondition xmlCondition = new XmlAdaptedCondition("~~~");
        conditions.add(xmlCondition);
        ArrayList<XmlAdaptedAllergy> emptyAllergies = new ArrayList<>();
        XmlAdaptedMedicalHistory xmlMedicalHistory = new XmlAdaptedMedicalHistory(emptyAllergies, conditions);
        String expectedMessage = Condition.MESSAGE_CONDITION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlMedicalHistory::toModelType);
    }
}
