package seedu.address.model.logging;

import java.util.List;

public interface HtmlFormattable {
    List<String> getFieldHeaders();
    List<String> getFields();
}
