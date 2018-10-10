package seedu.address.model.appointment;

import java.util.ArrayList;

public class TypeFields {
    public ArrayList<String> typeFields() {
        Type[] types = Type.values();
        ArrayList<String> typesList = new ArrayList<>();
        for (Type type : types) {
            typesList.add(type.getAbbreviation());
        }
        return typesList;
    }
}
