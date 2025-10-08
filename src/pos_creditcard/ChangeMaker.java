package pos_creditcard;

import java.util.Map;

public interface ChangeMaker {
    Map<Double, Integer> makeChange(double changeAmount, Map<Double, Integer> cashBox);
}
