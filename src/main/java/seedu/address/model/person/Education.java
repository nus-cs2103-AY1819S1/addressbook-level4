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

    private final EducationalLevel educationalLevel;
    private final int educationalGrade;

    /**
     * Constructs an {@code Education}.
     *
     * @param education A valid education.
     */
    public Education(String education) {
        requireNonNull(education);
        checkArgument(isValidEducation(education), MESSAGE_EDUCATION_CONSTRAINTS);
        educationalLevel = setEducationalLevel(education);
        educationalGrade = setEducationalGrade(education);
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
     * Returns a string of the promoted educational grade.
     * Returns the unchanged educational level and grade if the current education is a Final year.
     */
    public String getPromotedEducation() {
        if (isGraduatingYear(this)) {
            return toString();
        }

        return educationalLevel.name() + " " + (educationalGrade + 1);
    }

    /**
     * Initializes educationalLevel based on user input
     */
    public EducationalLevel setEducationalLevel(String education) {
        switch (education.split("\\s+")[0]) {
        case "Primary":
            return EducationalLevel.Primary;
        case "Secondary":
            return EducationalLevel.Secondary;
        default:
            return EducationalLevel.JC;
        }
    }

    /**
     * Initializes educationalGrade based on user input
     */
    public int setEducationalGrade(String education) {
        return Integer.parseInt(education.split("\\s+")[1]);
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

    /**
     * Returns a boolean value to indicate if an education object consist of a education
     * grade which is in the graduating academic year.
     */
    public boolean isGraduatingYear(Education education) {
        return education.toString().equals("JC 2") || education.toString().equals("Primary 6")
                || education.toString().equals("Secondary 4") || education.toString().equals("Secondary 5");
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
