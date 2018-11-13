package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.wish.Date;
import seedu.address.model.wish.Wish;

/**
 * Compares Wishes using Wish fields.
 */
public class WishComparator implements Comparator<Wish> {

    @Override
    public int compare(Wish o1, Wish o2) {
        assert(Date.isValidDate(o1.getDate().date));
        assert(Date.isValidDate(o1.getDate().date));
        java.util.Date date1 = o1.getDate().getDateObject();
        java.util.Date date2 = o2.getDate().getDateObject();
        int nameComparison = o1.getName().fullName.compareTo(o2.getName().fullName);

        if (date1.after(date2)) {
            return 1;
        } else if (date1.before(date2)) {
            return -1;
        } else if (nameComparison == 1) {
            return 1;
        } else if (nameComparison == -1) {
            return -1;
        } else {
            return o1.getId().compareTo(o2.getId());
        }
    }


    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
