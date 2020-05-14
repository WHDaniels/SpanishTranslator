import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author mercm
 */
public class Maps{

    public Maps(){}

    public HashMap<String, String> nounMap() throws IOException{
        BufferedReader nouns = new BufferedReader(new FileReader("textDocuments/noun.txt"));
        HashMap<String, String> nounsMap = new HashMap<>();

        createMap(nouns, nounsMap);

        return nounsMap;
    }

    public HashMap<String, String> verbMap() throws IOException{
        BufferedReader verbs = new BufferedReader(new FileReader("textDocuments/verb.txt"));
        HashMap<String, String> verbsMap = new HashMap<>();

        createMap(verbs, verbsMap);

        return verbsMap;
    }

    public HashMap<String, String> adjMap() throws IOException{
        BufferedReader adjectives = new BufferedReader(new FileReader("textDocuments/adj.txt"));
        HashMap<String, String> adjMap = new HashMap<>();

        createMap(adjectives, adjMap);

        return adjMap;
    }

    public HashMap<String, String> advMap() throws IOException{
        BufferedReader adverbs = new BufferedReader(new FileReader("textDocuments/adv.txt"));
        HashMap<String, String> advMap = new HashMap<>();

        createMap(adverbs, advMap);

        return advMap;
    }

    public HashMap<String, String> pronounMap() throws IOException{
        BufferedReader pronouns = new BufferedReader(new FileReader("textDocuments/pronoun.txt"));
        HashMap<String, String> pronounMap = new HashMap<>();

        createMap(pronouns, pronounMap);

        return pronounMap;
    }

    public HashMap<String, String> articleMap() throws IOException{
        BufferedReader articles = new BufferedReader(new FileReader("textDocuments/article.txt"));
        HashMap<String, String> articleMap = new HashMap<>();

        createMap(articles, articleMap);

        return articleMap;
    }

    public HashMap<String, String> prepMap() throws IOException{
        BufferedReader preps = new BufferedReader(new FileReader("textDocuments/prep.txt"));
        HashMap<String, String> prepMap = new HashMap<>();

        createMap(preps, prepMap);

        return prepMap;
    }

    public HashMap<String, String> conjMap() throws IOException{
        BufferedReader conjs = new BufferedReader(new FileReader("textDocuments/conj.txt"));
        HashMap<String, String> conjMap = new HashMap<>();

        createMap(conjs, conjMap);

        return conjMap;
    }

    public HashMap<String, String> interMap() throws IOException{
        BufferedReader inters = new BufferedReader(new FileReader("textDocuments/inter.txt"));
        HashMap<String, String> interMap = new HashMap<>();

        createMap(inters, interMap);

        return interMap;
    }

    public void createMap(BufferedReader reader, HashMap<String, String> map) throws IOException{

        String line;

        while((line = reader.readLine()) != null){

            String[] arr = line.split(" ", 2);

            if (arr.length >= 2)
                map.put(arr[0], arr[1]);

        }
    }
}