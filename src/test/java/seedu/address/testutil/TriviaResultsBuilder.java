package seedu.address.testutil;

import seedu.address.model.test.TriviaResult;
import seedu.address.model.test.TriviaResults;

/**
 * A utility class to build a TriviaResults class.
 */
public class TriviaResultsBuilder {
    private TriviaResults triviaResults;

    public TriviaResultsBuilder() {
        triviaResults = new TriviaResults();
    }

    public TriviaResultsBuilder(TriviaResults triviaResults) {
        this.triviaResults = triviaResults;
    }

    /**
     * Adds a new {@code TriviaResult} to the {@code TriviaResults} that we are building.
     */
    public TriviaResultsBuilder withTriviaResult(TriviaResult triviaResult) {
        triviaResults.addTriviaResult(triviaResult);
        return this;
    }

    public TriviaResults build() {
        return triviaResults;
    }
}
