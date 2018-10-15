package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;
import seedu.address.model.person.medicalrecord.BloodType;
import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ICNUMBER = "S1234567X";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BLOODTYPE = "A+";

    private Name name;
    private IcNumber icNumber;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private MedicalRecord medicalRecord;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        icNumber = new IcNumber(DEFAULT_ICNUMBER);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        medicalRecord = new MedicalRecord(new BloodType(DEFAULT_BLOODTYPE));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code patientToCopy}.
     */
    public PersonBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        icNumber = patientToCopy.getIcNumber();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        tags = new HashSet<>(patientToCopy.getTags());
        medicalRecord = patientToCopy.getMedicalRecord();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code IcNumber} of the {@code Patient} that we are building.
     */
    public PersonBuilder withIcNumber(String icNumber) {
        this.icNumber = new IcNumber(icNumber);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code MedicalRecord} of the {@code Patient} that we are building.
     * @param bloodType
     */
    public PersonBuilder withMedicalRecord(String bloodType) {
        this.medicalRecord = new MedicalRecord(new BloodType(bloodType));
        return this;
    }

    public Patient build() {
        return new Patient(name, icNumber, phone, email, address, tags, medicalRecord);
    }

}
