package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.word.UniqueWordList;
import seedu.address.model.word.Word;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameWord comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueWordList vocab;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        vocab = new UniqueWordList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the word list with {@code words}.
     * {@code words} must not contain duplicate words.
     */
    public void setVocab(List<Word> words) {
        this.vocab.setWords(words);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setVocab(newData.getPersonList());
    }

    //// word-level operations

    /**
     * Returns true if a word with the same identity as {@code word} exists in the address book.
     */
    public boolean hasPerson(Word word) {
        requireNonNull(word);
        return vocab.contains(word);
    }

    /**
     * Adds a word to the address book.
     * The word must not already exist in the address book.
     */
    public void addWord(Word p) {
        vocab.add(p);
    }

    /**
     * Replaces the given word {@code target} in the list with {@code editedWord}.
     * {@code target} must exist in the address book.
     * The word identity of {@code editedWord} must not be the same as another existing word in the address book.
     */
    public void updateWord(Word target, Word editedWord) {
        requireNonNull(editedWord);

        vocab.setWord(target, editedWord);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeWord(Word key) {
        vocab.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return vocab.asUnmodifiableObservableList().size() + " vocab";
        // TODO: refine later
    }

    @Override
    public ObservableList<Word> getPersonList() {
        return vocab.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && vocab.equals(((AddressBook) other).vocab));
    }

    @Override
    public int hashCode() {
        return vocab.hashCode();
    }
}
