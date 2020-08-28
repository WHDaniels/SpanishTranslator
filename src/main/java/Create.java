import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Create {

    static String Noun(String noun) {
        if(noun.length() >= 6 && "el/la".equals(noun.substring(0, 5)))
            return noun.substring(6, noun.length());
        else if("el".equals(noun.substring(0, 2)) || "la".equals(noun.substring(0, 2)))
            return noun.substring(3, noun.length());
        return noun;
    }

    static String Plural(String noun) {

        final Set<String> Rule1 = new HashSet<>(Arrays.asList("a", "e", "i", "o", "u", "á", "é", "ó", "í", "ú"));
        final Set<String> Rule2 = new HashSet<>(Arrays.asList("ay", "ey", "iy", "oy", "uy", "ch"));
        final Set<String> Rule2_1 = new HashSet<>(Arrays.asList("l", "r", "n", "d", "j", "s", "x"));

        // Weekdays are unchanged
        if("lunes".equalsIgnoreCase(noun))
            return "lunes";
        if("martes".equalsIgnoreCase(noun))
            return "martes";
        if("miércoles".equalsIgnoreCase(noun))
            return "miércoles";
        if("jueves".equalsIgnoreCase(noun))
            return "jueves";
        if("viernes".equalsIgnoreCase(noun))
            return "viernes";

        String lastLetterOfNoun = noun.substring(noun.length() - 1);
        String lastTwoLettersOfNoun = noun.substring(noun.length() - 2);
        String lastThreeLettersOfNoun = noun.substring(noun.length() - 3);

            // Add -es and drop the accent over the 'o' if the noun ends in -ión.
        if(noun.length() > 3 && "ión".equals(lastThreeLettersOfNoun))
            return noun.substring(0, noun.length() - 3) + "iones";

            // If a singular noun ends in z, you must change the z to a c before adding -es.
        else if(noun.length() > 0 && "z".equals(lastLetterOfNoun)){
            return noun.substring(0, noun.length() - 1) + "ces";
        }

        // The singular and plural forms of words ending in -st or -zt are the same.
        else if((noun.length() > 2 && "st".equals(lastTwoLettersOfNoun)) || (noun.length() > 2 && "zt".equals(lastTwoLettersOfNoun)))
            return noun;

            // When singular nouns end in -c, their final letter changes to -qu before adding -es.
        else if(noun.length() > 0 && "c".equals(lastLetterOfNoun))
            return noun.substring(0, noun.length() - 1) + "ques";

            // When singular nouns end in -g, their final letter changes to -gu before adding -es.
        else if(noun.length() > 0 && "g".equals(lastLetterOfNoun))
            return noun.substring(0, noun.length() - 1) + "gues";

            // The plural of "sí" is always "síes"
        else if(("sí").equals(noun))
            return "síes";

            // Rule 1: If a singular noun ends in an unstressed vowel (a, e, i, o, u) or the stressed vowels á, é, ó, í or ú add -s to the end of a singular noun to make it plural.
        else if(noun.length() > 0 && Rule1.contains(lastLetterOfNoun)){
            return noun + "s";
        }

        // Rule 2: If a singular noun ends in a vowel plus y or the consonants l, r, n, d, z, j, s, x, or ch, add -es.
        else if((noun.length() > 1 && Rule2.contains(lastTwoLettersOfNoun)) || (noun.length() > 0 && Rule2_1.contains(lastLetterOfNoun)))
            return noun + "es";

        else
            return noun + "s";
    }
}
