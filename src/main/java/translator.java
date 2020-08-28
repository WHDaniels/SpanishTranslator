import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.IOException;
import java.util.List;
import java.util.Properties;



public class translator {

    public String translate(String userInput) throws IOException {


        //populate.posAssign();

        String translation = "";

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


        /* Creates a series of buffered readers that read from their respective
           text files. A series of hash maps are then created, and each english
           word is assigned to a key, while its spanish translation is assigned to
           the value of that key.
           Nine reader-map pairings focus on a part of speech (noun, verb, adjective,
           etc).
        */

        Maps maps = new Maps();
        Usable is = new Usable();
        Construct build = new Construct();

        // The rest of the program is the part that handles the translation of words
        // and the concatenation of a legible sentence, which is what this method returns


        // Creates an array that stores each individual word of user input
        String[] split = userInput.toLowerCase().split("\\s+");

        // Stores the last word and punctuation of the user input
        String punctuation = split[split.length-1].substring(split[split.length-1].length()-1);

        // If there is punctuation at the end of the sentence, the last
        // value of the array is the same word at that position with no punctuation
        // If not, punctuation is empty
        if((".").equals(punctuation) || ("?").equals(punctuation) || ("!").equals(punctuation)){
            String lastWord = split[split.length-1].substring(0, split[split.length-1].length()-1);
            split[split.length-1] = lastWord.trim();
        }
        else
            punctuation = "";


        boolean hasAdj = false;
        boolean hasAdv = false;

        // Loop that handles translation on a word by word basis
        for(int i = 0; i < split.length; i++){

            boolean hasComma = false;

            String pos = "";

            // If the current word has a comma, take out the comma and set
            // set hasComma to true so we can add it back after the word is translated
            if(split[i].substring(split[i].length()-1).equals(",")) {
                split[i] = split[i].substring(0, split[i].length() - 1);
                hasComma = true;
            }

            // create an empty Annotation just with the given text
            Annotation annotation = new Annotation(split[i]);

            // run all Annotators on this text
            pipeline.annotate(annotation);


            // these are all the sentences in this document
            // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
            List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

            for(CoreMap sentence: sentences) {
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    // this is the POS tag of the token
                    pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                }
            }

            //-----------------------------------------------------------------

            // if the word can be an adjective and the word after the adjective is a noun
            if(is.adjUsable(maps.adjMap(), split, i, pos) && (split.length > i+1 && maps.nounMap().containsKey(split[i+1]))){
                hasAdj = true; continue; }

            // if the word can be an adverb and the word after the adverb is a noun
            if(is.advUsable(maps.advMap(), split, i, pos) && (split.length > i+1 && maps.nounMap().containsKey(split[i+1]))){
                hasAdv = true; continue; }

            translation += build.construct(maps, split, i, pos, translation);


            if(is.nounUsable(maps.nounMap(), split, i, pos) && hasAdj){
                translation += maps.adjMap().get(split[i-1]).toLowerCase() + " ";  hasAdj = false; }

            if(is.nounUsable(maps.nounMap(), split, i, pos) && hasAdv){
                translation += maps.advMap().get(split[i-1]).toLowerCase() + " ";  hasAdv = false; }

            if(hasComma)
                translation = translation.substring(0, translation.length()-1) + ", ";
        }

        //-----------------------------------------------------------------

        // capitalize the first letter of the sentence post-translation
        translation = translation.substring(0,1).toUpperCase() + translation.substring(1).trim() + punctuation;

        return translation;
    }
}