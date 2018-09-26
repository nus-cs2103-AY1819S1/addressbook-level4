package seedu.address.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;

//@@author snajef
/**
 * JAXB-friendly adapted version of the Prescription.
 */

@XmlRootElement
public class XmlAdaptedPrescription {
    @XmlElement
    private String drugName;

    @XmlElement
    private double dosage;

    @XmlElement
    private String doseUnit;

    @XmlElement
    private int dosesPerDay;

    @XmlElement
    private String startDate;

    @XmlElement
    private String endDate;

    @XmlElement
    private double durationInMilliseconds;

    /**
     * Constructs an XmlAdaptedPrescription.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPrescription() {}

    /**
     * Constructs a {@code XmlAdaptedPrescription} with the given details.
     */
    public XmlAdaptedPrescription(String drugName, double dosage, String doseUnit, int dosesPerDay, String startDate,
            String endDate, double durationInMilliseconds) {
        this.drugName = drugName;
        this.dosage = dosage;
        this.doseUnit = doseUnit;
        this.dosesPerDay = dosesPerDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationInMilliseconds = durationInMilliseconds;
    }

    /**
     * Converts a given Prescription into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPrescription(Prescription source) {
        drugName = source.getDrugName();
        dosage = source.getDose().getDose();
        doseUnit = source.getDose().getDoseUnit();
        dosesPerDay = source.getDose().getDosesPerDay();
        startDate = source.getDuration().getStartDateAsString();
        endDate = source.getDuration().getEndDateAsString();
        durationInMilliseconds = source.getDuration().getDurationInMilliseconds();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Prescription toModelType() throws IllegalValueException {
        Dose dose = new Dose(dosage, doseUnit, dosesPerDay);

        SimpleDateFormat dateFormat = Duration.DATE_FORMAT;
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        try {
            startDate.setTime(dateFormat.parse(this.startDate));
            endDate.setTime(dateFormat.parse(this.endDate));
        } catch (ParseException e) {
            throw new IllegalValueException(e.toString());
        }

        Duration duration = new Duration(Duration.getAsDays(durationInMilliseconds));
        duration.shiftDateRange(startDate);

        return new Prescription(drugName, dose, duration);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPrescription)) {
            return false;
        }

        return drugName.equals(((XmlAdaptedPrescription) other).drugName)
                && dosage == ((XmlAdaptedPrescription) other).dosage
                && doseUnit.equals(((XmlAdaptedPrescription) other).doseUnit)
                && dosesPerDay == ((XmlAdaptedPrescription) other).dosesPerDay
                && startDate.equals(((XmlAdaptedPrescription) other).startDate)
                && endDate.equals(((XmlAdaptedPrescription) other).endDate)
                && durationInMilliseconds == ((XmlAdaptedPrescription) other).durationInMilliseconds;
    }
}
