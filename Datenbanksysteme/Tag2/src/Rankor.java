import tools.CardDeck52;

import java.util.List;

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
            List<CardDeck52.Card> fullHouseCards = isFullHouse(threeCards, hand);
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
            List<CardDeck52.Card> twoPairCards = isTwoPair(pairCards, hand);
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
        List<CardDeck52.Card> allCards = tableCards;
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
        for (int i = 0; i < allCards.size()-1; i++) {
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
            List<CardDeck52.Card> flushCards = null;
            while (i > 0){
                //if card is spade add it to the list
                if(allCards.get(i).sign == CardDeck52.Card.Sign.Spades){
                    flushCards.add(allCards.get(i));
                    i--;
                }
            }


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
        List<CardDeck52.Card> allCards = tableCards;
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //check for straight
        //if any 5 cards have consecutive values
        //start from top to find the highest straight
        int i = allCards.size()-1;
        int countStraight = 0;
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
            //if the current card is not one higher than the next card
            else{
                //reset the count
                countStraight = 0;
            }
            i--;
        }
        return null;
    }
}
