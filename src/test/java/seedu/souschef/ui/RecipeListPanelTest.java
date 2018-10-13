package seedu.souschef.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.souschef.testutil.EventsUtil.postNow;
import static seedu.souschef.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalRecipes;
import static seedu.souschef.ui.testutil.GuiTestAssert.assertCardDisplaysRecipe;
import static seedu.souschef.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.RecipeCardHandle;
import guitests.guihandles.RecipeListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.commons.util.FileUtil;
import seedu.souschef.commons.util.XmlUtil;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.storage.recipe.XmlSerializableAddressBook;

public class RecipeListPanelTest extends GuiUnitTest {
    private static final ObservableList<Recipe> TYPICAL_RECIPES =
            FXCollections.observableList(getTypicalRecipes());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_RECIPE);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private RecipeListPanelHandle recipeListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_RECIPES);

        for (int i = 0; i < TYPICAL_RECIPES.size(); i++) {
            recipeListPanelHandle.navigateToCard(TYPICAL_RECIPES.get(i));
            Recipe expectedRecipe = TYPICAL_RECIPES.get(i);
            RecipeCardHandle actualCard = recipeListPanelHandle.getRecipeCardHandle(i);

            assertCardDisplaysRecipe(expectedRecipe, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_RECIPES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        RecipeCardHandle expectedRecipe = recipeListPanelHandle.getRecipeCardHandle(INDEX_SECOND_RECIPE.getZeroBased());
        RecipeCardHandle selectedRecipe = recipeListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedRecipe, selectedRecipe);
    }

    /**
     * Verifies that creating and deleting large number of recipes in {@code RecipeListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Recipe> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of recipe cards exceeded time limit");
    }

    /**
     * Returns a list of recipes containing {@code recipeCount} recipes that is used to populate the
     * {@code RecipeListPanel}.
     */
    private ObservableList<Recipe> createBackingList(int recipeCount) throws Exception {
        Path xmlFile = createXmlFileWithRecipes(recipeCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getObservableRecipeList());
    }

    /**
     * Returns a .xml file containing {@code recipeCount} recipes. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithRecipes(int recipeCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < recipeCount; i++) {
            builder.append("<recipes>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</recipes>\n");
        }
        builder.append("</addressbook>\n");

        Path manyRecipesFile = TEST_DATA_FOLDER.resolve("manyPersons.xml");
        FileUtil.createFile(manyRecipesFile);
        FileUtil.writeToFile(manyRecipesFile, builder.toString());
        manyRecipesFile.toFile().deleteOnExit();
        return manyRecipesFile;
    }

    /**
     * Initializes {@code recipeListPanelHandle} with a {@code RecipeListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code RecipeListPanel}.
     */
    private void initUi(ObservableList<Recipe> backingList) {
        RecipeListPanel recipeListPanel = new RecipeListPanel(backingList);
        uiPartRule.setUiPart(recipeListPanel);

        recipeListPanelHandle = new RecipeListPanelHandle(getChildNode(recipeListPanel.getRoot(),
                RecipeListPanelHandle.RECIPE_LIST_VIEW_ID));
    }
}
