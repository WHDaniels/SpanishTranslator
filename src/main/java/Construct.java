import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author mercm
 */
public class Construct {

    public String construct(Maps maps, String[] split, int i, String pos, String translation) throws IOException{

        Usable is = new Usable();

        // If word to be translated is noun
        if(is.nounUsable(maps.nounMap(), split, i, pos))
            return translatedNoun(split, i, maps.nounMap());

            // If word to be translated is verb
        else if(is.verbUsable(maps.verbMap(), split, i, pos))
            return maps.verbMap().get(split[i]).toLowerCase() + " ";

            // If word to be translated is adjective
        else if(is.adjUsable(maps.adjMap(), split, i, pos))
            return maps.adjMap().get(split[i]).toLowerCase() + " ";

            // If word to be translated is adverb
        else if(is.advUsable(maps.advMap(), split, i, pos))
            return maps.advMap().get(split[i]).toLowerCase() + " ";

            // If word to be translated is pronoun
        else if(is.pronounUsable(maps.pronounMap(), split, i, pos))
            return maps.pronounMap().get(split[i]).toLowerCase() + " ";

            // If word to be translated is a definite article
        else if(is.articleUsable(maps.articleMap(), split, i, pos))
            return maps.articleMap().get(split[i]).toLowerCase() + " ";

            // If word to be translated is a preposition
        else if(is.prepUsable(maps.prepMap(), split, i, pos))
            return maps.prepMap().get(split[i]).toLowerCase() + " ";

            // If word to be translated is a conjunction
        else if(is.conjUsable(maps.conjMap(), split, i, pos))
            return maps.conjMap().get(split[i]).toLowerCase() + " ";

            // If word to be translated is an interjection
        else if(is.interUsable(maps.interMap(), split, i, pos))
            return maps.interMap().get(split[i]).toLowerCase() + " ";

        return split[i] + " ";

    }

    private String translatedNoun(String[] split, int i, HashMap<String, String> map){
        if(split[i].length() < 2)
            return map.get(split[i]).toUpperCase() + " ";
        return map.get(split[i]) + " ";
    }

}