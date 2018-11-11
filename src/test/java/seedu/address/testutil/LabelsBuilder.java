package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.tag.Label;

/**
 * Small util class that helps to build Labels
 */
public class LabelsBuilder {

    /**
     * Builds a Set of Labels from a variable number of Strings provided
     *
     * @param keyWords A variable number of key words to create a set of labels from.
     * @return a Set of Labels
     */
    public static Set<Label> createLabelsFromKeywords(String... keyWords) {
        requireNonNull(keyWords);

        return Set.of(keyWords)
            .parallelStream()
            .map(keyword -> new Label(keyword))
            .collect(Collectors.toSet());
    }
}
