public class savePoint {
    /**
     * helper method to check if the hand is a royal flush/ straight flush/ flush or straight
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the rank of the hand as an integer
     */
    private static int checkAnyKindOfFlush(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
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
        //save the highest card of the straight
        int highestCard = 0;
        int countStraight = 0;
        //if any 5 cards have consecutive values
        //start from top to find the highest straight
        int i = allCards.size()-1;
        while (i > 0){
            //if the current card is one higher than the next card
            if(allCards.get(i).value == allCards.get(i-1).value + 1){
                //increase the count
                countStraight++;
                //if the count is 5 -> straight
                if(countStraight == 5){
                    isStraight = true;
                    highestCard = allCards.get(i).value;
                }
            }
            //if the current card is not one higher than the next card
            else{
                //reset the count
                countStraight = 0;
            }
            i--;
        }

        //if both is true -> straight flush
        if(isStraight && isFlush){
            //if highest card is 14 -> royal flush
            if(highestCard == 14){
                return 10;
            }
            //else straight flush
            else{
                return 9;
            }
        }
        //if only flush -> flush
        //if only straight -> straight
        if(isFlush && !isStraight){
            return 6;
        }
        if (!isFlush && isStraight){
            return 5;
        }
        //if none return 0
        return 0;
    }

    /**
     * helper method to check for other combinations not including flushes and straights
     * e.g.: one pair, two pair, three of a kind, full house, four of a kind
     * @param tableCards the cards on the table
     * @param hand the hand of the player
     * @return the rank of the hand as an integer
     */
    private static int checkOtherCombinations(List<CardDeck52.Card> tableCards, TexasHoldemHand hand){
        //combine all cards in one list
        List<CardDeck52.Card> allCards = tableCards;
        allCards.add(hand.card1);
        allCards.add(hand.card2);
        //sort that list
        allCards.sort(CardDeck52.Card::compareTo);
        //check for four of a kind
        //if any 4 cards have the same value
        int countFour = 0;
        for (int i = 0; i < allCards.size()-1; i++) {
            if(allCards.get(i).value == allCards.get(i+1).value){
                countFour++;
            } else {
                //reset if not consecutive
                countFour = 0;
            }
        }
        if (countFour == 4){
            return 8;
        }
        //check for three of a kind
        //if any 3 cards have the same value
        boolean isThree = false;
        int countThree = 0;
        int cardValThree = 0;
        for (int i = 0; i < allCards.size()-1; i++) {
            if(allCards.get(i).value == allCards.get(i+1).value){
                countThree++;
                if(countThree == 3){
                    isThree = true;
                    cardValThree = allCards.get(i).value;
                }
            } else {
                //reset if not consecutive
                countThree = 0;
            }
        }
        //check for full house if three of a kind is true
        if(isThree){
            //remove the three of a kind from the list
            for (int i = 0; i < allCards.size()-1; i++) {
                if(allCards.get(i).value == cardValThree){
                    allCards.remove(i);
                }
            }
            //check if in the remaining cards is a pair
            int countPair = 0;
            for (int i = 0; i < allCards.size()-1; i++) {
                if(allCards.get(i).value == allCards.get(i+1).value){
                    countPair++;
                } else {
                    //reset if not consecutive
                    countPair = 0;
                }
            }
            //if pair is true -> full house
            if(countPair == 2){
                return 7;
            }
        }
        //check for pair
        int countPair = 0;
        int cardValPair = 0;
        for (int i = 0; i < allCards.size()-1; i++) {
            if(allCards.get(i).value == allCards.get(i+1).value){
                countPair++;
                if(countPair == 2){
                    cardValPair = allCards.get(i).value;
                }
            } else {
                //reset if not consecutive
                countPair = 0;
            }
        }
        //if pair is true check for two pair
        if(countPair == 2){
            //remove the pair from the list
            for (int i = 0; i < allCards.size()-1; i++) {
                if(allCards.get(i).value == cardValPair){
                    allCards.remove(i);
                }
            }
            //check if in the remaining cards is a pair
            int countPair2 = 0;
            for (int i = 0; i < allCards.size()-1; i++) {
                if(allCards.get(i).value == allCards.get(i+1).value){
                    countPair2++;
                } else {
                    //reset if not consecutive
                    countPair2 = 0;
                }
            }
            //if pair is true -> two pair
            if(countPair2 == 2 && countPair == 2){
                return 3;
            }
            //if pair2 is false -> one pair
            else if(countPair2 != 2 && countPair == 2){
                return 2;
            }
        }
        //if none return 1 is high card
        return 1;
    }
}
