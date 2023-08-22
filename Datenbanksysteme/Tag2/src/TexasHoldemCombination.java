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
    //what combination is it
    CombinationType combinationType;
    //what cards are used in the combination
    List<CardDeck52.Card> combinationCards;
    List<CardDeck52.Card> handCards;

    // a)
    //constructor
    TexasHoldemCombination(List<CardDeck52.Card> tableCards, TexasHoldemHand hand) {
        int rank = Rankor.determineRank(tableCards, hand);
        if(rank == 1){
            this.combinationType = CombinationType.HighCard;
        }
        if (rank == 2){
            this.combinationType = CombinationType.OnePair;
        }
        if (rank == 3){
            this.combinationType = CombinationType.TwoPair;
        }
        if (rank == 4){
            this.combinationType = CombinationType.ThreeOfAKind;
        }
        if (rank == 5){
            this.combinationType = CombinationType.Straight;
        }
        if (rank == 6){
            this.combinationType = CombinationType.Flush;
        }
        if (rank == 7){
            this.combinationType = CombinationType.FullHouse;
        }
        if (rank == 8){
            this.combinationType = CombinationType.FourOfAKind;
        }
        if (rank == 9){
            this.combinationType = CombinationType.StraightFlush;
        }
        if (rank == 10){
            this.combinationType = CombinationType.RoyalFlush;
        }
        //get the cards used in the combination
        this.combinationCards = Rankor.getCombinationCards(tableCards, hand);
        this.handCards = Arrays.asList(hand.get());
    }

    // b)

    /**
     * method to compare the combination of two hands
     * @param that the other hand
     * @return 0 if equal, 1 if this is better, -1 if that is better
     */
    @Override
    public final int compareTo(TexasHoldemCombination that) {
        //determine the rank of my hand
        CombinationType myRank = this.combinationType;
        //determine the rank of the other hand
        CombinationType otherRank = that.combinationType;
        //compare the ranks
        if(myRank.compareTo(otherRank) > 0){
            return 1;
        }
        if(myRank.compareTo(otherRank) < 0){
            return -1;
        }
        //if the ranks are equal, compare the highest cards in the combination
        //for this combine the cards of the combination with hand cards and sort them
        List<CardDeck52.Card> myCards = this.combinationCards;
        myCards.addAll(this.handCards);
        Collections.sort(myCards);

        List<CardDeck52.Card> otherCards = that.combinationCards;
        otherCards.addAll(that.handCards);
        Collections.sort(otherCards);

        //compare the highest cards if they are the same compare the second highest cards and so on
        for (int i = myCards.size() - 1; i >= 0; i--) {
            if(myCards.get(i).compareTo(otherCards.get(i)) > 0){
                return 1;
            }
            if(myCards.get(i).compareTo(otherCards.get(i)) < 0){
                return -1;
            }
        }
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
