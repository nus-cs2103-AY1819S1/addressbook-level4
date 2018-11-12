package seedu.modsuni.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.modsuni.testutil.EventsUtil.postNow;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModules;
import static seedu.modsuni.ui.testutil.GuiTestAssert.assertCardDisplaysModule;
import static seedu.modsuni.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.ModuleCardHandle;
import guitests.guihandles.StagedModuleListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.modsuni.commons.events.ui.JumpToListRequestEvent;
import seedu.modsuni.commons.util.FileUtil;
import seedu.modsuni.commons.util.XmlUtil;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.storage.XmlSerializableModuleList;

public class StagedModuleListPanelTest extends GuiUnitTest {
    private static final ObservableList<Module> TYPICAL_MODULES =
            FXCollections.observableList(getTypicalModules());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_MODULE);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private StagedModuleListPanelHandle stagedModuleListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_MODULES);

        for (int i = 0; i < TYPICAL_MODULES.size(); i++) {
            stagedModuleListPanelHandle.navigateToCard(TYPICAL_MODULES.get(i));
            Module expectedModule = TYPICAL_MODULES.get(i);
            ModuleCardHandle actualCard = stagedModuleListPanelHandle.getModuleCardHandle(i);

            assertCardDisplaysModule(expectedModule, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_MODULES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ModuleCardHandle expectedModule = stagedModuleListPanelHandle
                .getModuleCardHandle(INDEX_SECOND_MODULE.getZeroBased());
        ModuleCardHandle selectedModule = stagedModuleListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedModule, selectedModule);
    }

    /**
     * Verifies that creating and deleting large number of modules in {@code StagedModuleListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Module> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of module cards exceeded time limit");
    }

    /**
     * Returns a list of modules containing {@code moduleCount} modules that is used to populate the
     * {@code StagedModuleListPanel}.
     */
    private ObservableList<Module> createBackingList(int moduleCount) throws Exception {
        Path xmlFile = createXmlFileWithModules(moduleCount);
        XmlSerializableModuleList xmlSerializableModuleList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableModuleList.class);
        return FXCollections.observableArrayList(xmlSerializableModuleList.toModelType().getModuleList());
    }

    /**
     * Returns a .xml file containing {@code moduleCount} modules. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithModules(int moduleCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<ModuleList>\n");
        for (int i = 0; i < moduleCount; i++) {
            builder.append("<modules>\n");
            builder.append("<code>").append(i).append("a</code>\n");
            builder.append("<description>aaa</description>\n");
            builder.append("<title>a</title>\n");
            builder.append("<parsedPrereq><or><code>aaa</code></or></parsedPrereq>\n");
            builder.append("<credit>4</credit>\n");
            builder.append("<department>aaa</department>\n");
            builder.append("<isAvailableInSem1>true</isAvailableInSem1>\n");
            builder.append("<isAvailableInSem2>true</isAvailableInSem2>\n");
            builder.append("<isAvailableInSpecialTerm1>true</isAvailableInSpecialTerm1>\n");
            builder.append("<isAvailableInSpecialTerm2>true</isAvailableInSpecialTerm2>\n");
            builder.append("<modsuni>a</modsuni>\n");
            builder.append("</modules>\n");
        }
        builder.append("</ModuleList>\n");

        Path manyModulesFile = Paths.get(TEST_DATA_FOLDER + "manyModules.xml");
        FileUtil.createFile(manyModulesFile);
        FileUtil.writeToFile(manyModulesFile, builder.toString());
        manyModulesFile.toFile().deleteOnExit();
        return manyModulesFile;
    }

    /**
     * Initializes {@code stagedModuleListPanelHandle} with a {@code stagedModuleListPanel}
     * backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code stagedModuleListPanel}.
     */
    private void initUi(ObservableList<Module> backingList) {
        StagedModuleListPanel stagedModuleListPanel = new StagedModuleListPanel(backingList);
        uiPartRule.setUiPart(stagedModuleListPanel);

        stagedModuleListPanelHandle = new StagedModuleListPanelHandle(getChildNode(stagedModuleListPanel.getRoot(),
                StagedModuleListPanelHandle.MODULE_LIST_VIEW_ID));
    }
}
