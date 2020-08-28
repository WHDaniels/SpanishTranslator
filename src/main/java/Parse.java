import java.io.PrintWriter;

class Parse {

    static void Noun(String parsedWord, String pos, String word, PrintWriter nounOut) {
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
