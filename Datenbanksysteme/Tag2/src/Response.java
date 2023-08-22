import tools.CardDeck52;

import java.util.List;

public class Response {
    int rank;
    List<CardDeck52.Card> combinationCards;

    //Constructor
    public Response(int rank, List<CardDeck52.Card> combinationCards){
        this.rank = rank;
        this.combinationCards = combinationCards;
    }
}
