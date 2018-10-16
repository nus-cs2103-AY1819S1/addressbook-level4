package seedu.address.model.template;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.entry.Taggable;
import seedu.address.model.tag.Tag;

/**
 * A template which specifies the categories and layout for the resume,
 * and which tags are to be included.
 */
public class Template {

    private static final String DELIMITER = ":";
    private static final String AND_DELIMITER = "&";
    private static final String OR_DELIMITER = " ";

    private ArrayList<TemplateSection> sections;

    public Template() {
        sections = new ArrayList<TemplateSection>();
    }

    /**
     * Returns a default template with no filters
     */
    public static Template getDefaultTemplate() {
        Template t = new Template();
        t.addSection("Work Experience" + DELIMITER + "~work" + DELIMITER);
        t.addSection("Education" + DELIMITER + "~education" + DELIMITER);
        t.addSection("Projects" + DELIMITER + "~projects" + DELIMITER);
        return t;
    }

    /**
     * Adds a section to the Template
     */
    public void addSection(String line) {
        /*
         * Work Experience:~work:#nus&#java #nus&#c
         * returns (nus AND java) OR (nus AND c)
         */
        String[] parts = line.split(DELIMITER);
        String title = parts[0];
        String cateName = parts[1];
        String tags = parts[2];

        Predicate<Taggable> predicate = getPredicate(tags);

        sections.add(new TemplateSection(title, cateName, predicate));
    }

    private Predicate<Taggable> getPredicate(String tags) {
        if (tags.equals("")) { //no filters
            return entry -> true;
        }

        //nus&java nus&c recent
        //split sum of products into array of products:
        //[nus&java, nus&c, recent]
        //TODO: see if can be renamed to be clearer
        String[] products = tags.split(OR_DELIMITER);

        ArrayList<ArrayList<Tag>> expressions = new ArrayList<>();

        for (int i = 0; i < products.length; i++) {
            //nus&java
            ArrayList<Tag> andTags = new ArrayList<>();

            for (String and : products[i].split(AND_DELIMITER)) { //[nus, java]
                andTags.add(new Tag(and));
            }
            expressions.add(andTags);
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
                    fitsOneExpression = true; //found an OR which it fits
                    break;
                }
            }
            return fitsOneExpression;
        };
    }

    public ArrayList<TemplateSection> getSections() {
        return new ArrayList<TemplateSection>(sections);
    }
}
