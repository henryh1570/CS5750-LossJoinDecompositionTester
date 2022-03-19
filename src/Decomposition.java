import java.util.HashSet;

//Decomposition is a set of smaller relations {R1, R2, ..., Rn} derived from a relation R.
public class Decomposition {

    HashSet<Relation> setOfDecompositions;

    public Decomposition(HashSet<Relation> decompositions) {
        setOfDecompositions = new HashSet<>();
        for (Relation R: decompositions) {
            setOfDecompositions.add(R);
        }
    }
}
