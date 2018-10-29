package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.test.ReadOnlyTriviaResults;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.test.TriviaResults;

/**
 * An Immutable TriviaResults that is serializable to XML format
 */
@XmlRootElement(name = "testresult")
public class XmlSerializableTriviaResults {
    @XmlElement
    private List<XmlAdaptedTriviaResult> results;

    /**
     * Creates an empty XmlSerializableTriviaBundle.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTriviaResults() {
        results = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTriviaResults(ReadOnlyTriviaResults src) {
        this();
        results.addAll(src.getResultList().stream()
                .map(XmlAdaptedTriviaResult::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this triviabundle into the model's {@code TriviaBundle} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCard}.
     */
    public TriviaResults toModelType() throws IllegalValueException {
        TriviaResults triviaResults = new TriviaResults();
        for (XmlAdaptedTriviaResult r : results) {
            TriviaResult result = r.toModelType();
            triviaResults.addTriviaResult(result);
        }
        return triviaResults;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTriviaResults)) {
            return false;
        }
        return results.equals(((XmlSerializableTriviaResults) other).results);
    }
}
