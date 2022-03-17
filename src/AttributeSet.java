import java.util.HashSet;

public class AttributeSet {

    HashSet<String> set;

    public AttributeSet(String[] list) {
        set = new HashSet<>();
        for(String str : list) {
            set.add(str);
        }
    }

    public String toString() {
        return set.toString();
    }
}
