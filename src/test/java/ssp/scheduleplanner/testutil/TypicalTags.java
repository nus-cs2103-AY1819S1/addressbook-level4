package ssp.scheduleplanner.testutil;

import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_TAG_CS;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_TAG_ST;

import ssp.scheduleplanner.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {
    public static final Tag CS = new Tag(VALID_TAG_CS);
    public static final Tag ST = new Tag(VALID_TAG_ST);
}
