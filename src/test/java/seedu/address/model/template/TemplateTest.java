package seedu.address.model.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.util.EntryBuilder;
import seedu.address.testutil.Assert;

public class TemplateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addSection_validFormat_success() throws Exception {
        Template t = new Template("dummy.txt");

        // note that multiple categories with the same title/category tag are allowed

        // valid, with tag
        t.addSection("Work:~work:java");
        t.addSection("Work:~work: java");

        // valid, no tag
        t.addSection("Work:~work:");
        t.addSection("Work:~work: ");

        // valid, spaces in title
        t.addSection("Work Experience:~work:");

        // valid, non-alphanumeric characters in title
        t.addSection("Work Experience (Jobs, Internships):~work:");

        // valid, multiple tags groups
        t.addSection("Work Experience:~work:java python");

        // valid, multiple tag groups with multiple tags
        t.addSection("Work Experience:~work:java&web&recent python&web ai");
    }

    @Test
    public void addSection_invalidFormat_throwsInvalidTemplateFileException() throws Exception {
        Template t = new Template("dummy.txt");

        // invalid, missing section
        Assert.assertThrows(InvalidTemplateFileException.class, () -> t.addSection("~work:java python"));
        Assert.assertThrows(InvalidTemplateFileException.class, () -> t.addSection("Work Experience:java python"));
        Assert.assertThrows(InvalidTemplateFileException.class, () -> t.addSection("Work Experience:~work"));

        // invalid, missing tilde
        Assert.assertThrows(InvalidTemplateFileException.class, () -> t.addSection("Work Experience:work:java"));
        Assert.assertThrows(InvalidTemplateFileException.class, () -> t.addSection("Work Experience:work:"));

        // invalid, space in category tag
        Assert.assertThrows(InvalidTemplateFileException.class, () ->
                t.addSection("Work Experience:~work exp:java"));
        Assert.assertThrows(InvalidTemplateFileException.class, () ->
                t.addSection("Work Experience: ~workexp:java"));

        // invalid, extra colons
        Assert.assertThrows(InvalidTemplateFileException.class, () ->
                t.addSection("Work Experience:~work:java:python"));
        Assert.assertThrows(InvalidTemplateFileException.class, () ->
                t.addSection("Work Experience::~work:java python"));
        Assert.assertThrows(InvalidTemplateFileException.class, () ->
                t.addSection("Work Experience:work::java python"));
        Assert.assertThrows(InvalidTemplateFileException.class, () ->
                t.addSection(":Work Experience:work:java python"));

    }

    @Test
    public void addSection() throws InvalidTemplateFileException {
        Template defaultTemplate = Template.getDefaultTemplate();
        Template t = new Template("default_template");
        t.addSection("Work Experience:~work:");
        t.addSection("Education:~education:");
        t.addSection("Projects:~projects:");
        assertEquals(defaultTemplate, t);
    }

    @Test
    public void predicateTest() throws InvalidTemplateFileException {
        Template t = new Template("dummy.txt");
        t.addSection("Education:~education:uni training course");
        t.addSection("Education:~education:");
        t.addSection("Education:~education:course&recent");

        ArrayList<TemplateSection> sections = t.getSections();
        Predicate<ResumeEntry> categoryPredicateEducation = sections.get(0).getCategoryPredicate();
        Predicate<ResumeEntry> tagPredicateUniOrTrainingOrCourse = sections.get(0).getTagPredicate();
        Predicate<ResumeEntry> tagPredicateAll = sections.get(1).getTagPredicate();
        Predicate<ResumeEntry> tagPrediateCourseAndRecent = sections.get(2).getTagPredicate();

        ResumeEntry nusEntry = new EntryBuilder().withCategory("education")
                .withTitle("National University of Singapore").withDuration("2010 - 2014")
                .withSubHeader("Bachelor of Computing")
                .withTags("uni").build();

        ResumeEntry courseEntry = new EntryBuilder().withCategory("education")
                .withTitle("Data Science Workshop").withDuration("2013")
                .withSubHeader("Data Science with Python")
                .withTags("course").build();

        ResumeEntry recentCourseEntry = new EntryBuilder().withCategory("education")
                .withTitle("Machine Learning Workshop").withDuration("2017")
                .withSubHeader("Introduction to Machine Learning")
                .withTags("course", "recent").build();

        ResumeEntry workEntry = new EntryBuilder().withCategory("work")
                .withTitle("Google").withDuration("2017")
                .withSubHeader("Internship and Training Program")
                .withTags("TRAINING").build();

        // fits category
        assertTrue(categoryPredicateEducation.test(nusEntry));

        // does not fit category
        assertFalse(categoryPredicateEducation.test(workEntry));

        // fits one of multiple tag groups with single tags
        assertTrue(tagPredicateUniOrTrainingOrCourse.test(nusEntry));
        assertTrue(tagPredicateUniOrTrainingOrCourse.test(courseEntry));

        // fits no filter
        assertTrue(tagPredicateAll.test(nusEntry));

        // fits all tags in a tag group
        assertTrue(tagPrediateCourseAndRecent.test(recentCourseEntry));

        // does not fit all tags in a tag group, only one
        assertFalse(tagPrediateCourseAndRecent.test(courseEntry));

        // does not fit tag as predicate is case sensitive
        assertFalse(tagPredicateUniOrTrainingOrCourse.test(workEntry));
    }
}
