package guru.springframework;

import java.util.Objects;

public class Pair {

    private final String from;
    private final String to;

    Pair(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Pair pair = (Pair) obj;
        return Objects.equals(this.from, pair.from)
                && Objects.equals(this.to, pair.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.from, this.to);
    }

}
