package seedu.address.model.test;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Contains the mapping of all the test.
 */
public class TriviaResults implements ReadOnlyTriviaResults {
    private final List<TriviaResult> triviaResults;
    private final HashMap<Card, List<Attempt>> attemptsOfCards;

    public TriviaResults() {
        triviaResults = new ArrayList<>();
        attemptsOfCards = new HashMap<>();
    }

    /**
     * Creates a TriviaResult using the ReadOnlyTriviaResults in the {@code toBeCopied}
     */
    public TriviaResults(ReadOnlyTriviaResults toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the ResultList with {@code results}.
     * {@code results}.
     */
    public void setTriviaResults(List<TriviaResult> results) {
        triviaResults.clear();
        triviaResults.addAll(results);

        attemptsOfCards.clear();
        for (TriviaResult result : results) {
            mapCardsToAttempts(result.getAttempts());
        }
    }

    /**
     * Resets the existing data of this {@code TriviaBundleList} with {@code newData}.
     */
    public void resetData(ReadOnlyTriviaResults newData) {
        requireNonNull(newData);

        setTriviaResults(newData.getResultList());
    }

    /**
     * Adds a triviaResult into the list of triviaResults
     */
    public void addTriviaResult(TriviaResult triviaResult) {
        requireAllNonNull(triviaResult);

        triviaResults.add(triviaResult);
        mapCardsToAttempts(triviaResult.getAttempts());
    }

    /**
     * Will map cards to attempts for efficient retrieval of attempts by card.
     */
    private void mapCardsToAttempts(List<? extends Attempt> attempts) {
        for (Attempt attempt : attempts) {
            List<Attempt> existingAttemptsByCard = attemptsOfCards.get(attempt.attemptedCard);
            if (existingAttemptsByCard == null) {
                attemptsOfCards.put(attempt.attemptedCard, new ArrayList<>(Arrays.asList(attempt)));
            } else {
                existingAttemptsByCard.add(attempt);
            }
        }
    }

    @Override
    public ObservableList<TriviaResult> getResultList() {
        return FXCollections.unmodifiableObservableList(
                FXCollections.observableArrayList(triviaResults));
    }

    @Override
    public ObservableList<Attempt> getAttemptsByCard(Card card) {
        List<Attempt> attempts = attemptsOfCards.get(card);
        if (attempts == null) {
            return FXCollections.unmodifiableObservableList(
                    FXCollections.observableList(new ArrayList<>()));
        } else {
            return FXCollections.unmodifiableObservableList(
                    FXCollections.observableList(attemptsOfCards.get(card)));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TriviaResults // instanceof handles nulls
                && triviaResults.equals(((TriviaResults) other).triviaResults));
    }

}
