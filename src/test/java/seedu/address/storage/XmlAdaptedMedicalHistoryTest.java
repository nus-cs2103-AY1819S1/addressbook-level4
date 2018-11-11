package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.MedicalHistory;
import seedu.address.testutil.Assert;

public class XmlAdaptedMedicalHistoryTest {
    private ArrayList<XmlAdaptedAllergy> xmlallergies = new ArrayList<>();
    private ArrayList<XmlAdaptedCondition> xmlconditions = new ArrayList<>();

    @Test
    public void toModelType_validAllergy_returnAllergy() throws Exception {
        XmlAdaptedAllergy xmlAllergy = new XmlAdaptedAllergy("egg");
        assertEquals(new Allergy("egg"), xmlAllergy.toModelType());
    }

    @Test
    public void toModelType_validAllergyGet_returnAllergy() throws Exception {
        XmlAdaptedAllergy xmlAllergy = new XmlAdaptedAllergy("egg");
        assertEquals(new Allergy(xmlAllergy.getXmlAdaptedAllergy()), xmlAllergy.toModelType());
    }

    @Test
    public void toModelType_validAllergyBuild_returnAllergy() throws Exception {
        Allergy allergy = new Allergy("egg");
        XmlAdaptedAllergy xmlAllergy = new XmlAdaptedAllergy(allergy);
        assertEquals(allergy, xmlAllergy.toModelType());
    }

    @Test
    public void toModelType_validCondition_returnCondition() throws Exception {
        XmlAdaptedCondition xmlCondition = new XmlAdaptedCondition("healthy");
        assertEquals(new Condition("healthy"), xmlCondition.toModelType());
    }
    @Test
    public void toModelType_validConditionGet_returnCondition() throws Exception {
        XmlAdaptedCondition xmlCondition = new XmlAdaptedCondition("healthy");
        assertEquals(new Condition(xmlCondition.getXmlAdaptedCondition()), xmlCondition.toModelType());
    }

    @Test
    public void toModelType_validConditionBuild_returnCondition() throws Exception {
        Condition condition = new Condition("healthy");
        XmlAdaptedCondition xmlCondition = new XmlAdaptedCondition(condition);
        assertEquals(condition, xmlCondition.toModelType());
    }

    @Test
    public void toModelType_validMedicalHistory_returnMedicalHistory() throws Exception {
        XmlAdaptedAllergy xmlAllergy = new XmlAdaptedAllergy("egg");
        XmlAdaptedCondition xmlCondition = new XmlAdaptedCondition("healthy");
        Allergy allergy = new Allergy("egg");
        Condition condition = new Condition("healthy");
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        xmlallergies.add(xmlAllergy);
        xmlconditions.add(xmlCondition);
        allergies.add(allergy);
        conditions.add(condition);
        assertEquals(new MedicalHistory(allergies, conditions),
                new XmlAdaptedMedicalHistory(xmlallergies, xmlconditions).toModelType());
    }

    @Test
    public void toModelType_validMedicalHistoryBuild_returnMedicalHistory() throws Exception {
        Allergy allergy = new Allergy("egg");
        Condition condition = new Condition("healthy");
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(allergy);
        conditions.add(condition);
        assertEquals(new MedicalHistory(allergies, conditions),
                new XmlAdaptedMedicalHistory(new MedicalHistory(allergies, conditions)).toModelType());
    }

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
        ArrayList<XmlAdaptedAllergy> invalidAllergies = new ArrayList<>();
        invalidAllergies.add(xmlAllergy);
        ArrayList<XmlAdaptedCondition> emptyConditions = new ArrayList<>();
        XmlAdaptedMedicalHistory xmlMedicalHistory = new XmlAdaptedMedicalHistory(invalidAllergies, emptyConditions);
        String expectedMessage = Allergy.MESSAGE_ALLERGY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlMedicalHistory::toModelType);
    }

    @Test
    public void toModelType_invalidConditionInMedicalHistory_throwsIllegalValueException() {
        XmlAdaptedCondition xmlCondition = new XmlAdaptedCondition("~~~");
        ArrayList<XmlAdaptedCondition> invalidConditions = new ArrayList<>();
        invalidConditions.add(xmlCondition);
        ArrayList<XmlAdaptedAllergy> emptyAllergies = new ArrayList<>();
        XmlAdaptedMedicalHistory xmlMedicalHistory = new XmlAdaptedMedicalHistory(emptyAllergies, invalidConditions);
        String expectedMessage = Condition.MESSAGE_CONDITION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, xmlMedicalHistory::toModelType);
    }
}
