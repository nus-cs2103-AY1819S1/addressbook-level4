package seedu.address.storage;

import java.util.HashSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  This class holds a full phrase and a set of slang corresponding to this full phrase.
 *  Thus, one instance of this class defines a mapping between a full phrase and its set of slang.
 *  For example, a full phrase could be "computer science" and the set of slang would be {"cs", "compsci"}.
 *
 *  It is meant to be JAXB friendly - i.e. meant to be used to des-serialize mappings held in XML, into a Java object
 *  representation.
 */

@XmlRootElement
public class XmlMapping {

    @XmlElement
    private String fullPhrase;

    @XmlElementWrapper(name = "allSlang")
    @XmlElement(name = "slang")
    private HashSet<String> slangSet;

    /* JAXB requires a default constructor */
    public XmlMapping() {}

    public XmlMapping(String fullPhrase, HashSet<String> slangSet) {
        this.fullPhrase = fullPhrase;
        this.slangSet = slangSet;
    }

    public String getFullPhrase() {
        return fullPhrase;
    }

    public HashSet<String> getSlangSet() {
        return slangSet;
    }

    @Override
    public String toString() {
        return fullPhrase + "[" + slangSet + "]";
    }
}
