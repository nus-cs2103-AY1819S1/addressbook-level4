package seedu.superta.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.student.Email;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Name;
import seedu.superta.model.student.Phone;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tag.Tag;

/**
 * JAXB-friendly version of the Student.
 */
public class XmlAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String studentId;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedFeedback> allFeedback = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedStudent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedStudent() {}

    /**
     * Constructs an {@code XmlAdaptedStudent} with the given student details.
     */
    public XmlAdaptedStudent(String name, String phone, String email, String studentId,
                             List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentId = studentId;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Student into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStudent
     */
    public XmlAdaptedStudent(Student source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        studentId = source.getStudentId().studentId;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        allFeedback = source.getFeedback().stream()
                .map(XmlAdaptedFeedback::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted student object into the model's Student object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student
     */
    public Student toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final List<Feedback> allStudentFeedback = new ArrayList<>();
        for (XmlAdaptedFeedback feedback : allFeedback) {
            allStudentFeedback.add(feedback.toModelType());
        }

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
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_STUDENT_ID_CONSTRAINTS);
        }
        final StudentId modelStudentId = new StudentId(studentId);

        final List<Feedback> modelFeedback = new ArrayList<>(allStudentFeedback);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        Student modelStudent = new Student(modelName, modelPhone, modelEmail, modelStudentId,
                modelTags, modelFeedback);
        return modelStudent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedStudent)) {
            return false;
        }

        XmlAdaptedStudent otherPerson = (XmlAdaptedStudent) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(studentId, otherPerson.studentId)
                && tagged.equals(otherPerson.tagged)
                && allFeedback.equals(otherPerson.allFeedback);
    }
}
