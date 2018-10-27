package seedu.clinicio.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;

import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.receptionist.Receptionist;

/**
 * An Immutable ClinicIo that is serializable to XML format
 */
@XmlRootElement(name = "clinicio")
public class XmlSerializableClinicIo {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_DOCTOR = "Doctors list contains duplicate doctor(s).";
    public static final String MESSAGE_DUPLICATE_RECEPTIONIST = "Doctors list contains duplicate receptionist(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    @XmlElement
    private List<XmlAdaptedDoctor> doctors;

    @XmlElement
    private List<XmlAdaptedReceptionist> receptionists;
    /**
     * Creates an empty XmlSerializableClinicIo.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableClinicIo() {
        persons = new ArrayList<>();
        doctors = new ArrayList<>();
        receptionists = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableClinicIo(ReadOnlyClinicIo src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        doctors.addAll(src.getDoctorList().stream().map(XmlAdaptedDoctor::new).collect(Collectors.toList()));
        receptionists.addAll(
                src.getReceptionistList().stream().map(XmlAdaptedReceptionist::new).collect(Collectors.toList()));
    }

    /**
     * Converts this ClinicIO into the model's {@code ClinicIo} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} & {@code XmlAdaptedDoctor} & & {@code XmlAdaptedReceptionist}.
     */
    public ClinicIo toModelType() throws IllegalValueException {
        ClinicIo clinicIo = new ClinicIo();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (clinicIo.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            clinicIo.addPerson(person);
        }
        //@@author jjlee050
        for (XmlAdaptedDoctor d : doctors) {
            Doctor doctor = d.toModelType();
            if (clinicIo.hasDoctor(doctor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DOCTOR);
            }
            clinicIo.addDoctor(doctor);
        }

        for (XmlAdaptedReceptionist r : receptionists) {
            Receptionist receptionist = r.toModelType();
            if (clinicIo.hasReceptionist(receptionist)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECEPTIONIST);
            }
            clinicIo.addReceptionist(receptionist);
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
                && doctors.equals(((XmlSerializableClinicIo) other).doctors)
                && receptionists.equals(((XmlSerializableClinicIo) other).receptionists);

    }
}
