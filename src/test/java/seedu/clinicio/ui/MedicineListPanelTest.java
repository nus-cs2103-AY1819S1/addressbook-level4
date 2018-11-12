package seedu.clinicio.ui;

//@@author aaronseahyh

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import static seedu.clinicio.testutil.EventsUtil.postNow;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalMedicines;

import static seedu.clinicio.ui.testutil.GuiTestAssert.assertCardDisplaysMedicine;
import static seedu.clinicio.ui.testutil.GuiTestAssert.assertMedicineCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.MedicineCardHandle;
import guitests.guihandles.MedicineListPanelHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.clinicio.commons.events.ui.JumpToListRequestEvent;
import seedu.clinicio.commons.util.FileUtil;
import seedu.clinicio.commons.util.XmlUtil;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.storage.XmlSerializableClinicIo;

public class MedicineListPanelTest extends GuiUnitTest {

    private static final ObservableList<Medicine> TYPICAL_MEDICINES =
            FXCollections.observableList(getTypicalMedicines());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(
            INDEX_SECOND_MEDICINE);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private MedicineListPanelHandle medicineListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_MEDICINES);

        for (int i = 0; i < TYPICAL_MEDICINES.size(); i++) {
            medicineListPanelHandle.navigateToCard(TYPICAL_MEDICINES.get(i));
            Medicine expectedMedicine = TYPICAL_MEDICINES.get(i);
            MedicineCardHandle actualCard = medicineListPanelHandle.getMedicineCardHandle(i);

            assertCardDisplaysMedicine(expectedMedicine, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_MEDICINES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        MedicineCardHandle expectedMedicine = medicineListPanelHandle
                .getMedicineCardHandle(INDEX_SECOND_MEDICINE.getZeroBased());
        MedicineCardHandle selectedMedicine = medicineListPanelHandle.getHandleToSelectedCard();
        assertMedicineCardEquals(expectedMedicine, selectedMedicine);
    }

    /**
     * Verifies that creating and deleting large number of medicines in {@code MedicineListPanel} requires less than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Medicine> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of medicine cards exceeded time limit");
    }

    /**
     * Returns a list of medicines containing {@code medicineCount} medicines that is used to populate the
     * {@code MedicineListPanel}.
     */
    private ObservableList<Medicine> createBackingList(int medicineCount) throws Exception {
        Path xmlFile = createXmlFileWithMedicines(medicineCount);
        XmlSerializableClinicIo xmlClinicIo =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableClinicIo.class);
        return FXCollections.observableArrayList(xmlClinicIo.toModelType().getMedicineList());
    }

    /**
     * Returns a .xml file containing {@code medicineCount} medicines.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithMedicines(int medicineCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<clinicio>\n");
        for (int i = 0; i < medicineCount; i++) {
            builder.append("<medicines>\n");
            builder.append("<medicineName>").append(i).append("a</medicineName>\n");
            builder.append("<medicineType>Tablet</medicineType>\n");
            builder.append("<effectiveDosage>2</effectiveDosage>\n");
            builder.append("<lethalDosage>8</lethalDosage>\n");
            builder.append("<price>21.03</price>\n");
            builder.append("<quantity>2103</quantity>\n");
            builder.append("</medicines>\n");
        }
        builder.append("</clinicio>\n");

        Path manyMedicinesFile = TEST_DATA_FOLDER.resolve("manyMedicines.xml");
        FileUtil.createFile(manyMedicinesFile);
        FileUtil.writeToFile(manyMedicinesFile, builder.toString());
        manyMedicinesFile.toFile().deleteOnExit();
        return manyMedicinesFile;
    }

    /**
     * Initializes {@code medicineListPanelHandle} with a {@code MedicineListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code MedicineListPanel}.
     */
    private void initUi(ObservableList<Medicine> backingList) {
        MedicineListPanel medicineListPanel = new MedicineListPanel(backingList);
        uiPartRule.setUiPart(medicineListPanel);

        medicineListPanelHandle = new MedicineListPanelHandle(getChildNode(medicineListPanel.getRoot(),
                MedicineListPanelHandle.MEDICINE_LIST_VIEW_ID));
    }

}
