import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine()
                .toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s+]", "")
                .split(" ");

        Arrays.stream(words)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEach(word -> System.out.println(word.getKey()));

//        Arrays.stream(new Scanner(System.in).nextLine().toLowerCase().split("\\W+"))
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .limit(10)
//                .forEach(x -> System.out.println(x.getKey()));
    }
}