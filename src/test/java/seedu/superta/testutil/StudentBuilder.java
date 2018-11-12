package seedu.superta.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.superta.model.student.Email;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Name;
import seedu.superta.model.student.Phone;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tag.Tag;
import seedu.superta.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_STUDENT_ID = "A0166733Y";
    public static final String DEFAULT_FEEDBACK = "";

    private Name name;
    private Phone phone;
    private Email email;
    private StudentId studentId;
    private Set<Tag> tags;
    private List<Feedback> allFeedback;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        studentId = new StudentId(DEFAULT_STUDENT_ID);
        tags = new HashSet<>();
        allFeedback = new ArrayList<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        studentId = studentToCopy.getStudentId();
        tags = new HashSet<>(studentToCopy.getTags());
        allFeedback = new ArrayList<>(studentToCopy.getFeedback());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code StudentId} of the {@code Student} that we are building.
     */
    public StudentBuilder withStudentId(String studentId) {
        this.studentId = new StudentId(studentId);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code feedbacks} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withFeedback(String ... feedbacks) {
        this.allFeedback = SampleDataUtil.getFeedbackList(feedbacks);
        return this;
    }

    public Student build() {
        return new Student(name, phone, email, studentId, tags, allFeedback);
    }

}
