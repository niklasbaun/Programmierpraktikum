import tools.CardDeck52;

import java.util.*;

public class Rankor {

    /**
     * method to determine the rank of a hand
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the rank of the hand as an integer
     */
    public static Response determineRank(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        //check if the hand is a flush
        List<CardDeck52.Card> flushCards = isFlush(tableCards, hand);
        if(flushCards != null){
            //if flush check for straight
            List<CardDeck52.Card> straightCards = isStraight(flushCards, hand);
            if(straightCards != null){
                //if straight check for royal flush
                List<CardDeck52.Card> royalFlushCards = isRoyalFlush(straightCards, hand);
                if(royalFlushCards != null){
                    //if royal flush return 10
                    return new Response(10, royalFlushCards);
                }
                //else should be Straight Flush
                else{
                    return new Response(9, straightCards);
                }
            } else {
                //should be flush
                return new Response(6, flushCards);
            }
        }
        //check if the hand is a straight
        List<CardDeck52.Card> straightCards = isStraight(tableCards, hand);
        if(straightCards != null){
            //is straight
            return new Response(5, straightCards);
        }
        //check for four of a kind
        List<CardDeck52.Card> fourCards = isFourOfAKind(tableCards, hand);
        if(fourCards != null){
            //is four of a kind
            return new Response(8, fourCards);
        }
        //check for three of a kind
        List<CardDeck52.Card> threeCards = isThreeOfAKind(tableCards, hand);
        if (threeCards != null){
            //check for full house
            List<CardDeck52.Card> fullHouseCards = isFullHouse(threeCards, tableCards, hand);
            if(fullHouseCards != null){
                //is full house
                return new Response(7, fullHouseCards);
            }
            //is three of a kind
            return new Response(4, threeCards);
        }
        //check for one pair
        List<CardDeck52.Card> pairCards = isOnePair(tableCards, hand);
        if(pairCards != null){
            //check for two pair
            List<CardDeck52.Card> twoPairCards = isTwoPair(pairCards, tableCards, hand);
            if(twoPairCards != null){
                //is two pair
                return new Response(3, twoPairCards);
            }
            //is one pair
            return new Response(2, pairCards);
        }
        //check for high card
        List<CardDeck52.Card> highCardCards = isHighCard(tableCards, hand);
        if(highCardCards != null){
            //is high card
            return new Response(1, highCardCards);
        }
        //if none return Error Response
        return new Response(0, null);
    }


    /**
     * helper method to check for flush
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the cards used in the combination
     */
    private static List<CardDeck52.Card> isFlush(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //check for flush
        //if any 5 cards have the same sign
        int countSpade = 0;
        int countHearts = 0;
        int countDiamonds = 0;
        int countClubs = 0;
        for (int i = 0; i < allCards.size(); i++) {
            if(allCards.get(i).sign == CardDeck52.Card.Sign.Spades){
                countSpade++;
            }
            if(allCards.get(i).sign == CardDeck52.Card.Sign.Hearts){
                countHearts++;
            }
            if(allCards.get(i).sign == CardDeck52.Card.Sign.Diamonds){
                countDiamonds++;
            }
            if(allCards.get(i).sign == CardDeck52.Card.Sign.Clubs){
                countClubs++;
            }
        }
        //if any of the counts is 5 -> flush
        if (countSpade >= 5 ){
            //search for the 5 spades with the highest value
            int i = allCards.size()-1;
            int count = 0;
            List<CardDeck52.Card> flushCards = new ArrayList<>();
            while (i > 0){
                //if card is spade add it to the list
                if(allCards.get(i).sign == CardDeck52.Card.Sign.Spades && count <= 5){
                    flushCards.add(allCards.get(i));
                    count++;
                }
                i--;
            }
            return flushCards;
        }
        if (countHearts >= 5 ){
            //search for the 5 hearts with the highest value
            int i = allCards.size()-1;
            int count = 0;
            List<CardDeck52.Card> flushCards = new ArrayList<>();
            while (i >=0){
                //if card is heart add it to the list
                if(allCards.get(i).sign == CardDeck52.Card.Sign.Hearts && count <= 5){
                    flushCards.add(allCards.get(i));
                    count++;
                }
                i--;
            }
            return flushCards;
        }
        if (countDiamonds >= 5 ){
            //search for the 5 diamonds with the highest value
            int i = allCards.size()-1;
            int count = 0;
            List<CardDeck52.Card> flushCards = new ArrayList<>();
            while (i > 0){
                //if card is diamond add it to the list
                if(allCards.get(i).sign == CardDeck52.Card.Sign.Diamonds && count <= 5){
                    flushCards.add(allCards.get(i));
                    count++;
                }
                i--;
            }
            return flushCards;
        }
        if (countClubs >= 5 ){
            //search for the 5 clubs with the highest value
            int i = allCards.size()-1;
            int count = 0;
            List<CardDeck52.Card> flushCards = new ArrayList<>();
            while (i >= 0){
                //if card is club add it to the list
                if(allCards.get(i).sign == CardDeck52.Card.Sign.Clubs && count <= 5){
                    flushCards.add(allCards.get(i));
                    count++;
                }
                i--;
            }
            return flushCards;
        }
        return null;
    }

    /**
     * helper method to check for straight
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the rank of the hand as an integer
     */
    private static List<CardDeck52.Card> isStraight(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //check for straight
        //if any 5 cards have consecutive values
        //start from top to find the highest straight
        int i = allCards.size()-1;
        int countStraight = 1;
        while (i > 0){
            //if the current card is one higher than the next card
            if(allCards.get(i).value == allCards.get(i-1).value + 1){
                //increase the count
                countStraight++;
                //if the count is 5 -> straight
                if(countStraight == 5){
                    return allCards.subList(i-1, i+4);
                }
            }
            //if the current card is not one higher than the next card, but also not the same
            else if(allCards.get(i).value != allCards.get(i-1).value && allCards.get(i).value != allCards.get(i-1).value + 1){
                //reset the count
                countStraight = 1;
            }
            i--;
        }
        return null;
    }

    /**
     * helper method to check if sth is a royal flush
     * @param straightCards the cards used in the straight
     * @param hand the hand of the player
     * @return the rank of the hand as an integer and the used cards
     */
    private static List<CardDeck52.Card> isRoyalFlush(List<CardDeck52.Card> straightCards, TexasHoldemHand hand) {
        //combine all Cards
        List<CardDeck52.Card> allCards = new ArrayList<>(straightCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);

        //check if highest card is an ace
        if(allCards.get(allCards.size()-1).value == 14){
            //is royalFlush
            return straightCards;
        }
        return null;
    }

    /**
     * helper method to check for four of a kind
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the cards used in the combination
     */
    private static List<CardDeck52.Card> isFourOfAKind(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //check for four of a kind
        //if any 4 cards have the same value -> four of a kind
        Map<Integer, Integer> valueCount = new HashMap<>();
        for(int i = 0; i < allCards.size(); i++){
            if(valueCount.containsKey(allCards.get(i).value)){
                valueCount.put(allCards.get(i).value, valueCount.get(allCards.get(i).value) + 1);
            } else {
                valueCount.put(allCards.get(i).value, 1);
            }
        }
        //check if any value has a count of 4
        for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
            if(entry.getValue() == 4){
                //search for the 4 cards with the same value
                int j = allCards.size()-1;
                List<CardDeck52.Card> fourCards = new ArrayList<>();
                while (j > 0){
                    //if card has the same value add it to the list
                    if(allCards.get(j).value == entry.getKey()){
                        fourCards.add(allCards.get(j));
                    }
                    j--;
                }
                return fourCards;
            }
        }
        return null;
    }

    /**
     * helper method to check for three of a kind
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the cards used in the combination
     */
    private static List<CardDeck52.Card> isThreeOfAKind(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //check for three of a kind
        //create Map to count the values
        //if any 3 cards have the same value -> three of a kind
        Map<Integer, Integer> valueCount = new HashMap<>();
        for(int i = 0; i < allCards.size(); i++){
            if(valueCount.containsKey(allCards.get(i).value)){
                valueCount.put(allCards.get(i).value, valueCount.get(allCards.get(i).value) + 1);
            } else {
                valueCount.put(allCards.get(i).value, 1);
            }
        }
        //check if any value has a count of 3
        for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
            if(entry.getValue() == 3){
                //search for the 3 cards with the same value
                int j = allCards.size()-1;
                List<CardDeck52.Card> threeCards = new ArrayList<>();
                while (j > 0){
                    //if card has the same value add it to the list
                    if(allCards.get(j).value == entry.getKey()){
                        threeCards.add(allCards.get(j));
                    }
                    j--;
                }
                return threeCards;
            }
        }
        return null;
    }

    /**
     * helper method to check for full house
     * @param threeCards the cards used in the three of a kind
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the cards used in the combination
     */
    private static List<CardDeck52.Card> isFullHouse(List<CardDeck52.Card> threeCards, List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        //combine hand and table cards
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //remove the three of a kind from the list
        for (int i = 0; i < allCards.size()-1; i++) {
            if(allCards.get(i).value == threeCards.get(0).value){
                allCards.remove(i);
            }
        }
        //check if in the remaining cards is a pair
        //create Map to count the values
        //if any 2 cards have the same value -> pair
        Map<Integer, Integer> valueCount = new HashMap<>();
        for(int i = 0; i < allCards.size(); i++){
            if(valueCount.containsKey(allCards.get(i).value)){
                valueCount.put(allCards.get(i).value, valueCount.get(allCards.get(i).value) + 1);
            } else {
                valueCount.put(allCards.get(i).value, 1);
            }
        }
        //check if any value has a count of 2
        for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
            if(entry.getValue() == 2){
                //search for the 2 cards with the same value
                int j = allCards.size()-1;
                List<CardDeck52.Card> pairCards = new ArrayList<>();
                while (j > 0){
                    //if card has the same value add it to the list
                    if(allCards.get(j).value == entry.getKey()){
                        pairCards.add(allCards.get(j));
                    }
                    j--;
                }
                //add the three of a kind to the list
                pairCards.addAll(threeCards);
                return pairCards;
            }
        }
        return null;
    }

    /**
     * helper method to check for one pair
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the cards used in the combination
     */
    private static List<CardDeck52.Card> isOnePair (List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        //combine hand and table cards
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);

        //check for one pair
        //create Map to count the values
        //if any 2 cards have the same value -> pair
        Map<Integer, Integer> valueCount = new HashMap<>();
        for(int i = 0; i < allCards.size(); i++){
            if(valueCount.containsKey(allCards.get(i).value)){
                valueCount.put(allCards.get(i).value, valueCount.get(allCards.get(i).value) + 1);
            } else {
                valueCount.put(allCards.get(i).value, 1);
            }
        }
        //check if any value has a count of 2
        for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
            if(entry.getValue() == 2){
                //search for the 2 cards with the same value
                int j = allCards.size()-1;
                List<CardDeck52.Card> pairCards = new ArrayList<>();
                while (j > 0){
                    //if card has the same value add it to the list
                    if(allCards.get(j).value == entry.getKey()){
                        pairCards.add(allCards.get(j));
                    }
                    j--;
                }
                return pairCards;
            }
        }
        return null;
    }

    /**
     * helper method to check for two pair
     * @param pairCards the cards used in the pair
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the cards used in the combination
     */
    private static List<CardDeck52.Card> isTwoPair (List<CardDeck52.Card> pairCards, List<CardDeck52.Card> tableCards, TexasHoldemHand hand) {
        //combine hand and table cards
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //remove the pair from the list
        for (int i = 0; i < allCards.size()-1; i++) {
            if(allCards.get(i).value == pairCards.get(0).value){
                allCards.remove(i);
            }
        }
        //check if in the remaining cards is a pair
        //create Map to count the values
        //if any 2 cards have the same value -> pair
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (int i =0; i < allCards.size(); i++){
            if(valueCount.containsKey(allCards.get(i).value)){
                valueCount.put(allCards.get(i).value, valueCount.get(allCards.get(i).value) + 1);
            } else {
                valueCount.put(allCards.get(i).value, 1);
            }
        }
        //check if any value has a count of 2
        for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
            if(entry.getValue() == 2){
                //search for the 2 cards with the same value
                int j = allCards.size()-1;
                List<CardDeck52.Card> pair2Cards = new ArrayList<>();
                while (j > 0){
                    //if card has the same value add it to the list
                    if(allCards.get(j).value == entry.getKey()){
                        pair2Cards.add(allCards.get(j));
                    }
                    j--;
                }
                //add the other pair to the list
                pair2Cards.addAll(pairCards);
                return pair2Cards;
            }
        }
        return null;
    }

    /**
     * helper method to check for the highest card
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the cards used in the combination
     */
    private static List<CardDeck52.Card> isHighCard (List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        //combine hand and table cards
        List<CardDeck52.Card> allCards = new ArrayList<>(tableCards);
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //return the highest card
        return allCards.subList(allCards.size()-1, allCards.size());
    }
}
