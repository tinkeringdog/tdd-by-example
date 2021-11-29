package guru.springframework;

public interface Expression {

    Money reduce(String to, Bank bank);

    Expression plus(Expression addend);

    Expression times(int multiplier);

}
