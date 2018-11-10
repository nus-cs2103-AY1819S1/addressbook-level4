package seedu.address.storage;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.MedicalHistory;

/**
 * JAXB-friendly adapted version of the MedicalHistory.
 */
public class XmlAdaptedMedicalHistory {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MedicalHistory's %s field is missing!";

    @XmlElement
    private ArrayList<XmlAdaptedAllergy> allergies = new ArrayList<>();
    @XmlElement
    private ArrayList<XmlAdaptedCondition> conditions = new ArrayList<>();

    public ArrayList<XmlAdaptedAllergy> getXmlAdaptedAllergies() {
        return allergies;
    }
    public ArrayList<XmlAdaptedCondition> getXmlAdaptedConditions() {
        return conditions;
    }

    /**
     * Constructs an XmlAdaptedMedicalHistory.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedicalHistory(){}
    
    public XmlAdaptedMedicalHistory(ArrayList<XmlAdaptedAllergy> allergies, ArrayList<XmlAdaptedCondition> conditions) {
        this.allergies = allergies;
        this.conditions = conditions;
    }

    public XmlAdaptedMedicalHistory(MedicalHistory source) {
        for (int i = 0; i < source.getAllergies().size(); i++) {
            this.allergies.add(new XmlAdaptedAllergy(source.getAllergies().get(i)));
        }
        for (int i = 0; i < source.getConditions().size(); i++) {
            this.conditions.add(new XmlAdaptedCondition(source.getConditions().get(i)));
        }
    }


    /**
     * Converts this jaxb-friendly adapted MedicalHistory object into the model's MedicalHistory object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public MedicalHistory toModelType() throws IllegalValueException {

        ArrayList<Allergy> al = new ArrayList<>();
        ArrayList<Condition> c = new ArrayList<>();

        for (int i = 0; i < allergies.size(); i++) {
            al.add(this.allergies.get(i).toModelType());
        }

        for (int i = 0; i < conditions.size(); i++) {
            c.add(this.conditions.get(i).toModelType());
        }

        return new MedicalHistory(al, c);
    }


    public ArrayList<XmlAdaptedAllergy> getAllergies() {
        return allergies;
    }

    public ArrayList<XmlAdaptedCondition> getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedicalHistory)) {
            return false;
        }

        if (allergies.equals(((XmlAdaptedMedicalHistory) other).getAllergies())
                && conditions.equals(((XmlAdaptedMedicalHistory) other).getConditions())) {
            return true;
        } else {
            return false;
        }
    }
}
