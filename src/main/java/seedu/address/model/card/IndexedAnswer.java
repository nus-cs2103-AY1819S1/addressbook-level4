package seedu.address.model.card;

/**
 * An answer that is identified by an id.
 */
public class IndexedAnswer extends Answer {
    private final int id;

    public IndexedAnswer(Answer answer, int id) {
        super(answer.value);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof IndexedAnswer)) {
            return false;
        }

        IndexedAnswer o = (IndexedAnswer) other;
        return id == o.id;
    }
}
