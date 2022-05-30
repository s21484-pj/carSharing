import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.*;

class BadWordsDetector {

    private static Stream<String> createBadWordsDetectingStream(String text, List<String> badWords) {
        // write your code here
        String[] a = text.split(" ");
        Set<String> words = Stream.of(a).collect(Collectors.toSet());
        words.removeIf(b -> !badWords.contains(b));
        return words.stream().sorted();
    }

    /* Do not change the code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] parts = scanner.nextLine().split(";");

        // the first part is a text
        String text = parts[0];

        // the second part is a bad words dictionary
        List<String> dict = parts.length > 1 ?
                Arrays.asList(parts[1].split(" ")) :
                Collections.singletonList("");

        System.out.println(createBadWordsDetectingStream(text, dict).collect(Collectors.toList()));
    }

}