package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.MedicalHistory;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TAG = "Patient";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_TELEGRAM_ID = "1234";
    public static final String VALID_ALLERGY = "milk";
    public static final String VALID_CONDITION = "sub-health";


    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;
    private String telegramId;
    private MedicalHistory medicalHistory;
    private List<Appointment> upcomingAppointments;
    private List<Appointment> pastAppointments;



    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
        telegramId = DEFAULT_TELEGRAM_ID;
        medicalHistory = new MedicalHistory(new ArrayList<String>(Arrays.asList(VALID_ALLERGY)),
                new ArrayList<String>(Arrays.asList(VALID_CONDITION)));
        upcomingAppointments = new ArrayList<>();
        pastAppointments = new ArrayList<>();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code personToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        remark = patientToCopy.getRemark();
        tags = new HashSet<>(patientToCopy.getTags());
        medicalHistory = new MedicalHistory(patientToCopy.getMedicalHistory());
        upcomingAppointments = patientToCopy.getUpcomingAppointments();
        pastAppointments = patientToCopy.getPastAppointments();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Patient} that we are building.
     */
    public PatientBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code MedicalHistory} of the {@code Patient} that we are building.
     */
    public PatientBuilder withMedicalHistory(String allergy, String condition) {
        if (allergy.equals("") && condition.equals("")) {
            this.medicalHistory = new MedicalHistory();
        } else if (!(allergy.equals("")) && condition.equals("")) {
            this.medicalHistory = new MedicalHistory(new ArrayList<String>(Arrays.asList(allergy)), new ArrayList<>());
        } else if ((allergy.equals("")) && !(condition.equals(""))) {
            this.medicalHistory = new MedicalHistory(new ArrayList<>(),
                    new ArrayList<String>(Arrays.asList(condition)));
        } else {
            this.medicalHistory = new MedicalHistory(new ArrayList<String>(Arrays.asList(allergy)),
                    new ArrayList<String>(Arrays.asList(condition)));
        }

        return this;
    }

    /**
     * Adds one {@code appointment} of the {@code Patient} that we are building
     */
    public PatientBuilder withAppointment(Appointment ... allAppointments) {
        for (Appointment appointment : allAppointments) {
            upcomingAppointments.add(appointment);
        }
        return this;
    }

    /**
     * constructor
     */
    public Patient build() {
        return new Patient(name, phone, email, address, remark, tags, telegramId,
                upcomingAppointments, pastAppointments, medicalHistory);
    }
}
