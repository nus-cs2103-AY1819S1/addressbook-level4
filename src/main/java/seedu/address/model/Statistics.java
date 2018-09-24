package seedu.address.model;

import java.util.HashMap;
import java.util.Set;

public abstract class Statistics {

    public HashMap<String, Integer> statistics;

    public Statistics() {
        this.statistics = new HashMap<>();
    }

    public HashMap<String, Integer> getStatistics() {
        return this.statistics;
    }

    public Set<String> getKeys() {
        return this.statistics.keySet();
    }

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
