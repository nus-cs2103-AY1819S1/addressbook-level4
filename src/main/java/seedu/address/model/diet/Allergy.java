package seedu.address.model.diet;

//@author yuntongzhang
/**
 * Represents a list of food allergies as part of a diet requirement.
 *
 * @author yuntongzhang
 */
public class Allergy {
    private final List<String> allergyList;

    public Allergy(String... allergies) {
        this.allergyList = new LinkedList<>();

    }


}
