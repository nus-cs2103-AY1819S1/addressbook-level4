package seedu.address.model.logging;

import java.util.List;

/**
 * Interface that specifies what a HtmlFormattable object for Html pages should support.
 */
public interface HtmlFormattable {
    List<String> getFieldHeaders();
    List<String> getFields();
}
