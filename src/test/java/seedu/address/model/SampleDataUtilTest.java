package seedu.address.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import seedu.address.model.expense.Person;
import seedu.address.model.util.SampleDataUtil;

public class SampleDataUtilTest {
    @Test
    public void testGetSamplePersons() {
        Assert.assertEquals(SampleDataUtil.getSamplePersons().length, 7);
    }

    @Test
    public void testGetSampleAddressBook() {
        ReadOnlyAddressBook book = SampleDataUtil.getSampleAddressBook();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        for (Person p : samplePersons) {
            Assert.assertTrue(book.getPersonList().contains(p));
        }
        Assert.assertEquals(SampleDataUtil.getSamplePersons().length, samplePersons.length);
    }
}
