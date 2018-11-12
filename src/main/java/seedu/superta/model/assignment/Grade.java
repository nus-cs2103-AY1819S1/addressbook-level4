package seedu.superta.model.assignment;

import seedu.superta.model.student.StudentId;

/**
 * Model for Grading details.
 */
public class Grade {
    private final String tgId;
    private final Title asId;
    private final StudentId stId;
    private final Double marks;

    public Grade(String tgId, Title asId, StudentId stId, Double marks) {
        this.tgId = tgId;
        this.asId = asId;
        this.stId = stId;
        this.marks = marks;
    }

    public String getTgId() {
        return tgId;
    }

    public Title getAsId() {
        return asId;
    }

    public StudentId getStId() {
        return stId;
    }

    public Double getMarks() {
        return marks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Grade e = (Grade) other;
        return tgId.equals(e.getTgId())
                && asId.equals(e.getAsId())
                && stId.equals(e.getStId())
                && marks.equals(e.getMarks());
    }
}
