# SpanishTranslator.
Program that takes a English input and translates it to Spanish. Individual word translations are made through maps which hold English keys and Spanish values taken from the text documents in the **Text Documents** folder.

These text documents each hold a specific part of speech and have already been constructed by parsing [spanishdict.com](https://www.spanishdict.com/) for each English word. 

When the program runs, the part of speech of each word inputted by the user is detected using Stanford's Open NLP, and the words Spanish translation is found by searching the text document corresponding to that word's part of speech. The output of the program is the concatenation of every translated word. Input and output is handled through the JavaFX gui.
