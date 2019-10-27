package seedu.address.model.template;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.model.category.Category;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;

/**
 * A template which specifies the categories and layout for the resume,
 * and which tags are to be included.
 */
public class Template {

    private static final String DELIMITER = ":";
    private static final String AND_DELIMITER = "&";
    private static final String OR_DELIMITER = " ";
    private static final String TEMPLATE_LINE_REGEX = "^[^:]+:~([^:\\s])+:[^:]*$";

    private ArrayList<TemplateSection> sections;

    // filePath string, purely for display purposes
    private String filePath;
    private String sectionsString;

    public Template(String filePath) {
        this.filePath = filePath;
        sections = new ArrayList<>();
        sectionsString = "";
    }

    public String getFilepath() {
        return filePath;
    }

    public String getSectionsString() {
        return sectionsString;
    }

    /**
     * Returns a default template with no filters
     */
    public static Template getDefaultTemplate() {
        Template t = new Template("default_template");
        try {
            t.addSection("Work Experience" + DELIMITER + "~work" + DELIMITER);
            t.addSection("Education" + DELIMITER + "~education" + DELIMITER);
            t.addSection("Projects" + DELIMITER + "~projects" + DELIMITER);
        } catch (InvalidTemplateFileException e) {
            //Exception will never occur in this case
        }
        return t;
    }

    /**
     * Adds a section to the Template
     */
    public void addSection(String line) throws InvalidTemplateFileException {
        if (!line.matches(TEMPLATE_LINE_REGEX)) {
            throw new InvalidTemplateFileException("Specified file has invalid format.");
        }

        /*
         * Work Experience:~work:nus&java nus&c
         * returns (nus AND java) OR (nus AND c)
         */
        String[] parts = line.split(DELIMITER);

        String title = parts[0];
        String cateName = parts[1].substring(1); // remove ~
        String tagsString = parts.length == 3 ? parts[2].trim() : "";

        Predicate<ResumeEntry> tagPredicate = createTagPredicate(tagsString);
        Predicate<ResumeEntry> catePredicate = createCategoryPredicate(cateName);

        sections.add(new TemplateSection(title, catePredicate, tagPredicate));

        sectionsString += String.format("%s: ~%s\n%s\n", title, cateName,
                tagsString.equals("") ? "" : tagsString + "\n");
    }

    /**
     * Returns a predicate which checks if entry matches tags
     */
    private Predicate<ResumeEntry> createTagPredicate(String tags) {
        if (tags.equals("")) { //no filters
            return entry -> true;
        }

        /*
         * start with sum of products: "nus&java nus&c recent"
         * and split into array of products/tag groups: [nus&java, nus&c, recent]
         */
        String[] tagGroups = tags.split(OR_DELIMITER);

        ArrayList<ArrayList<Tag>> expressions = new ArrayList<>();

        for (int i = 0; i < tagGroups.length; i++) {
            ArrayList<Tag> expression = new ArrayList<>();
            // for each product: [nus&java], split into tags: [nus, java]
            for (String tag : tagGroups[i].split(AND_DELIMITER)) {
                expression.add(new Tag(tag));
            }
            expressions.add(expression);
        }

        return entry -> {
            Set<Tag> entryTags = entry.getTags();
            boolean fitsOneExpression = false;
            for (ArrayList<Tag> expression : expressions) {

                boolean fitsAllInExpression = true;
                for (Tag tag : expression) {

                    if (!entryTags.contains(tag)) {
                        fitsAllInExpression = false;
                        break;
                    }
                }

                if (fitsAllInExpression) {
                    fitsOneExpression = true;
                    break;
                }
            }
            return fitsOneExpression;
        };
    }

    private Predicate<ResumeEntry> createCategoryPredicate(String category) {
        return entry -> entry.getCategory().equals(new Category(category));
    }

    public ArrayList<TemplateSection> getSections() {
        return new ArrayList<>(sections);
    }

    @Override
    public String toString() {
        return filePath + "\n" + sectionsString;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Template)) {
            return false;
        }

        // state check
        Template other = (Template) obj;
        return filePath.equals(other.filePath)
                && sectionsString.equals(other.sectionsString);
    }
}
