package seedu.address.logic.commands;

import static seedu.address.logic.commands.UndoRedoCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.UndoRedoCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoRedoCommandTestUtil.assertImagesEqual;

import static seedu.address.testutil.DefaultModelWithImage.getDefaultModelWithImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import seedu.address.MainApp;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;

public class UndoCommandTest {

    private final Model model = getDefaultModelWithImage();
    private CommandHistory commandHistory = new CommandHistory();
    private UndoCommand undoCommand = new UndoCommand();

    private BufferedImage originalImage = null;
    private BufferedImage oneTransformationDoneImage = null;
    private BufferedImage twoTransformationDoneImage = null;

    private Transformation grayTransformation = new Transformation("colorspace", "gray");
    private Transformation blurTransformation = new Transformation("blur", "0x8");

    @Before
    public void setUp() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            originalImage = ImageIO.read(classLoader.getResource("testimgs/original.png"));
            oneTransformationDoneImage = ImageIO.read(classLoader.getResource("testimgs/gray.png"));
            twoTransformationDoneImage = ImageIO.read(classLoader.getResource("testimgs/gray&blur.png"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        System.out.println("reading resources ok");
    }

    @Test
    public void execute_singleUndo() {
        executeTransformation(grayTransformation);
        assertImagesEqual(getModelPreviewImage(model), oneTransformationDoneImage);

        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", originalImage);
        clearCache();
    }

    @Test
    public void execute_successiveUndo() {
        executeTransformation(grayTransformation);
        assertImagesEqual(getModelPreviewImage(model), oneTransformationDoneImage);
        executeTransformation(blurTransformation);
        assertImagesEqual(getModelPreviewImage(model), twoTransformationDoneImage);

        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", oneTransformationDoneImage);

        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", originalImage);
        clearCache();
    }

    @Test
    public void execute_defaultStateHasNothingToUndo() {
        Model model = getDefaultModelWithImage();
        assertCommandFailure(undoCommand, model, commandHistory, "No more commands to undo!", originalImage);
        clearCache();
    }

    /**
     * Get the model's previewImage's current image.
     */
    private BufferedImage getModelPreviewImage(Model model) {
        return model.getCurrentPreviewImage().getImage();
    }

    /**
     * Execute the transformation on the current model.
     */
    private void executeTransformation(Transformation transformation) {
        try {
            Path imagePath = model.getCurrentPreviewImagePath();
            BufferedImage modifiedImage = ImageMagickUtil.processImage(imagePath, transformation);
            model.updateCurrentPreviewImage(modifiedImage, transformation);
        } catch (IOException | InterruptedException | ParseException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Clears cache in storage folder.
     */
    public void clearCache() {
        String cachePath = MainApp.MAIN_PATH + "/src/main/java/seedu/address/storage/cache";
        File cache = new File(cachePath);
        File[] list = cache.listFiles();
        if (list != null) {
            for (File file: list) {
                if (!file.getName().equals("dummy.txt")) {
                    file.delete();
                }
            }
        }
    }
}
