package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Dose;

//@@author snajef
/**
 * XML adapted version of the Dose class.
 * @author Darien Chong
 *
 */
@XmlRootElement
public class XmlAdaptedDose {
    @XmlElement(required = true)
    private double dosage;

    @XmlElement(required = true)
    private String dosageUnit;

    @XmlElement(required = true)
    private int dosesPerDay;

    public XmlAdaptedDose() {}

    public XmlAdaptedDose(double dosage, String dosageUnit, int dosesPerDay) throws IllegalValueException {
        if (dosage <= 0) {
            throw new IllegalValueException(Dose.MESSAGE_DOSAGE_MUST_BE_POSITIVE);
        }

        this.dosage = dosage;
        this.dosageUnit = dosageUnit;

        if (dosesPerDay <= 0) {
            throw new IllegalValueException(Dose.MESSAGE_DOSES_PER_DAY_MUST_BE_POSITIVE_INTEGER);
        }

        this.dosesPerDay = dosesPerDay;
    }

    public XmlAdaptedDose(Dose source) {
        dosage = source.getDose();
        dosageUnit = source.getDoseUnit();
        dosesPerDay = source.getDosesPerDay();
    }

    public Dose toModelType() throws IllegalValueException {
        return new Dose(dosage, dosageUnit, dosesPerDay);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof XmlAdaptedDose)) {
            return false;
        }

        XmlAdaptedDose xad = (XmlAdaptedDose) o;
        return dosage == xad.dosage
            && dosageUnit.equals(xad.dosageUnit)
            && dosesPerDay == xad.dosesPerDay;
    }
}
