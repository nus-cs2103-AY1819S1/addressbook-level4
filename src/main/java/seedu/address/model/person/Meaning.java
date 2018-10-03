package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Meaning {

    public static final String MESSAGE_MEANING_CONSTRAINTS = "Meaning of a word should not be left blank";


    public final String fullMeaning;

    public Meaning(String meaning){
        requireNonNull(meaning);

        fullMeaning = meaning;
    }

    @Override
    public String toString() {
        return fullMeaning;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meaning // instanceof handles nulls
                && fullMeaning.equals(((Meaning) other).fullMeaning)); // state check
    }

    @Override
    public int hashCode() {
        return fullMeaning.hashCode();
    }
}
