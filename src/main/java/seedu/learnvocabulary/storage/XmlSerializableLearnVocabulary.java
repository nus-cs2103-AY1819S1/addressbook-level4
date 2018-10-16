package seedu.learnvocabulary.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.learnvocabulary.commons.exceptions.IllegalValueException;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.word.Word;

/**
 * An Immutable LearnVocabulary that is serializable to XML format
 */
@XmlRootElement(name = "learnvocabulary")
public class XmlSerializableLearnVocabulary {

    public static final String MESSAGE_DUPLICATE_WORD = "LearnVocabulary contains duplicate word(s).";

    @XmlElement
    private List<XmlAdaptedWord> words;

    /**
     * Creates an empty XmlSerializableLearnVocabulary.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableLearnVocabulary() {
        words = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableLearnVocabulary(ReadOnlyLearnVocabulary src) {
        this();
        words.addAll(src.getWordList().stream().map(XmlAdaptedWord::new).collect(Collectors.toList()));
    }

    /**
     * Converts this LearnVocabulary into the model's {@code LearnVocabulary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedWord}.
     */
    public LearnVocabulary toModelType() throws IllegalValueException {
        LearnVocabulary learnVocabulary = new LearnVocabulary();
        for (XmlAdaptedWord p : words) {
            Word word = p.toModelType();
            if (learnVocabulary.hasWord(word)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WORD);
            }
            learnVocabulary.addWord(word);
        }
        return learnVocabulary;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableLearnVocabulary)) {
            return false;
        }
        return words.equals(((XmlSerializableLearnVocabulary) other).words);
    }
}
