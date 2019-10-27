package seedu.address.model.category;

import static org.junit.Assert.assertTrue;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
import static seedu.address.testutil.TypicalEntrys.NUS_EDUCATION;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.entry.ResumeEntry;

public class CategoryManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CategoryManager categoryManager = new CategoryManager();

    @Test
    public void mkPredicate_working() {
        ResumeEntry[] entries = { NUS_EDUCATION, WORK_FACEBOOK };
        categoryManager.setList(Arrays.asList(entries));

        // single category
        categoryManager.setPredicate(categoryManager.mkPredicate("work"));
        assertTrue(categoryManager.getList().size() == 1);

        // multiple category, only get first one
        String[] categories = {"nothing", "work"};
        List<String> categoriesList = Arrays.asList(categories);

        categoryManager.setPredicate(categoryManager.mkPredicate(categoriesList));
        assertTrue(categoryManager.getList().size() == 0);

        // add on to multiple tags
        categoryManager.setPredicate(categoryManager.mkPredicate(PREDICATE_SHOW_ALL_ENTRIES, Arrays.asList("work")));
        assertTrue(categoryManager.getList().size() == 1);

        // add on to existing predicate
        categoryManager.setPredicate(categoryManager.mkPredicate((ResumeEntry entry) -> false, "work"));
        assertTrue(categoryManager.getList().size() == 0);
    }
}
