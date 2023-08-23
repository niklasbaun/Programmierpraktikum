import org.junit.jupiter.api.Test;
import tools.CardDeck52;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class RankorTest {

    @Test
    void determineRank() {
        //add Cards for a Royal Flush
        List<CardDeck52.Card> tableCards = new ArrayList<>();
        tableCards.add(new CardDeck52.Card(10, CardDeck52.Card.Sign.Hearts));
        tableCards.add(new CardDeck52.Card(11, CardDeck52.Card.Sign.Hearts));
        tableCards.add(new CardDeck52.Card(12, CardDeck52.Card.Sign.Hearts));
        tableCards.add(new CardDeck52.Card(13, CardDeck52.Card.Sign.Hearts));
        tableCards.add(new CardDeck52.Card(8, CardDeck52.Card.Sign.Diamonds));

        //check this hand for royal flush
        TexasHoldemHand hand1 = new TexasHoldemHand();
        hand1.takeDeal(new CardDeck52.Card(14, CardDeck52.Card.Sign.Hearts));
        hand1.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Clubs));

        //check this hand for straight flush
        TexasHoldemHand hand2 = new TexasHoldemHand();
        hand2.takeDeal(new CardDeck52.Card(9, CardDeck52.Card.Sign.Hearts));
        hand2.takeDeal(new CardDeck52.Card(4, CardDeck52.Card.Sign.Spades));

        //check this hand for flush
        TexasHoldemHand hand3 = new TexasHoldemHand();
        hand3.takeDeal(new CardDeck52.Card(6, CardDeck52.Card.Sign.Hearts));
        hand3.takeDeal(new CardDeck52.Card(4, CardDeck52.Card.Sign.Hearts));

        //check this hand for straight
        TexasHoldemHand hand4 = new TexasHoldemHand();
        hand4.takeDeal(new CardDeck52.Card(9, CardDeck52.Card.Sign.Clubs));
        hand4.takeDeal(new CardDeck52.Card(5, CardDeck52.Card.Sign.Spades));

        //check these combinations
        //print out the cards
        System.out.println("Table Cards:");
        for (CardDeck52.Card card : tableCards) {
            System.out.println(card.toString());
        }
        System.out.println("\n Hand 1:");
        for (CardDeck52.Card card : hand1.get()) {
            System.out.println(card.toString());
        }
        assertEquals(10, Rankor.determineRank(tableCards, hand1).rank);  //Royal Flush
        System.out.println("\n Hand 2:");
        for (CardDeck52.Card card : hand2.get()) {
            System.out.println(card.toString());
        }
        assertEquals(9, Rankor.determineRank(tableCards, hand2).rank);   //Straight Flush
        System.out.println("\n Hand 3:");
        for (CardDeck52.Card card : hand3.get()) {
            System.out.println(card.toString());
        }
        assertEquals(6, Rankor.determineRank(tableCards, hand3).rank);   //Flush
        System.out.println("\n Hand 4:");
        for (CardDeck52.Card card : hand4.get()) {
            System.out.println(card.toString());
        }
        assertEquals(5, Rankor.determineRank(tableCards, hand4).rank);   //Straight

        //add Cards for a pair/three/Four of a Kind
        List<CardDeck52.Card> tableCards2 = new ArrayList<>();
        tableCards2.add(new CardDeck52.Card(10, CardDeck52.Card.Sign.Hearts));
        tableCards2.add(new CardDeck52.Card(10, CardDeck52.Card.Sign.Diamonds));
        tableCards2.add(new CardDeck52.Card(7, CardDeck52.Card.Sign.Spades));
        tableCards2.add(new CardDeck52.Card(6, CardDeck52.Card.Sign.Hearts));
        tableCards2.add(new CardDeck52.Card(8, CardDeck52.Card.Sign.Diamonds));

        //check this hand for two pair
        TexasHoldemHand hand5 = new TexasHoldemHand();
        hand5.takeDeal(new CardDeck52.Card(7, CardDeck52.Card.Sign.Hearts));
        hand5.takeDeal(new CardDeck52.Card(8, CardDeck52.Card.Sign.Clubs));

        //check this hand for three of a kind
        TexasHoldemHand hand6 = new TexasHoldemHand();
        hand6.takeDeal(new CardDeck52.Card(10, CardDeck52.Card.Sign.Clubs));
        hand6.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Spades));

        //check this hand for four of a kind
        TexasHoldemHand hand7 = new TexasHoldemHand();
        hand7.takeDeal(new CardDeck52.Card(10, CardDeck52.Card.Sign.Clubs));
        hand7.takeDeal(new CardDeck52.Card(10, CardDeck52.Card.Sign.Spades));

        //check this hand for pair
        TexasHoldemHand hand8 = new TexasHoldemHand();
        hand8.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Clubs));
        hand8.takeDeal(new CardDeck52.Card(13, CardDeck52.Card.Sign.Spades));

        //check this hand as full house
        TexasHoldemHand hand9 = new TexasHoldemHand();
        hand9.takeDeal(new CardDeck52.Card(10, CardDeck52.Card.Sign.Clubs));
        hand9.takeDeal(new CardDeck52.Card(8, CardDeck52.Card.Sign.Spades));

        //check these combinations
        //print out the cards
        System.out.printf("-------------------------\n");
        System.out.println("\nTable Cards:");
        for (CardDeck52.Card card : tableCards2) {
            System.out.println(card.toString());
        }
        System.out.println("\n Hand 5:");
        for (CardDeck52.Card card : hand5.get()) {
            System.out.println(card.toString());
        }
        assertEquals(3, Rankor.determineRank(tableCards2, hand5).rank);   //Two Pair

        System.out.println("\n Hand 6:");
        for (CardDeck52.Card card : hand6.get()) {
            System.out.println(card.toString());
        }
        assertEquals(4, Rankor.determineRank(tableCards2, hand6).rank);   //Three of a Kind

        System.out.println("\n Hand 7:");
        for (CardDeck52.Card card : hand7.get()) {
            System.out.println(card.toString());
        }
        assertEquals(8, Rankor.determineRank(tableCards2, hand7).rank);   //Four of a Kind

        System.out.println("\n Hand 8:");
        for (CardDeck52.Card card : hand8.get()) {
            System.out.println(card.toString());
        }
        assertEquals(2, Rankor.determineRank(tableCards2, hand8).rank);   //Pair

        System.out.println("\n Hand 9:");
        for (CardDeck52.Card card : hand9.get()) {
            System.out.println(card.toString());
        }
        assertEquals(7, Rankor.determineRank(tableCards2, hand9).rank);   //Full House
    }
}