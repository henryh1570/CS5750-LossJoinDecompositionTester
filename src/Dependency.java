import java.util.HashSet;

public class Dependency {

    HashSet<AttributeSet> determinants;  //Left-side
    HashSet<AttributeSet> dependents;    //Right-side

    public Dependency(AttributeSet[] left, AttributeSet[] right) {

        for (AttributeSet as : left) {
            determinants.add(as);
        }

        for(AttributeSet as : right) {
            dependents.add(as);
        }

    }
}
