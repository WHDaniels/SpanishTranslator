import java.util.HashMap;

/**
 *
 * @author mercm
 */
public class Usable {

    public boolean nounUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("NN".equals(pos) || "NNS".equals(pos) || "NNP".equals(pos) || "NNPS".equals(pos));
    }

    public boolean verbUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("VB".equals(pos) || "VBD".equals(pos) || "VBG".equals(pos) || "VBN".equals(pos) || "VBP".equals(pos) || "VBZ".equals(pos));
    }

    public boolean adjUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("JJ".equals(pos) || "JJR".equals(pos) || "JJS".equals(pos));
    }

    public boolean advUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("RB".equals(pos) || "RBR".equals(pos) || "RBS".equals(pos) || "WRB".equals(pos));
    }

    public boolean pronounUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("PRP".equals(pos) || "PRP$".equals(pos) || "WP".equals(pos) || "WP$".equals(pos) || "WDT".equals(pos) || "WRB".equals(pos) || "LS".equals(pos));
    }

    public boolean articleUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "DT".equals(pos);
    }

    public boolean prepUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "IN".equals(pos);
    }

    public boolean conjUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "CC".equals(pos);
    }

    public boolean interUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "UH".equals(pos);
    }
}
