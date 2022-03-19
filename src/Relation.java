import java.util.HashSet;

//Relation R is the set containing all attributes. Also known as universal relation.
public class Relation {

    HashSet<String> setOfAttributes;

    public Relation(String[] arr) {
        setOfAttributes = new HashSet<>();
        for (String att : arr) {
            setOfAttributes.add(att);
        }
    }

    public String toString() {
        return setOfAttributes.toString();
    }
}
