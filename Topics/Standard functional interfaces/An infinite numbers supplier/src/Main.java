import java.util.function.*;

class FunctionUtils {

    public static Supplier<Integer> getInfiniteRange() {
        // write your code here
        return new Supplier<Integer>() {
            int res = 0;
            @Override
            public Integer get() {
                return res++;
            }
        };
    }

}