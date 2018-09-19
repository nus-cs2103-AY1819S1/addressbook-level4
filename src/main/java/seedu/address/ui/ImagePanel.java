package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

import java.net.URL;
import java.util.logging.Logger;

/**
 * An UI component that displays an Image.
 */
public class ImagePanel extends UiPart<Region> {

    private static final String FXML = "ImagePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    /**
     * An image panel for showing the previews
     */


    @FXML
    private ImageView imageView;

    public ImagePanel(URL url) {
        super(FXML);
        Image image = new Image(url.toString());
        imageView.setImage(image);
    }

    public void setImage(Image image){
        this.imageView.setImage(image);
    }


}
