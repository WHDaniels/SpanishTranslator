import org.jsoup.select.Elements;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Parse {

    // The rule set used to accurately create the plural version of the noun that is called
    final static Set<String> Rule1 = new HashSet<>(Arrays.asList("a", "e", "i", "o", "u", "á", "é", "ó", "í", "ú"));
    final static Set<String> Rule2 = new HashSet<>(Arrays.asList("ay", "ey", "iy", "oy", "uy", "ch"));
    final static Set<String> Rule2_1 = new HashSet<>(Arrays.asList("l", "r", "n", "d", "j", "s", "x"));

    public static void Noun(String parsedWord, String pos, String word, PrintWriter nounOut) {
        parsedWord = Create.Noun(parsedWord);

        if("NN".equals(pos) || "IN".equals(pos)){
            nounOut.println(word + " " + parsedWord);
            System.out.println(word + " : " + parsedWord);
        }

        else if("NNS".equals(pos)){
            parsedWord = Create.Plural(parsedWord);
            nounOut.println(word + " " + parsedWord);
            System.out.println(word + " : " + parsedWord);
        }

    }
}
