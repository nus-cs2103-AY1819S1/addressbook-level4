package seedu.lostandfound.model.util;

public class Sequence {
    private int nextId;

    public Sequence() {
        nextId = 0;
    }

    public Sequence(int start) {
        nextId = start;
    }

    public int next() {
        return nextId++;
    }

    public void set(int x) {
        if (nextId > x) return;
        nextId = x + 1;
    }
}
