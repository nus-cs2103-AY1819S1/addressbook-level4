package seedu.superta.model.student;

import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.superta.model.tag.Tag;

/**
 * Represents a Student in the SuperTA client.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final StudentId studentId;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final ObservableList<Feedback> feedback = FXCollections.observableArrayList();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, StudentId studentId, Set<Tag> tags,
                   List<Feedback> feedback) {
        requireAllNonNull(name, phone, email, studentId, tags, feedback);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentId = studentId;
        this.tags.addAll(tags);
        this.feedback.addAll(feedback);
    }

    public Student(Student other) {
        name = other.name;
        phone = other.phone;
        email = other.email;
        studentId = other.studentId;
        tags.addAll(other.tags);
        feedback.addAll(other.feedback);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && (otherStudent.getPhone().equals(getPhone()) || otherStudent.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both students have the same ID.
     */
    public boolean isSameId(Student otherStudent) {
        return studentId.equals(otherStudent.studentId);
    }

    /**
     * Returns true if both persons have the same student id.
     */
    public boolean hasSameId(StudentId stId) {
        return studentId.equals(stId);
    }

    /**
     * Returns an immutable list of feedback, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<Feedback> getFeedback() {
        return FXCollections.unmodifiableObservableList(feedback);
    }

    public void addFeedback(Feedback feedback) {
        this.feedback.add(feedback);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getPhone().equals(getPhone())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getStudentId().equals(getStudentId())
                && otherStudent.getTags().equals(getTags())
                && otherStudent.getFeedback().equals(getFeedback());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, studentId, tags, feedback);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Student ID: ")
                .append(getStudentId())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        // TODO: feedback?
        return builder.toString();
    }

}
