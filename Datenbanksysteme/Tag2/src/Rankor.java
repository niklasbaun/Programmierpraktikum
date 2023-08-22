import tools.CardDeck52;

import java.util.List;

public class Rankor {

    /**
     * method to determine the rank of a hand
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the rank of the hand as an integer
     */
    public static int determineRank(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        //check if the hand is a royal flush/ straight flush/ flush or straight
        int combSFOrRF = checkAnyKindOfFlush(tableCards, hand);

        return 0;
    }

    /**
     * helper method to check if the hand is a royal flush/ straight flush/ flush or straight
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the rank of the hand as an integer
     */
    public static int checkAnyKindOfFlush(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        boolean isFlush = false;
        boolean isStraight = false;
        //combine all cards in one list
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
        if (countSpade == 5 || countHearts == 5 || countDiamonds == 5 || countClubs == 5){
            isFlush = true;
        }
        //check for straight
        //if any 5 cards have consecutive values
        for (int i = 0; i < allCards.size() -4; i++){
            if (allCards.get(i).value == allCards.get(i+1).value -1 && allCards.get(i).value == allCards.get(i+2).value -2 && allCards.get(i).value == allCards.get(i+3).value -3 && allCards.get(i).value == allCards.get(i+4).value -4){
                isStraight = true;
            }
        }
        //if both is true -> straight flush


        return 0;
    }
}
