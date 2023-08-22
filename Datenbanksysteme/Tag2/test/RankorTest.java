import org.junit.jupiter.api.Test;
import tools.CardDeck52;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class RankorTest {

    @Test
    void determineRank() {
        //create a set of table cards and three hands
        CardDeck52 deck = new CardDeck52();
        //add Cards for a Royal Flush
        List<CardDeck52.Card> tableCards = deck.deal(5);
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
        System.out.println("Hand 1:");
        for (CardDeck52.Card card : hand1.get()) {
            System.out.println(card.toString());
        }
        assertEquals(10, Rankor.determineRank(tableCards, hand1));  //Royal Flush
        System.out.println("Hand 2:");
        for (CardDeck52.Card card : hand2.get()) {
            System.out.println(card.toString());
        }
        assertEquals(9, Rankor.determineRank(tableCards, hand2));   //Straight Flush
        System.out.println("Hand 3:");
        for (CardDeck52.Card card : hand3.get()) {
            System.out.println(card.toString());
        }
        assertEquals(6, Rankor.determineRank(tableCards, hand3));   //Flush
        System.out.println("Hand 4:");
        for (CardDeck52.Card card : hand4.get()) {
            System.out.println(card.toString());
        }
        assertEquals(5, Rankor.determineRank(tableCards, hand4));   //Straight

    }

    @Test
    void getCombinationCards() {
    }
}