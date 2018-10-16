package seedu.address.model.resume;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.template.Template;
import seedu.address.model.template.TemplateSection;

/**
 * Represents a Resume generated by the app, containing the Template used to generate it
 * as well as ResumeSections comprising ResumeEntries as specified by the Template.
 */
public class Resume {
    // Attributes
    public final String name;

    // Data
    private final Model model;
    private final Template template;
    //private final ResumeHeader resumeHeader;
    private final List<ResumeSection> resumeSectionList;

    public Resume(String name, Model model) {
        requireAllNonNull(name, model);
        this.name = name;
        this.model = model;

        // TODO: Use the actual template loaded in the model
        template = Template.getDefaultTemplate();
        requireAllNonNull(template);

        // TODO: Implement personal info
        //resumeHeader = new ResumeHeader(model.getPersonInfo());
        //requireAllNonNull(resumeHeader);

        resumeSectionList = new ArrayList<>();
        populateSectionList();
        requireAllNonNull(resumeSectionList);
    }

    /**
     * Populates the section list by using each TemplateSection to fetch a set of entries belonging to that section.
     */
    private void populateSectionList() {
        ArrayList<TemplateSection> templateSections = template.getSections();
        for (TemplateSection templateSection : templateSections) {
            resumeSectionList.add(fetchSectionEntries(templateSection));
        }
    }

    /**
     * Fetches a ResumeSection containing entries which match the tags specified in the
     * @param templateSection and
     * @return the ResumeSection containing the entries specified.
     */
    private ResumeSection fetchSectionEntries(TemplateSection templateSection) {
        // TODO: implement ModelManager.getFiltered()
        return new ResumeSection(templateSection.getTitle(), new ArrayList<>());
        //return new ResumeSection(templateSection.getTitle(),
        //        model.getFiltered(templateSection.getCategory(), templateSection.getPredicate()));
    }

    /*public ResumeHeader getHeader() {
        return resumeHeader;
    }*/

    public List<ResumeSection> getSectionList() {
        return resumeSectionList;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Resume)) {
            return false;
        }

        // state check
        Resume other = (Resume) obj;
        return model.equals(other.model)
                && name.equals(other.name);
    }
}
