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
    private Dependencies sampleDependencies;
    private Set<String> sampleSet;
    private Task sampleTaskInDependency;
    private Task sampleTaskOutsideDependency;
    private String sampleHash1;
    private String sampleHash2;
    @Before
    public void setUp() {
        sampleHash1 = "12345";
        sampleHash2 = "67890";

        sampleTaskInDependency = new TaskBuilder().build();

        HashSet<String> hashes = new HashSet<String>();
        hashes.add(sampleHash1);
        hashes.add(Integer.toString(sampleTaskInDependency.hashCode()));

        sampleTaskOutsideDependency = new TaskBuilder().withName("Outside Dependency").build();
        sampleDependencies = new Dependencies(hashes);
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
    public void updateHash_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dependencies().updateHash(null, sampleHash1));
        assertThrows(NullPointerException.class, () -> new Dependencies().updateHash(sampleHash1, null));

    }

    @Test
    public void containsDependency() {
        assertFalse(sampleDependencies.containsDependency(sampleTaskOutsideDependency));
        assertTrue(sampleDependencies.containsDependency(sampleTaskInDependency));
    }

    @Test
    public void addDependency() {
        //Creating expected dependency
        sampleSet.add(Integer.toString(sampleTaskOutsideDependency.hashCode()));
        Dependencies expectedDependency = new Dependencies(sampleSet);
        //Checking equality
        assertEquals(expectedDependency, sampleDependencies.addDependency(sampleTaskOutsideDependency));
    }

    @Test
    public void removeDependency() {
        //Creating expected dependency
        sampleSet.remove(Integer.toString(sampleTaskInDependency.hashCode()));
        Dependencies expectedDependency = new Dependencies(sampleSet);
        //Checking equality
        assertEquals(expectedDependency, sampleDependencies.removeDependency(sampleTaskInDependency));
    }

    @Test
    public void updateHash() {
        String oldHash = Integer.toString(sampleTaskInDependency.hashCode());
        String newHash = sampleHash2;
        sampleSet.remove(oldHash);
        sampleSet.add(newHash);
        Dependencies expectedDependency = new Dependencies(sampleSet);
        assertEquals(expectedDependency, sampleDependencies.updateHash(oldHash, newHash));
    }

    @Test
    public void equals() {
        assertNotEquals(sampleDependencies, new Dependencies());

        HashSet<String> hashes = new HashSet<String>();
        hashes.add(sampleHash1);
        hashes.add(Integer.toString(sampleTaskInDependency.hashCode()));

        Dependencies newSample = new Dependencies(hashes);
        assertEquals(newSample, sampleDependencies);
    }

}
