import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zjhnd on 2017/4/13.
 */
public class Test {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            System.out.print(entry.getValue() + " ");
        }
        System.out.println();
        map.remove(1);
        iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            System.out.print(entry.getValue() + " ");
        }
    }
}
