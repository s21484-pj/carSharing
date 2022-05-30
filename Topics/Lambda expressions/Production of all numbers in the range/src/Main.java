import java.util.function.*;


class Operator {

    public static LongBinaryOperator binaryOperator = (x, y) -> {
        long result = x;
        for (int i = (int) x + 1; i <= y; i++) {
            result *= i;
        }
        return result;
    };
}