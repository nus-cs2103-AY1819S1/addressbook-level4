package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.TaskBuilder;


public class DependenciesTest {
    private Dependencies sampleDependency;
    private Set<String> sampleSet;
    private Task sampleTaskInDependency;
    private Task sampleTaskOutsideDependency;

    @Before
    public void setUp() {
        HashSet<String> hashes = new HashSet<String>();
        hashes.add("12345");
        hashes.add("67890");
        sampleTaskInDependency = new TaskBuilder().withName("Test").build();
        hashes.add(Integer.toString(sampleTaskInDependency.hashCode()));

        sampleTaskOutsideDependency = new TaskBuilder().build();
        sampleDependency = new Dependencies(hashes);
        sampleSet = hashes;

    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dependencies(null));
    }

    @Test
    public void addDependency_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dependencies().addDependency(null));
    }

    @Test
    public void removeDependency_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dependencies().removeDependency(null));
    }

    @Test
    public void containsDependency() {
        assertFalse(sampleDependency.containsDependency(sampleTaskOutsideDependency));
        assertTrue(sampleDependency.containsDependency(sampleTaskInDependency));
    }

    @Test
    public void addDependency() {
        //Creating expected dependency
        sampleSet.add(Integer.toString(sampleTaskOutsideDependency.hashCode()));
        Dependencies expectedDependency = new Dependencies(sampleSet);
        //Checking equality
        assertEquals(expectedDependency, sampleDependency.addDependency(sampleTaskOutsideDependency));
    }

    @Test
    public void removeDependency() {
        //Creating expected dependency
        sampleSet.remove(Integer.toString(sampleTaskInDependency.hashCode()));
        Dependencies expectedDependency = new Dependencies(sampleSet);
        //Checking equality
        assertEquals(expectedDependency, sampleDependency.removeDependency(sampleTaskInDependency));
    }

    @Test
    public void updateHash() {
        String oldHash = Integer.toString(sampleTaskInDependency.hashCode());
        String newHash = "99999";
        sampleSet.remove(oldHash);
        sampleSet.add(newHash);
        Dependencies expectedDependency = new Dependencies(sampleSet);
        assertEquals(expectedDependency, sampleDependency.updateHash(oldHash, newHash));
    }

    @Test
    public void equals() {
        assertNotEquals(sampleDependency, new Dependencies());

        HashSet<String> hashes = new HashSet<String>();
        hashes.add("12345");
        hashes.add("67890");
        sampleTaskInDependency = new TaskBuilder().withName("Test").build();
        hashes.add(Integer.toString(sampleTaskInDependency.hashCode()));

        Dependencies newSample = new Dependencies(hashes);
        assertEquals(newSample, sampleDependency);
    }

}
