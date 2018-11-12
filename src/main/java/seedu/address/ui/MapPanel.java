package seedu.address.ui;

import static seedu.address.ui.util.MapDataUtil.DISTRICT_CACHE;
import static seedu.address.ui.util.MapDataUtil.POSTAL_CODE_CACHE;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * An UI component that displays a map along with placeholders for pending {@code Order}.
 */
public class MapPanel extends UiPart<Region> {

    private static final String FXML = "MapPanel.fxml";
    private static final String PLACEHOLDER_IMAGE_PATH = "/images/placeholder.png";

    private final Logger logger = LogsCenter.getLogger(MapPanel.class);

    private final Image placeholder = new Image(getClass().getResource(PLACEHOLDER_IMAGE_PATH).toExternalForm());
    private ImageView placeholderContainer;

    @FXML
    private GridPane mapPane;

    public MapPanel() {
        super(FXML);
    }

    /**
     * Initialises map with all pending orders using a cache
     * @param orderMap map of pending orders where key is postal code and value is counter
     */
    public void initialise(HashMap<String, Integer> orderMap) {
        // for each postal code
        for (String postalCodeKey : orderMap.keySet()) {

            if (POSTAL_CODE_CACHE.containsKey(postalCodeKey)) {

                int regionCode = POSTAL_CODE_CACHE.get(postalCodeKey);
                Integer[] coordinates = DISTRICT_CACHE.get(regionCode);

                placeholderContainer = new ImageView(placeholder);

                int count = orderMap.get(postalCodeKey);

                // set a limit on count
                if (count > 25) {
                    count = 25;
                }

                int increment = 16 + count * 2;

                placeholderContainer.setPreserveRatio(true);
                placeholderContainer.setFitHeight(increment);
                placeholderContainer.setFitWidth(increment);

                mapPane.add(placeholderContainer, coordinates[1], coordinates[0]);
            }
        }
    }

    /**
     * Clears all placeholders from map
     */
    public void clear() {
        mapPane.getChildren().clear();
    }
}
