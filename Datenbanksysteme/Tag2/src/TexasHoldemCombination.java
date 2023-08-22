import tools.CardDeck52;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public final class TexasHoldemCombination implements Comparable<TexasHoldemCombination> {
    public enum CombinationType {
        HighCard,
        OnePair,
        TwoPair,
        ThreeOfAKind,
        Straight,
        Flush,
        FullHouse,
        FourOfAKind,
        StraightFlush,
        RoyalFlush
    }

    CombinationType combinationType;
    List<CardDeck52.Card> combinationCards;

    // a)
    //constructor
    TexasHoldemCombination(List<CardDeck52.Card> tableCards, TexasHoldemHand hand) {
        int rank = Rankor.determineRank(tableCards, hand);
        if(rank == 1){
            CombinationType combinationType = CombinationType.HighCard;
        }
        if (rank == 2){
            CombinationType combinationType = CombinationType.OnePair;
        }
        if (rank == 3){
            CombinationType combinationType = CombinationType.TwoPair;
        }
        if (rank == 4){
            CombinationType combinationType = CombinationType.ThreeOfAKind;
        }
        if (rank == 5){
            CombinationType combinationType = CombinationType.Straight;
        }
        if (rank == 6){
            CombinationType combinationType = CombinationType.Flush;
        }
        if (rank == 7){
            CombinationType combinationType = CombinationType.FullHouse;
        }
        if (rank == 8){
            CombinationType combinationType = CombinationType.FourOfAKind;
        }
        if (rank == 9){
            CombinationType combinationType = CombinationType.StraightFlush;
        }
        if (rank == 10){
            CombinationType combinationType = CombinationType.RoyalFlush;
        }
    }

    // b)

    /**
     * method to compare the combination of two hands
     * @param that the other hand
     * @return 0 if equal, 1 if this is better, -1 if that is better
     */
    @Override
    public final int compareTo(TexasHoldemCombination that) {


        return 0;
    }

    // c)

    /**
     * method to generate an endless stream of all possible combinations
     * @return the stream
     */
    public static Stream<TexasHoldemCombination> generate() {
        // TODO

        return null;
    }

    public static void main(String[] args) {
        CardDeck52 deck = new CardDeck52();
        boolean hasTable = Math.random() >= 0.5;
        List<CardDeck52.Card> tableCards = hasTable ?
                deck.deal(ThreadLocalRandom.current().nextInt(3, 5 + 1)) :
                Collections.emptyList();

        TexasHoldemHand hand = new TexasHoldemHand();
        TexasHoldemHand hand2 = new TexasHoldemHand();
        hand.takeDeal(deck.deal());
        hand2.takeDeal(deck.deal());
        hand.takeDeal(deck.deal());
        hand2.takeDeal(deck.deal());

        Stream.of(hand.eval(tableCards), hand2.eval(tableCards))
                .sorted(Comparator.reverseOrder())
                .forEach(combination -> System.out.println(
                        "CombinationType: " + combination.combinationType +
                        ", CombinationCards: " + combination.combinationCards));

        TexasHoldemCombination combination = hand.eval(tableCards);
        System.out.println("Table Cards (" + tableCards.size() + "): " + tableCards);
        System.out.println("Hand: " + Arrays.toString(hand.get()));
        System.out.println("Combination (" + combination.combinationCards.size() + "): " + combination.combinationType + " -> " + combination.combinationCards);
    }
}
