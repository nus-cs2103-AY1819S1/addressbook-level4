package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Anakin} that keeps track of its own history.
 */
public class Anakin_VersionedAnakin extends Anakin {

    private final List<Anakin_ReadOnlyAnakin> anakinStateList;
    private int currentStatePointer;

    public Anakin_VersionedAnakin(Anakin_ReadOnlyAnakin initialState) {
        super(initialState);

        anakinStateList = new ArrayList<>();
        anakinStateList.add(new Anakin(initialState));
        currentStatePointer = 0;
    }
}
