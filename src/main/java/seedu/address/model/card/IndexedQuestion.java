package seedu.address.model.card;

/**
 * A question that is identified by an id.
 */
public class IndexedQuestion extends Question {
    private final int id;

    public IndexedQuestion(Question question, int id) {
        super(question.value);
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

        if (!(other instanceof IndexedQuestion)) {
            return false;
        }

        IndexedQuestion o = (IndexedQuestion) other;
        return id == o.id;
    }
}
