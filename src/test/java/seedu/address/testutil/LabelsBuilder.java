package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.tag.Label;

public class LabelsBuilder {

    /**
     * @param keywords
     * @return
     */
    public static Set<Label> createLabelsFromKeywords(String... keywords) {
        return Set.of(keywords)
            .parallelStream()
            .map(keyword -> new Label(keyword))
            .collect(Collectors.toSet());
    }
}
