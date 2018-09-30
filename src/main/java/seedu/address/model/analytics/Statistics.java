package seedu.address.model.analytics;

import java.util.HashMap;
import java.util.Set;

/**
 * Represents statistics of a general type.
 */
public abstract class Statistics {

    public HashMap<String, Integer> statistics;

    /**
     * Initializes a HashMap to store statistic names as keys and their values.
     */
    public Statistics() {
        this.statistics = new HashMap<>();
    }

    /**
     * @return The HashMap containing all statistics of a type.
     */
    public HashMap<String, Integer> getStatistics() {
        return this.statistics;
    }

    /**
     * @return The names of the statistics.
     */
    public Set<String> getKeys() {
        return this.statistics.keySet();
    }

    /**
     * @return A String consisting of a statistic and its value, each on a separate line.
     */
    @Override
    public String toString() {
        String toDisplay = "";
        for (HashMap.Entry<String, Integer> entry : this.statistics.entrySet()) {
            toDisplay += entry.getKey() + ": " + entry.getValue() + "\n";
        }

        return toDisplay;
    }

    public abstract void compute();
}
