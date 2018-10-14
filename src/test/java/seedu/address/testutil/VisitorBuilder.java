package seedu.address.testutil;

import seedu.address.model.visitor.Visitor;

public class VisitorBuilder {
    private static final String DEFAULT_VISITOR = "GAOJIAXIN";

    private String visiotr;

    public VisitorBuilder() {
        visiotr = DEFAULT_VISITOR;
    }

    public VisitorBuilder(Visitor v) {
        visiotr = v.getVisitorName();
    }

    public VisitorBuilder withVisitor(Visitor v) {
        visiotr = v.getVisitorName();
        return this;
    }

    public Visitor build() {
        return new Visitor(visiotr);
    }
}
