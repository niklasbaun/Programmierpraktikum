import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TexasHoldemCombinationTest {

    @Test
    void compareTo() {

    }

    @Test
    void generate() {
        //generate 1000 random combinations
        //collect them in a map and show results
        TexasHoldemCombination.generate().limit(1000);
    }
}