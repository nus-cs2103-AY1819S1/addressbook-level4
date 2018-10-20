package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.wish.Date;
import seedu.address.model.wish.Wish;

public class WishComparator implements Comparator<Wish> {

    @Override
    public int compare(Wish o1, Wish o2) {
        assert(Date.isValidDate(o1.getDate().date));
        assert(Date.isValidDate(o1.getDate().date));
        java.util.Date date1 = o1.getDate().getDateObject();
        java.util.Date date2 = o2.getDate().getDateObject();

        if (date1.after(date2)) {
           return 1;
        } else if (date1.before(date2)) {
           return -1;
        } else if (o1.getPrice().value > o2.getPrice().value) {
           return 1;
        } else if (o1.getPrice().value < o2.getPrice().value) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
