package guru.springframework;

import java.util.HashMap;

public class Bank {

    private final HashMap<Pair, Integer> rateMap = new HashMap<>();

    public Money reduce(Expression expression, String currency) {
        return expression.reduce(currency, this);
    }

    public void addRate(String from, String to, int rate) {
        this.rateMap.put(new Pair(from, to), rate);
    }

    public int rate(String from, String to) {
        if (from.equals(to)) {
            return 1;
        }
        return rateMap.get(new Pair(from, to));
    }

}
