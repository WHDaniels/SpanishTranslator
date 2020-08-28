import java.util.HashMap;

/**
 *
 * @author mercm
 */
class Usable {

    boolean nounUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("NN".equals(pos) || "NNS".equals(pos) || "NNP".equals(pos) || "NNPS".equals(pos));
    }

    boolean verbUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("VB".equals(pos) || "VBD".equals(pos) || "VBG".equals(pos) || "VBN".equals(pos) || "VBP".equals(pos) || "VBZ".equals(pos));
    }

    boolean adjUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("JJ".equals(pos) || "JJR".equals(pos) || "JJS".equals(pos));
    }

    boolean advUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("RB".equals(pos) || "RBR".equals(pos) || "RBS".equals(pos) || "WRB".equals(pos));
    }

    boolean pronounUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) &&
                ("PRP".equals(pos) || "PRP$".equals(pos) || "WP".equals(pos) || "WP$".equals(pos) || "WDT".equals(pos) || "WRB".equals(pos) || "LS".equals(pos));
    }

    boolean articleUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "DT".equals(pos);
    }

    boolean prepUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "IN".equals(pos);
    }

    boolean conjUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "CC".equals(pos);
    }

    boolean interUsable(HashMap<String, String> map, String[] split, int i, String pos){
        return map.containsKey(split[i]) && "UH".equals(pos);
    }
}
