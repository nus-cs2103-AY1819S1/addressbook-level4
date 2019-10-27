package seedu.address.model.awareness;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.isEmptyString;
import static seedu.address.commons.util.StringUtil.isNotPaddedByWhiteSpace;
import static seedu.address.commons.util.StringUtil.isOneWord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;

/** This class manages mappings between slang and full phrases */
public class Dictionary {

    public static final String MESSAGE_SLANG_CONSTRAINTS = "Slang cannot be empty, only whitespace, more than 1 word"
                                                           + " or a duplicate of existing slang.";

    public static final String MESSAGE_FULLPHRASE_CONSTRAINTS = "Full phrases cannot be empty, or only whitespace";

    private static final String SPACE = " ";

    private final Map<String, String> mappings;
    private final TreeSet<String> allFullPhrases;

    /** Create an empty Dictionary (with no mappings). */
    public Dictionary() {
        mappings = new HashMap<String, String>();
        allFullPhrases = new TreeSet<String>();
    }

    /**
     * Registers a new slang - fullPhrase mapping into the Dictionary.
     * @param slang
     * @param fullPhrase
     * @throws IllegalValueException if the given slang or full phrase is invalid.
     */
    public void registerMapping(String slang, String fullPhrase) throws IllegalValueException {

        requireAllNonNull(slang, fullPhrase);

        // pre-processing: remove trailing whitespaces.
        String cleanedSlang = clean(slang);
        String cleanedFullPhrase = clean(fullPhrase);

        if (!isValidSlang(cleanedSlang)) {
            throw new IllegalValueException(MESSAGE_SLANG_CONSTRAINTS);
        }

        if (!isValidFullPhrase(cleanedFullPhrase)) {
            throw new IllegalValueException(MESSAGE_FULLPHRASE_CONSTRAINTS);
        }

        registerFullPhrase(fullPhrase);
        mappings.put(cleanedSlang, cleanedFullPhrase);
    }

    /**
     * Registers multiple slang with a single full phrase.
     *
     * @param slangSet the string array containing all slang to register.
     * @param fullPhrase
     * @throws IllegalValueException if the full phrase, or any one of the slang is invalid.
     */
    public void registerMultipleMapping(String[] slangSet, String fullPhrase) throws IllegalValueException {
        requireAllNonNull(slangSet, fullPhrase);

        for (String eachSlang : slangSet) {
            registerMapping(eachSlang, fullPhrase);
        }
    }

    /**
     * Registers a new full phrase in the set of full phrases tracked in the Dictionary.
     * Each space delimited token in the full phrase is tracked individually.
     *
     * @param fullPhrase
     * @throws IllegalValueException
     */
    public void registerFullPhrase(String fullPhrase) throws IllegalValueException {

        requireNonNull(fullPhrase);
        String cleanedFullPhrase = clean(fullPhrase);

        if (!isValidFullPhrase(cleanedFullPhrase)) {
            throw new IllegalValueException(MESSAGE_FULLPHRASE_CONSTRAINTS);
        }

        // any tokens of this full phrase which are already tracked, are not added again.
        Arrays.stream(tokenize(cleanedFullPhrase))
              .filter(token -> !isEmptyString(token))
              .filter(token-> !allFullPhrases.contains(token))
              .forEach(allFullPhrases::add);

    }


    /**
     * Given an expression, returns a possible Event name. An Event with this name may not actually exist.
     * For example, given the expression "cs", "computer science" is returned as an Event name.
     * However, "computer science" may not be an actual Event.
     *
     * Similarly, given the expression "sg cs innov", "singapore computer science innoventure workshop" may be returned.
     * This may be an actual Event.
     *
     * @param expression an expression is a String containing a combination of slang, partial phrases and full phrases.
     * @return the name of a possible Event, derived using the given expression and user defined contextual
     * information. If NO possible Event name is found, the original expression is returned.
     */
    public String getPossibleEventName(String expression) {
        requireNonNull(expression);

        return Arrays.stream(tokenize(expression))
                     .filter(token -> !isEmptyString(token))
                     .map(slang -> getFullPhraseFromSlang(slang))
                     .map(partialPhrase -> getFullPhraseFromPartialPhrase(partialPhrase))
                     .collect(Collectors.joining(SPACE));
    }

    /**
     * Returns a full phrase that corresponds to a particular slang, or the slang itself if no matching full words are
     * found.
     *
     * @param slang a string representing a slang (e.g. "compsci" or "cs"). Slang includes acronyms.
     * @return the full phrase corresponding to the given slang, or the slang itself if no corresponding full phrases
     *         exist.
     */
    private String getFullPhraseFromSlang(String slang) {
        return Optional.ofNullable(mappings.get(slang))
                       .orElse(slang);
    }

    /**
     * Returns a full phrase that starts the with given partial phrase.
     * For example, if the partial phrase is "comp", it may match with "computer science".
     * If multiple matching full phrases are found, the lexicographically smallest full phrase is returned.
     * If no matching full phrases are found, the partial phrase itself is returned.
     *
     * @param partialPhrase a string representing an incomplete full phrase.
     * @return the name of the lexicographically smallest matching full phrase.
     */
    private String getFullPhraseFromPartialPhrase(String partialPhrase) {
        return Optional.ofNullable(allFullPhrases.ceiling(partialPhrase))
                       .filter(possibleName -> possibleName.startsWith(partialPhrase))
                       .orElse(partialPhrase);
    }

    /**
     * Returns true iff the given string can be considered a valid slang in this Dictionary.
     * To be a valid slang, the given string must obey the rules below:
     *   - Must not be whitespace.
     *   - Must not be an empty string.
     *   - Must not be already present in any mapping contained in this Dictionary
     *   - Must not be more than ONE WORD
     *
     * @param slang the string to be validated.
     * @return true iff the given string is considered a valid slang in this Dictionary.
     */
    private boolean isValidSlang(String slang) {
        assert isNotPaddedByWhiteSpace(slang);

        return !isEmptyString(slang) && !mappings.containsKey(slang)
                                     && isOneWord(slang);

    }

    /**
     * Returns true iff the given string is a valid full phrase in this Dictionary.
     * To be a valid full phrase, the given string must be an empty string.
     *
     * @param fullPhrase the string to be validated.
     * @return true iff the given string is considered a valid full phrase in this Dictionary.
     */
    private boolean isValidFullPhrase(String fullPhrase) {
        assert isNotPaddedByWhiteSpace(fullPhrase);
        return !isEmptyString(fullPhrase);
    }

    private String[] tokenize(String expression) {
        return expression.split(SPACE);
    }

    private String clean(String s) {
        return s.trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Dictionary)) { // instanceof handles nulls
            return false;
        }

        Dictionary otherDictionary = (Dictionary) other;

        return mappings.equals(otherDictionary.mappings)
                       && allFullPhrases.equals(otherDictionary.allFullPhrases);

    }

}
