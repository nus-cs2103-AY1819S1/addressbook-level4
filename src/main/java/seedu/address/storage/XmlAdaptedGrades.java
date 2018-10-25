package seedu.address.storage;

import java.util.Arrays;
import java.util.Map;

import javax.xml.bind.annotation.XmlValue;

import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Grades;

/**
 * JAXB-friendly adapted version of the Grades.
 */
public class XmlAdaptedGrades {
    @XmlValue
    private String grade;

    /**
     * Constructs an XmlAdaptedGrades.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGrades() {
    }


    public XmlAdaptedGrades(String grade) {
        this.grade = grade;
    }

    /**
     * Constructs a {@code XmlAdaptedGrades} with the given {@code gradeInput}.
     */
    public XmlAdaptedGrades(Map.Entry<String, Grades> grade) {
        this.grade = grade.getKey() + " " + grade.getValue().toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Pair<String, Grades> toModelType() throws IllegalValueException {
        if (!Grades.isValidGradeInput(grade)) {
            throw new IllegalValueException(Grades.MESSAGE_GRADE_INPUT_CONSTRAINTS);
        }
        String[] splitGrade = grade.trim().split("\\s+");
        return new Pair<>(splitGrade[0], new Grades(splitGrade[1]));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGrades)) {
            return false;
        }

        return Arrays.equals(
                grade.trim().split("\\s+"), ((XmlAdaptedGrades) other).grade.trim().split("\\s+"));
    }

}
