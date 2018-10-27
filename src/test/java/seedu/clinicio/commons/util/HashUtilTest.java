package seedu.clinicio.commons.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

//@@author jjlee050
public class HashUtilTest {

    @Test
    public void hashToString() {
        //Null password
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.hashToString(null));

        assertNotNull(HashUtil.hashToString("123"));
    }

    @Test
    public void verifyPassword() {

        //Null password and/or hashed password
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.verifyPassword(null, null));
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.verifyPassword(null, "1231"));
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.verifyPassword("131", null));

    }
}
