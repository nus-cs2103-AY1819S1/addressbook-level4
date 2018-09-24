package seedu.address.model.tag;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class LabelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Label(null));
    }

    @Test
    public void constructor_invalidLabelName_throwsIllegalArgumentException() {
        String invalidLabelName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Label(invalidLabelName));
    }

    @Test
    public void isValidLabelName() {
        // null label name
        Assert.assertThrows(NullPointerException.class, () -> Label.isValidLabelName(null));
    }

}
