import java.util.ArrayList;

//Decomposition is a set of smaller relations {R1, R2, ..., Rn} derived from a relation R.
public class Decomposition {

    ArrayList<Relation> setOfDecompositions;

    public Decomposition(ArrayList<Relation> decompositions) {
        setOfDecompositions = new ArrayList<>();
        for (Relation R: decompositions) {
            setOfDecompositions.add(R);
        }
    }
}
