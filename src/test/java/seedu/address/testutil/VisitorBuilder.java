package seedu.address.testutil;

import seedu.address.model.visitor.Visitor;

/**
 * builder class for visitor
 */
public class VisitorBuilder {
    private static final String DEFAULT_VISITOR = "GAOJIAXIN";

    private String visiotr;

    public VisitorBuilder() {
        visiotr = DEFAULT_VISITOR;
    }

    /**
     * constructor
     * @param v
     */
    public VisitorBuilder(Visitor v) {
        visiotr = v.getVisitorName();
    }

    /**
     * setter
     * @param v
     */
    public VisitorBuilder withVisitor(Visitor v) {
        visiotr = v.getVisitorName();
        return this;
    }

    public Visitor build() {
        return new Visitor(visiotr);
    }
}
