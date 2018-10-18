package seedu.address.model.visitor;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The visitor list to store all the visitors for patient.
 */
public class VisitorList extends ArrayList<Visitor> {
    private ArrayList<Visitor> visitorArrayList;

    public VisitorList() {
        visitorArrayList = new ArrayList<Visitor>();
    }


    public VisitorList(VisitorList visitorList) {
        Objects.requireNonNull(visitorList);
        this.visitorArrayList = new ArrayList<>(Objects.requireNonNull(visitorList.visitorArrayList));
    }

    public VisitorList(ArrayList<Visitor> visitors) {
        Objects.requireNonNull(visitors);
        this.addAll(visitors);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Visitor m : visitorArrayList) {
            sb.append(m.toString()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof VisitorList) {
            VisitorList ml = (VisitorList) o;
            return visitorArrayList.size() == ml.visitorArrayList.size()
                    && visitorArrayList.containsAll(ml.visitorArrayList);
        }

        return false;
    }
}
