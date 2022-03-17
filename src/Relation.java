import java.util.HashSet;

public class Relation {

    HashSet<AttributeSet> setOfAttSets;

    public Relation(AttributeSet[] arr) {
        setOfAttSets = new HashSet<>();
        for (AttributeSet as : arr) {
            setOfAttSets.add(as);
        }
    }

    public String toString() {
        return setOfAttSets.toString();
    }
}
