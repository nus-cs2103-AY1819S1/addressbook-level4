package seedu.address.commons.util;

import seedu.address.model.volunteer.Gender;

/**
 * Utility methods related to Gender
 */
public class GenderUtil {
    public static final String GENDER_MALE_VALIDATION_REGEX = "m";
    public static final String GENDER_FEMALE_VALIDATION_REGEX = "f";
    public static final String GENDER_MALE_FORMAT = "Male";
    public static final String GENDER_FEMALE_FORMAT = "Female";

    private static String formattedGender;
    /**
     * Returns a friendly date string of a Volunteer Birthday object.
     */
    public static String getFriendlyGenderFromVolunteerGender(Gender gender) {
        if (gender.value.equals(GENDER_FEMALE_VALIDATION_REGEX)) {
            formattedGender = GENDER_FEMALE_FORMAT;
        } else if (gender.value.equals(GENDER_MALE_VALIDATION_REGEX)) {
            formattedGender = GENDER_MALE_FORMAT;
        }
        return formattedGender;
    }
}
