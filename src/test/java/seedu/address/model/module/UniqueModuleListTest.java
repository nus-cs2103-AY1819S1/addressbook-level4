package seedu.address.model.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_CALCULUS;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.ST2131;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.testutil.ModuleBuilder;

public class UniqueModuleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList(new ArrayList<>());

    @Test
    public void contains_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.contains(null);
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(CS2100));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueModuleList.add(CS2100);
        assertTrue(uniqueModuleList.contains(CS2100));
    }

    @Test
    public void contains_moduleWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModuleList.add(CS2100);
        Module editedCS2100 = new ModuleBuilder(CS2100).withTags(VALID_TAG_CALCULUS)
                .build();
        assertTrue(uniqueModuleList.contains(editedCS2100));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.add(null);
    }

    @Test
    public void add_duplicateModule_throwsDuplicateModuleException() {
        uniqueModuleList.add(CS2100);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.add(CS2100);
    }

    @Test
    public void setModule_nullTargetModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModule(null, CS2100);
    }

    @Test
    public void setModule_nullEditedModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModule(CS2100, null);
    }

    @Test
    public void setModule_targetModuleNotInList_throwsModuleNotFoundException() {
        thrown.expect(ModuleNotFoundException.class);
        uniqueModuleList.setModule(CS2100, CS2100);
    }

    @Test
    public void setModule_editedModuleIsSameModule_success() {
        uniqueModuleList.add(CS2100);
        uniqueModuleList.setModule(CS2100, CS2100);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList(new ArrayList<>());
        expectedUniqueModuleList.add(CS2100);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasSameIdentity_success() {
        uniqueModuleList.add(CS2100);
        Module editedCS2100 = new ModuleBuilder(CS2100).withModuleTitle(VALID_MODULETITLE_ST2131)
                .withTags(VALID_TAG_CALCULUS)
                .build();
        uniqueModuleList.setModule(CS2100, editedCS2100);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList(new ArrayList<>());
        expectedUniqueModuleList.add(editedCS2100);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasDifferentIdentity_success() {
        uniqueModuleList.add(CS2100);
        uniqueModuleList.setModule(CS2100, ST2131);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList(new ArrayList<>());
        expectedUniqueModuleList.add(ST2131);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasNonUniqueIdentity_throwsDuplicateModuleException() {
        uniqueModuleList.add(CS2100);
        uniqueModuleList.add(ST2131);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.setModule(CS2100, ST2131);
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.remove(null);
    }

    @Test
    public void remove_moduleDoesNotExist_throwsModuleNotFoundException() {
        thrown.expect(ModuleNotFoundException.class);
        uniqueModuleList.remove(CS2100);
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueModuleList.add(CS2100);
        uniqueModuleList.remove(CS2100);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList(new ArrayList<>());
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullUniqueModuleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModules((UniqueModuleList) null);
    }

    @Test
    public void setModules_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(CS2100);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList(new ArrayList<>());
        expectedUniqueModuleList.add(ST2131);
        uniqueModuleList.setModules(expectedUniqueModuleList);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueModuleList.setModules((List<Module>) null);
    }

    @Test
    public void setModules_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(CS2100);
        List<Module> moduleList = Collections.singletonList(ST2131);
        uniqueModuleList.setModules(moduleList);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList(new ArrayList<>());
        expectedUniqueModuleList.add(ST2131);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_listWithDuplicateModules_throwsDuplicateModuleException() {
        List<Module> listWithDuplicateModules = Arrays.asList(CS2100, CS2100);
        thrown.expect(DuplicateModuleException.class);
        uniqueModuleList.setModules(listWithDuplicateModules);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueModuleList.asUnmodifiableObservableList().remove(0);
    }
}
