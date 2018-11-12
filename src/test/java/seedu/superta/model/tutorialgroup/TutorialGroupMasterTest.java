package seedu.superta.model.tutorialgroup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// @@author Caephler

public class TutorialGroupMasterTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TutorialGroupMaster master = new TutorialGroupMaster();

    @Test
    public void constructorWithHashMap_success() {
        HashMap<String, TutorialGroup> data = new HashMap<>();
        TutorialGroup tg = getModelTutorialGroup();
        data.put(tg.getId(), tg);

        TutorialGroupMaster tgMaster = new TutorialGroupMaster(data);
        assertTrue(tgMaster.contains(tg.getId()));
    }

    @Test
    public void duplicateTutorialGroupId_appendRandom() {
        master.addTutorialGroup(getModelTutorialGroup());
        master.addTutorialGroup(getModelTutorialGroup());
        assertTrue(master.tutorialGroups.size() == 2);
    }

    @Test
    public void getTutorialGroup_success() {
        TutorialGroup tg = getModelTutorialGroup();
        master.addTutorialGroup(tg);
        assertTrue(master.getTutorialGroup(tg.getId()).isPresent());
    }

    @Test
    public void getTutorialGroup_failure() {
        assertFalse(master.getTutorialGroup(getModelTutorialGroup().getId()).isPresent());
    }

    @Test
    public void generateUidsNoDupes_shouldReturnSame() {
        TutorialGroup tg = getModelTutorialGroup();
        assertFalse(master.contains(tg.getId()));
        assertTrue(master.generateUid(tg.getId()).equals(tg.getId()));
    }

    @Test
    public void generateUidsDupe_shouldReturnDifferent() {
        TutorialGroup tg = getModelTutorialGroup();
        master.addTutorialGroup(tg);
        assertTrue(master.contains(tg.getId()));
        assertFalse(master.generateUid(tg.getId()).equals(tg.getId()));
    }



    private TutorialGroup getModelTutorialGroup() {
        return new TutorialGroup("test_id", "test_name");
    }
}
