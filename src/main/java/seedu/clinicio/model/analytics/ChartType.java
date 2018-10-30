package seedu.clinicio.model.analytics;

/**
 *
 */
public enum ChartType {
    VERTICAL_BAR("vbar"),
    HORIZONTAL_BAR("hbar"),
    STACKED_BAR("stackedbar"),
    LINE("line"),
    AREA("area");

    private final String type;

    ChartType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
