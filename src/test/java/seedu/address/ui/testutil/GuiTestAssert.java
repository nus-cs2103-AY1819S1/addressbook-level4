package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CarparkCardHandle;
import guitests.guihandles.CarparkListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.carpark.Carpark;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(CarparkCardHandle expectedCard, CarparkCardHandle actualCard) {
        assertEquals(expectedCard.getCarparkNumber(), actualCard.getCarparkNumber());
        assertEquals(expectedCard.getCarparkType(), actualCard.getCarparkType());
        assertEquals(expectedCard.getCoordinate(), actualCard.getCoordinate());
        assertEquals(expectedCard.getFreeParking(), actualCard.getFreeParking());
        assertEquals(expectedCard.getLotsAvailable(), actualCard.getLotsAvailable());
        assertEquals(expectedCard.getNightParking(), actualCard.getNightParking());
        assertEquals(expectedCard.getShortTerm(), actualCard.getShortTerm());
        assertEquals(expectedCard.getTotalLots(), actualCard.getTotalLots());
        assertEquals(expectedCard.getTypeOfParking(), actualCard.getTypeOfParking());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysCarpark(Carpark expectedCarpark, CarparkCardHandle actualCard) {
        assertEquals(expectedCarpark.getCarparkNumber().value, actualCard.getCarparkNumber());
        assertEquals(expectedCarpark.getCarparkType().value, actualCard.getCarparkType());
        assertEquals(expectedCarpark.getCoordinate().value, actualCard.getCoordinate());
        assertEquals(expectedCarpark.getFreeParking().value, actualCard.getFreeParking());
        assertEquals(expectedCarpark.getLotsAvailable().value, actualCard.getLotsAvailable());
        assertEquals(expectedCarpark.getNightParking().value, actualCard.getNightParking());
        assertEquals(expectedCarpark.getShortTerm().value, actualCard.getShortTerm());
        assertEquals(expectedCarpark.getTotalLots().value, actualCard.getTotalLots());
        assertEquals(expectedCarpark.getTypeOfParking().value, actualCard.getTypeOfParking());
        assertEquals(expectedCarpark.getAddress().value, actualCard.getAddress());
        assertEquals(expectedCarpark.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CarparkListPanelHandle personListPanelHandle, Carpark... carparks) {
        for (int i = 0; i < carparks.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysCarpark(carparks[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CarparkListPanelHandle personListPanelHandle, List<Carpark> carparks) {
        assertListMatching(personListPanelHandle, carparks.toArray(new Carpark[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CarparkListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
