package seedu.address.storage.budget;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.budget.CategoryBudget;

/**
 * JAXB-friendly version of Category Budget.
 */

public class XmlAdaptedCategoryBudget extends XmlAdaptedBudget {

    @XmlElement(required = true)
    private String category;

    public XmlAdaptedCategoryBudget(CategoryBudget cBudget) {
        super(cBudget);
        this.category = cBudget.getCategory().categoryName;
    }

    @Override
    public CategoryBudget toModelType() {
        return new CategoryBudget(this.category, String.format("%.2f", this.budgetCap));
    }
}
