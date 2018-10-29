package seedu.lostandfound.model.util;

/**
 * Singleton sequence
 */
public class Sequence {
    private static Sequence theOne;
    private Integer next;

    private Sequence() {
        next = 0;
    }

    public static Sequence getInstance() {
        if (theOne == null) {
            theOne = new Sequence();
        }
        return theOne;
    }

    public Integer next() {
        return next++;
    }

    public void set(Integer x) {
        next = Math.max(next, x + 1);
    }
}
