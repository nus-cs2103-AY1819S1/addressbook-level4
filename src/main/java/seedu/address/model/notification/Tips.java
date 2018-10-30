package seedu.address.model.notification;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a list of available tips.
 */
//@@Snookerballs
public class Tips {
    private List<Tip> tips;

    /**
     * Creates a Tips object using an existing list {@coee tips}
     * @param tips The list to create a Tips object with
     */
    public Tips(List<Tip> tips) {
        requireAllNonNull(tips);
        this.tips = tips;
    }

    public Tips() {
        this.tips = new ArrayList<>();
    }

    /**
     * Choose a random tip from the list of tips
     * @return a randomly chosen tip
     */
    public Tip getRandomTip() {
        if (tips.size() == 0) {
            return new Tip("No Tips!", "There are no tips!");
        }

        Random rand = new Random();
        int index = rand.nextInt(tips.size() - 1);
        return tips.get(index);
    }

}
