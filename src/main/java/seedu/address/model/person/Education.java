package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's educational level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEducation(String)}
 */
public class Education {
    public static final String MESSAGE_EDUCATION_CONSTRAINTS =
            "Education should contain level and grade, separated by a space in between.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EDUCATION_VALIDATION_REGEX = "[\\S*]{2,10}[\\s][1-6]";

    /**
     * Represents the educational levels which are allowed.
     * Also represents the list of educational levels which the tutor is able to teach.
     */
    enum EducationalLevel {
        Primary, Secondary, JC
    }

    private EducationalLevel educationalLevel;
    private int educationalGrade;

    /**
     * Constructs an {@code Education}.
     *
     * @param education A valid education.
     */
    public Education(String education) {
        requireNonNull(education);
        checkArgument(isValidEducation(education), MESSAGE_EDUCATION_CONSTRAINTS);
        setEducationalLevelAndGrade(education);
    }

    /**
     * Returns the educational level of the person.
     */
    public EducationalLevel getEducationalLevel() {
        return educationalLevel;
    }

    /**
     * Returns the educational grade of the person.
     */
    public int getEducationalGrade() {
        return educationalGrade;
    }

    /**
     * Sets the Educational Level and Grade based on user input.
     */
    public void setEducationalLevelAndGrade(String education) {
        String[] splittedEducation = education.split("\\s+");
        educationalGrade = Integer.valueOf(splittedEducation[1]);

        switch (splittedEducation[0]) {
        case "Primary":
            educationalLevel = EducationalLevel.Primary;
            break;
        case "Secondary":
            educationalLevel = EducationalLevel.Secondary;
            break;

        case "JC":
        default:
            educationalLevel = EducationalLevel.JC;
            break;
        }
    }

    /**
     * Returns true if a given string is a valid education.
     */
    public static boolean isValidEducation(String test) {
        if (!test.matches(EDUCATION_VALIDATION_REGEX)) {
            return false;
        }

        String[] splitTest = test.split("\\s+");
        int grade = Integer.valueOf(splitTest[1]);

        if (splitTest[0].equals("Primary")) {
            return grade <= 6;
        } else if (splitTest[0].equals("Secondary")) {
            return grade <= 5;
        } else if (splitTest[0].equals("JC")) {
            return grade <= 2;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return educationalLevel.toString() + " " + educationalGrade;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Education // instanceof handles nulls
                && educationalLevel.equals(((Education) other).educationalLevel)
                && educationalGrade == ((Education) other).educationalGrade); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(educationalLevel, educationalGrade);
    }
}
