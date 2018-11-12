package seedu.superta.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.model.assignment.GradeEntry;

/**
 * An XML Representation of a Grade in a gradebook.
 */
public class XmlGrade {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Grade's %s field is missing!";

    @XmlElement(required = true)
    private String studentId;
    @XmlElement(required = true)
    private Double marks;

    public XmlGrade() { }

    public XmlGrade(GradeEntry entry) {
        this.studentId = entry.studentId.studentId;
        this.marks = entry.marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public Double getMarks() {
        return marks;
    }
}
