package tutorhelper.storage;

import static tutorhelper.model.subject.SubjectType.convertStringToSubjectName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import tutorhelper.commons.exceptions.IllegalValueException;
import tutorhelper.model.subject.Subject;
import tutorhelper.model.subject.SubjectType;
import tutorhelper.model.subject.Syllabus;

/**
 * JAXB-friendly adapted version of the Subject.
 */
public class XmlAdaptedSubject {

    @XmlAttribute
    private String subjectName;

    @XmlElement
    private List<XmlAdaptedSyllabus> subjectContent = new ArrayList<>();

    @XmlAttribute
    private float completionRate;

    /**
     * Constructs an XmlAdaptedSubject.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSubject() {}

    /**
     * Converts a given Subject into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSyllabusBook
     */
    public XmlAdaptedSubject(Subject source) {
        this.subjectName = source.getSubjectName();
        this.subjectContent = source.getSubjectContent().stream()
            .map(XmlAdaptedSyllabus::new)
            .collect(Collectors.toList());
        this.completionRate = source.getCompletionRate();
    }

    public XmlAdaptedSubject(String subjectName, List<XmlAdaptedSyllabus> subjectContent, float completionRate) {
        this.subjectName = subjectName;
        this.subjectContent = subjectContent;
        this.completionRate = completionRate;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Subject object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student
     */
    public Subject toModelType() throws IllegalValueException {
        if (!SubjectType.isValidSubjectName(subjectName)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }

        List<Syllabus> modelSyllabus = new ArrayList<>();
        for (XmlAdaptedSyllabus syllabus : subjectContent) {
            modelSyllabus.add(syllabus.toModelType());
        }
        return new Subject(convertStringToSubjectName(subjectName), modelSyllabus, completionRate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSubject)) {
            return false;
        }

        return subjectName.equals(((XmlAdaptedSubject) other).subjectName)
                && contentAreSame((XmlAdaptedSubject) other);
    }

    /**
     * Checks whether the content of this syllabus book is the same
     * with the other syllabus book.
     * @param other the one to be compared to
     * @return true if contents are the same
     */
    private boolean contentAreSame(XmlAdaptedSubject other) {
        if (subjectContent.size() != other.subjectContent.size()) {
            return false;
        }
        for (int i = 0, j = 0; i < subjectContent.size(); i++, j++) {
            if (!subjectContent.get(i).equals(other.subjectContent.get(j))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectName, subjectContent);
    }
}
