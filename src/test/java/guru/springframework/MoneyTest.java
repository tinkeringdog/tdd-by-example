package guru.springframework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MoneyTest {

    @Test
    public void testMultiplication() {
        Money dollar = Money.dollar(5);
        assertEquals(Money.dollar(10), dollar.times(2));
        assertEquals(Money.dollar(15), dollar.times(3));

        Money franc = Money.franc(5);
        assertEquals(Money.franc(10), franc.times(2));
        assertEquals(Money.franc(15), franc.times(3));
    }

    @Test
    void testEquality() {
        assertEquals(Money.dollar(5), Money.dollar(5));
        assertNotEquals(Money.dollar(5), Money.dollar(10));
        assertEquals(Money.franc(5), Money.franc(5));
        assertNotEquals(Money.franc(5), Money.franc(10));
        assertNotEquals(Money.dollar(5), Money.franc(5));
    }

    @Test
    void testCurrency() {
        assertEquals("USD", Money.dollar(10).currency());
        assertEquals("CHF", Money.franc(10).currency());
    }

    @Test
    void testAddition() {
        Money fiveDollar = Money.dollar(5);
        Expression expression = fiveDollar.plus(fiveDollar);
        Bank bank = new Bank();
        Money reduced = bank.reduce(expression, "USD");
        assertEquals(Money.dollar(10), reduced);
    }

    @Test
    void testPlusReturnsSum() {
        Money fiveDollar = Money.dollar(5);
        Expression sumExp = fiveDollar.plus(fiveDollar);
        Sum sum = (Sum) sumExp;
        assertEquals(Money.dollar(5), sum.augend);
        assertEquals(Money.dollar(5), sum.addend);
    }

    @Test
    void testReduceSum() {
        Money fiveDollar = Money.dollar(5);
        Expression sum = fiveDollar.plus(fiveDollar);
        Bank bank = new Bank();
        assertEquals(Money.dollar(10), bank.reduce(sum, "USD"));
    }

    @Test
    void testReduceMoney() {
        Money fiveDollar = Money.dollar(5);
        Bank bank = new Bank();
        assertEquals(Money.dollar(5), bank.reduce(fiveDollar, "USD"));
    }

    @Test
    void testReduceMoneyDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression result = bank.reduce(Money.franc(2), "USD");
        assertEquals(Money.dollar(1), result);
    }

    @Test
    void testIdentityRate() {
        assertEquals(1, new Bank().rate("USD", "USD"));
        assertEquals(1, new Bank().rate("CHF", "CHF"));
    }

    @Test
    void testAdditionMixedCurrency() {
        Expression fiveDollar = Money.dollar(5);
        Expression tenFranc = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveDollar.plus(tenFranc), "USD");
        assertEquals(Money.dollar(10), result);
    }

    @Test
    void testSumAddition() {
        Expression fiveDollar = Money.dollar(5);
        Expression tenFranc = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(
                fiveDollar.plus(tenFranc).plus(fiveDollar), "USD");
        assertEquals(Money.dollar(15), result);
    }

    @Test
    void testSumTimes() {
        Expression fiveDollar = Money.dollar(5);
        Expression tenFranc = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(
                fiveDollar.plus(tenFranc).times(2), "USD");
        assertEquals(Money.dollar(20), result);
    }

}
