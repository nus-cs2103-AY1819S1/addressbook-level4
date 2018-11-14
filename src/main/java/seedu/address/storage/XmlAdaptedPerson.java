package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Mark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmptyAddress;
import seedu.address.model.person.EmptyEmail;
import seedu.address.model.person.EmptyPhone;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePhoto;
import seedu.address.model.tag.Tag;



/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String profilephoto;
    @XmlElement(required = true)
    private String note;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedMark> marks = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedMark> attendance = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address,
                            List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.profilephoto = null;
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with an additional Picture parameter
     * marks parameter, attendance parameter and note parameter.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, String profilephoto,
                            List<XmlAdaptedTag> tagged, List<XmlAdaptedMark> marks,
                            List<XmlAdaptedMark> attendance, String note) {
        this(name, phone, email, address, tagged);
        this.profilephoto = profilephoto;
        this.note = note;
        if (marks != null) {
            this.marks = new ArrayList<>(marks);
        }
        if (attendance != null) {
            this.attendance = new ArrayList<>(attendance);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source, List<String> allowedAssignmentUid, List<String> allowedAttendanceUid) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        profilephoto = source.getProfilePhoto().getPath();
        note = source.getNote().toString();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        for (String key: allowedAssignmentUid) {
            Mark mark = source.getMarks().get(key);
            if (mark != null) {
                marks.add(new XmlAdaptedMark(key, mark.internalString));
            }
        }
        for (String key: allowedAttendanceUid) {
            Mark mark = source.getAttendance().get(key);
            if (mark != null) {
                attendance.add(new XmlAdaptedMark(key, mark.internalString));
            }
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        profilephoto = source.getProfilePhoto().getPath();
        note = source.getNote().toString();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        for (Map.Entry<String, Mark> entry : source.getMarks().entrySet()) {
            marks.add(new XmlAdaptedMark(entry.getKey(), entry.getValue().internalString));
        }
        for (Map.Entry<String, Mark> entry : source.getAttendance().entrySet()) {
            attendance.add(new XmlAdaptedMark(entry.getKey(), entry.getValue().internalString));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {


        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!EmptyPhone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        // phone is guaranteed to be legal, it can only be saved as data if it was created and unedited
        // unless data was tampered with
        final Phone modelPhone = phone.equals("") ? new EmptyPhone() : new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!EmptyEmail.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        // email is guaranteed to be legal, it can only be saved as data if it was created and unedited
        // unless data was tampered with
        final Email modelEmail = email.equals("") ? new EmptyEmail() : new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!EmptyAddress.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        // address is guaranteed to be legal, it can only be saved as data if it was created and unedited
        // unless data was tampered with
        final Address modelAddress = address.equals("") ? new EmptyAddress() : new Address(address);

        ProfilePhoto modelPhoto = new ProfilePhoto();
        if (this.profilephoto != null) {
            modelPhoto = new ProfilePhoto(this.profilephoto);
        }

        Note modelNote = new Note();
        if (this.note != null) {
            modelNote = new Note(this.note);
        }

        final Set<Tag> modelTags = new HashSet<>();
        for (XmlAdaptedTag tag : tagged) {
            modelTags.add(tag.toModelType());
        }

        final Map<String, Mark> modelMarks = new HashMap<>();
        for (XmlAdaptedMark mark : marks) {
            modelMarks.put(mark.getKey(), mark.toModelType());
        }

        final Map<String, Mark> modelAttendance = new HashMap<>();
        for (XmlAdaptedMark mark : attendance) {
            modelAttendance.put(mark.getKey(), mark.toModelType());
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelPhoto, modelTags,
          modelMarks, modelAttendance, modelNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;

        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && Objects.equals(profilephoto, otherPerson.profilephoto)
                && tagged.equals(otherPerson.tagged)
                && marks.equals(otherPerson.marks)
                && attendance.equals(otherPerson.attendance)
                && Objects.equals(note, otherPerson.note);
    }
}
