import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
This is the class dedicated to populating the different parts-of-speech-specified text
documents with the correct translations by scraping a Spanish-to-English translation site.
This part of the program is only meant to be used once, as the documents only need to be
populated once (which is why less effort was put into refactoring into cleaner code).
The code to populate the documents is commented out in the beginning of the "translate" method.
If for some reason posAssign() was ran after the documents have already been populated,
the program would simply start over and continue to populate them starting from the first
word in the "english3.txt" file, appending the new entries to the end of the document.
This shouldn't have any effect on the program other than the HashMaps (that hold the
word/translation pairings) being larger.
 */

public class populate {

    public static void posAssign() throws IOException{

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


        String word, url, noun, verb, adj, adv, pronoun, article, prep, conj, inter;
        Elements nounHolder, verbHolder, adjHolder, advHolder, pronounHolder, articleHolder, prepHolder, conjHolder, interHolder;
        int nounLocation;

        Document document = null;

        BufferedReader reader   = new BufferedReader(new FileReader("english3.txt"));
        PrintWriter nounOut     = new PrintWriter(new FileOutputStream("noun.txt", true),  true);
        PrintWriter verbOut     = new PrintWriter(new FileOutputStream("verb.txt", true),  true);
        PrintWriter adjOut      = new PrintWriter(new FileOutputStream("adj.txt", true),  true);
        PrintWriter advOut      = new PrintWriter(new FileOutputStream("adv.txt", true),  true);
        PrintWriter pronounOut  = new PrintWriter(new FileOutputStream("pronoun.txt", true),  true);
        PrintWriter articleOut  = new PrintWriter(new FileOutputStream("article.txt", true),  true);
        PrintWriter prepOut     = new PrintWriter(new FileOutputStream("prep.txt", true),  true);
        PrintWriter conjOut     = new PrintWriter(new FileOutputStream("conj.txt", true),  true);
        PrintWriter interOut    = new PrintWriter(new FileOutputStream("inter.txt", true),  true);

        // While there is something to be read in the BufferedReader, assign 'word' to what is on that line
        while ((word = reader.readLine()) != null){

            System.out.println("|" + word + "|");
            url = "https://www.spanishdict.com/translate/" + word;

            try{

                // Setup for the Jsoup parser
                document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .timeout(60000)
                        .ignoreHttpErrors(true)
                        .get();

            }
            catch(SocketException Se)
            {
                //System.out.print("Socket Exception: " + word);
            }
            catch(SocketTimeoutException STe)
            {
                //System.out.print("Timeout Exception: " + word);
            }

            //-----------------------------------------------------------------

            String pos = "";

            // read some text in the text variable
            // String text = word;

            // create an empty Annotation just with the given text
            Annotation annotation = new Annotation(word);

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
            noun = "";
            nounLocation = 0;

                /* If the website only has one part of speech for the specified word,
                   the following code (up until the 'else' statement) will get the translation.

                   The following abbreviations are shorthand for the CSS selectors used in
                   the scraping of the website to get the translations for words.

                   Jsoup takes these as inputs to get the raw text at those selectors,
                   determining the parts of speech of the word read from the BufferedReader
                   with the help of a few if statements.

                   If a word is found to have say, a 'noun' heading, further scraping is done
                   to get the first noun that is a spanish translation of the english word,
                   and that translation is assigned to the variable 'noun' (and so on for
                   the rest of the parts of speech).

                   (The selectors are different for words associated with one part of speech
                    as opposed to many, which is the reason they can be differentiated).
                */

            String fHH = "#dictionary-neodict-en > div";
            String fH = "._2RDqaj2O";
            String qD = "#quickdef1-en";
            String qDtext = "._1btShz4h";

            Elements wordHolder = document.select(qD);
            String parsedWord = wordHolder.select(qDtext).text();

            Elements firstHeadingHolder = document.select(fHH);
            String firstHeading = firstHeadingHolder.select(fH).text();

            if("noun".equalsIgnoreCase(firstHeading)){
                    /* A few rules for cleaning up the selected noun.
                       Nouns with "el" or "la" (or both) at the beginning
                       have those articles removed.

                       If the noun is detected to be singular, it is promptly
                       added to the 'noun.txt' file, if the noun is detected to be
                       plural, it is reconstructed into the plural version (see
                       'createPlural' method below).
                    */

                if(word.length() <= 1 || noun.length() <= 0)
                    continue;

                Parse.Noun(parsedWord, pos, word, nounOut);

            }
            else if("transitive verb".equalsIgnoreCase(firstHeading) || ("intransitive verb").equalsIgnoreCase(firstHeading)){
                verbOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }
            else if("adjective".equalsIgnoreCase(firstHeading)){
                adjOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }
            else if("adverb".equalsIgnoreCase(firstHeading)){
                advOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }
            else if("pronoun".equalsIgnoreCase(firstHeading)){
                pronounOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }
            else if("definite article".equalsIgnoreCase(firstHeading)){
                articleOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }
            else if("preposition".equalsIgnoreCase(firstHeading)){
                prepOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }
            else if("conjunction".equalsIgnoreCase(firstHeading)){
                conjOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }
            else if("interjection".equalsIgnoreCase(firstHeading)){
                interOut.println(word + " " + parsedWord);
                System.out.println(word + " : " + parsedWord);
            }

                /* If there are multiple parts of speech for the word,
                   the selectors that are to be parsed for the various parts
                   of speech headings (noun, verb, etc) are cycled through.

                   The following 'hH' and 'posHolder' string are such selectors,
                   whose positions correspond to the number 'i'. The maximum number
                   of positions on the website is 5, so as the loop goes through 5
                   iterations, if a word is found to be associated with any of the
                   parts of speech, its position is caught along with the text at
                   that position (which is the translated word).

                   For example, if a word 'love' has multiple parts of speech
                   associated with it, if at any position 1 through 5 there is
                   a 'noun' heading, 'nounHolder' captures that specific selector
                   and extracts the text that is the translation for that word.
                */

            else{
                int i = 1;
                while(i <= 5){

                    String hH = "#dictionary-neodict-en > div > div._2xs-UBSR:nth-of-type(" + i + ")";
                    String hHtext = "._2RDqaj2O";

                    String posHolder = "div._2xs-UBSR:nth-of-type(" + i + ") > .FyTYrC-y > div:nth-of-type(1) > .FyTYrC-y > div:nth-of-type(1)";
                    String posText = ".C2TP2MvR";

                    Elements headingHolder = document.select(hH);
                    String heading = headingHolder.select(hHtext).text();
                    if("noun".equalsIgnoreCase(heading)){
                        nounLocation = i;
                        nounHolder = document.select(posHolder);
                        noun = nounHolder.select(posText).text();

                        Elements extraHeadingHolder = document.select(".inline--CJsLA.showcase--2i4Hl");
                        String extraHeading = extraHeadingHolder.select(".noHref--1cchI").text();

                        if("plural noun".equals(extraHeading)){
                            // Gets only the first noun that is given is there is a "plural noun" heading
                            noun = "";
                            continue;
                        }

                            /* If the first part of speech of the word is a noun
                               grab the translation from a different spot than
                               normal to avoid repetitions of the translated word
                            */
                        if(nounLocation == 2){
                            nounHolder = document.select(qD);
                            noun = nounHolder.select(qDtext).text();
                        }

                        if(word.length() <= 1 || noun.length() <= 0)
                            noun = "";
                        else if(noun.length() >= 6 && "el/la".equals(noun.substring(0, 5)))
                            noun = noun.substring(6, noun.length());
                        else if("el".equals(noun.substring(0, 2)) || "la".equals(noun.substring(0, 2)))
                            noun = noun.substring(3, noun.length());
                        if("NN".equals(pos) || "IN".equals(pos)){
                            nounOut.println(word + " " + noun);
                            System.out.println(word + " : " + noun);
                        }
                        else if("NNS".equals(pos)){
                            noun = Create.Plural(noun);
                            nounOut.println(word + " " + noun);
                            System.out.println(word + " : " + noun);
                        }
                    }
                    if("transitive verb".equalsIgnoreCase(heading) || "intransitive verb".equalsIgnoreCase(heading)){
                        verbHolder = document.select(posHolder);
                        verb = verbHolder.select(posText).text();

                        verbOut.println(word + " " + verb);
                        System.out.println(word + " : " + verb);
                    }
                    if("adjective".equalsIgnoreCase(heading)){
                        adjHolder = document.select(posHolder);
                        adj = adjHolder.select(posText).text();

                        adjOut.println(word + " " + adj);
                        System.out.println(word + " : " + adj);
                    }
                    if("adverb".equalsIgnoreCase(heading)){
                        advHolder = document.select(posHolder);
                        adv = advHolder.select(posText).text();

                        advOut.println(word + " " + adv);
                        System.out.println(word + " : " + adv);
                    }
                    if("pronoun".equalsIgnoreCase(heading)){
                        pronounHolder = document.select(posHolder);
                        pronoun = pronounHolder.select(posText).text();

                        pronounOut.println(word + " " + pronoun);
                        System.out.println(word + " : " + pronoun);
                    }
                    if("article".equalsIgnoreCase(heading)){
                        articleHolder = document.select(posHolder);
                        article = articleHolder.select(posText).text();

                        articleOut.println(word + " " + article);
                        System.out.println(word + " : " + article);
                    }
                    if("preposition".equalsIgnoreCase(heading)){
                        prepHolder = document.select(posHolder);
                        prep = prepHolder.select(posText).text();

                        prepOut.println(word + " " + prep);
                        System.out.println(word + " : " + prep);
                    }
                    if("conjunction".equalsIgnoreCase(heading)){
                        conjHolder = document.select(posHolder);
                        conj = conjHolder.select(posText).text();

                        conjOut.println(word + " " + conj);
                        System.out.println(word + " : " + conj);
                    }
                    if("interjection".equalsIgnoreCase(heading)){
                        interHolder = document.select(posHolder);
                        inter = interHolder.select(posText).text();

                        interOut.println(word + " " + inter);
                        System.out.println(word + " : " + inter);
                    }
                    i++;
                }
            }
                /* System.out.printf("Word: %-20s, Noun: %B(%-20s) @ %d, Verb: %B(%-20s) @ %d, Adj: %B(%-20s) @ %d, Adv: %B(%-20s) @ %d\n",
                 word, hasNoun, noun, nounLocation, hasVerb, verb, verbLocation, hasAdj, adj, adjLocation, hasAdv, adv, advLocation);
                 System.out.println(word + "    |    " + noun + "  |  " + verb + "  |  " + adj + "  |  " + adv + "  |  "
                 + pronoun + "  |  "  + article  + "  |  " + prep  + "  |  " + conj  + "  |  " + inter);
                */
        }
        nounOut.close(); verbOut.close(); adjOut.close(); advOut.close(); pronounOut.close();
        articleOut.close(); prepOut.close(); conjOut.close(); interOut.close();

    }
}