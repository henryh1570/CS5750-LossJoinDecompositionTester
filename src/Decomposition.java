import java.util.HashSet;

public class Decomposition {

    HashSet<Relation> setOfDecompositions;

    public Decomposition(HashSet<Relation> decompositions) {
        setOfDecompositions = new HashSet<>();
        for (Relation R: decompositions) {
            setOfDecompositions.add(R);
        }
    }
}
