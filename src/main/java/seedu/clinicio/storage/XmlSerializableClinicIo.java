package seedu.clinicio.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;

import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

/**
 * An Immutable ClinicIo that is serializable to XML format
 */
@XmlRootElement(name = "clinicio")
public class XmlSerializableClinicIo {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";
    public static final String MESSAGE_DUPLICATE_STAFF = "Staffs list contains duplicate staff(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    @XmlElement
    private List<XmlAdaptedPatient> patients;

    @XmlElement
    private List<XmlAdaptedStaff> staffs;

    /**
     * Creates an empty XmlSerializableClinicIo.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableClinicIo() {
        persons = new ArrayList<>();
        patients = new ArrayList<>();
        staffs = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableClinicIo(ReadOnlyClinicIo src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        patients.addAll(src.getPatientList().stream().map(XmlAdaptedPatient::new).collect(Collectors.toList()));
        staffs.addAll(src.getStaffList().stream().map(XmlAdaptedStaff::new).collect(Collectors.toList()));
    }

    /**
     * Converts this ClinicIO into the model's {@code ClinicIo} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} & {@code XmlAdaptedStaff} & & {@code XmlAdaptedReceptionist}.
     */
    public ClinicIo toModelType() throws IllegalValueException {
        ClinicIo clinicIo = new ClinicIo();
        for (XmlAdaptedPerson p : persons) {
            Person person = Patient.buildFromPerson(p.toModelType());
            if (clinicIo.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            clinicIo.addPerson(person);
        }
        for (XmlAdaptedPatient pa: patients) {
            Patient patient = pa.toModelType();
            if (clinicIo.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            clinicIo.addPatient(patient);
        }
        for (XmlAdaptedStaff s : staffs) {
            Staff staff = s.toModelType();
            if (clinicIo.hasStaff(staff)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            clinicIo.addStaff(staff);
        }

        return clinicIo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableClinicIo)) {
            return false;
        }

        return persons.equals(((XmlSerializableClinicIo) other).persons)
                && patients.equals(((XmlSerializableClinicIo) other).patients)
                && staffs.equals(((XmlSerializableClinicIo) other).staffs);

    }
}
