import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A graph that encodes word ladders.
 *
 * The class does not store the full graph in memory, just a dictionary of words.
 * The edges are then computed on demand.
 */
public class WordLadder implements DirectedGraph<String> {

    private final Set<String> dictionary;
    private final Set<Character> alphabet;

    /**
     * Creates a new empty graph.
     */
    public WordLadder() {
        dictionary = new HashSet<>();
        alphabet = new HashSet<>();
    }

    /**
     * Adds the {@code word} to the dictionary if it only contains letters.
     * The word is converted to lowercase.
     * @param word  the word
     */
    public void addWord(String word) {
        if (word.matches("\\p{L}+")) {
            word = word.toLowerCase();
            dictionary.add(word);
            for (char c : word.toCharArray())
                alphabet.add(c);
        }
    }

    /**
     * Creates a new word ladder graph from the given dictionary file.
     * The file should contain one word per line, except lines starting with "#".
     * @param file  path to a text file
     */
    public WordLadder(String file) throws IOException {
        this();
        Files.lines(Paths.get(file))
            .filter(line -> !line.startsWith("#"))
            .map(String::trim)
            .forEach(this::addWord);
    }

    @Override
    public Set<String> nodes() {
        return Collections.unmodifiableSet(dictionary);
    }

    /**
     * @param  w  a graph node (a word)
     * @return a list of the graph edges that originate from {@code w}
     */
    @Override
    public List<DirectedEdge<String>> outgoingEdges(String w) {

        //initialize new linkedlist that takes in string w  and returns directedge obj
        List<DirectedEdge<String>> edges = new LinkedList<>();

        //iterate over all the chars in the input string, eg. "yes" has 3 letters to iterate over
        for (int i = 0; i < w.length(); i++) {

            //check if current iteration is the first char of the input string
            if (i == 0) {

                //start a loop over all the chars in the alphabet
                for (Character x : alphabet) {

                    //construct new wordladder by replacing the first char of w with the current
                    //char in the alphabet
                    String wladder = x + w.substring(i + 1);

                    //check if the ladder word is in dictionary and is not equal to w
                    if (dictionary.contains(wladder) && !wladder.equals(w)) {

                        //create new directedge obj with input string w as the starting vertex
                        //and wladder as ending vertex. then add directedge obj to linkedlist
                        edges.add(new DirectedEdge<String>(w, wladder));
                    }
                }
                //if current iteration is not first character of w
            } else {

                //start loop over all the chars in alphabet
                for (Character c : alphabet) {

                    //construct new word ladder by replacing current chars of w at index i with the current
                    //chars of the alphabet
                    String wladder = w.substring(0, i) + c + w.substring(i + 1);

                    //check if the constructed ladder is in the dictionary and not equal to w
                    if (dictionary.contains(wladder) && !wladder.equals(w)) {

                        //create new directedge obj with input w as the starting vertex and wladder
                        //as ending vertex. new directedge obj is added to linkedlist
                        edges.add(new DirectedEdge<String>(w, wladder));
                    }
                }
            }
        }
        /*****************
         * TODO: Task 2  *
         * Replace this. *
         *****************/
        //return the linkedlist of directededge objects, which is outgoing edges from w
        return edges;
    }

    /**
     * @param  w  one node/word
     * @param  u  another node/word
     * @return the guessed best cost for getting from {@code w} to {@code u}
     * (the number of differing character positions)
     */
    @Override
    public double guessCost(String w, String u) {
        /*****************
         * TODO: Task 4  *
         * Replace this. *
         *****************/
        return 0;
    }

    @Override
    public String parseNode(String w) {
        return w;
    }

    /**
     * @return a string representation of the graph
     */
    @Override
    public String toString() {
        StringWriter buffer = new StringWriter();
        PrintWriter w = new PrintWriter(buffer);
        w.println("Word ladder graph with " + numNodes() + " words");
        w.println("Alphabet: " + alphabet.stream().map(Object::toString).collect(Collectors.joining()));
        w.println();

        w.println("Random example words with ladder steps:");
        DirectedEdge.printOutgoingEdges(w, this, null);
        return buffer.toString();
    }

}
