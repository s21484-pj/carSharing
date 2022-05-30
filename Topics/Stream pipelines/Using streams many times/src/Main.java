import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FunctionUtils {

    public static <T> Supplier<Stream<T>> saveStream(Stream<T> stream){
        // write your code here
        Collection<T> collection = stream.collect(Collectors.toList());
        Supplier<Stream<T>> supplier = collection::stream;
        return supplier;
    }
}