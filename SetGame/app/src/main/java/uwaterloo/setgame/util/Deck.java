package uwaterloo.setgame.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import uwaterloo.setgame.util.cardfeatures.Color;
import uwaterloo.setgame.util.cardfeatures.Fill;
import uwaterloo.setgame.util.cardfeatures.Shape;

/**
 * Created by Stephen on 11/28/2016.
 */

public class Deck {
    ArrayList<Card> cards = new ArrayList<Card>();

    public Deck() {
        for(int num=1;num<4;num++){
            for(int shape=0;shape<3;shape++){
                for(int fill=0;fill<3;fill++){
                    for(int color=0;color<3;color++){
                        cards.add(new Card(new Shape(shape),new Color(color),new Fill(fill),num));
                    }
                }
            }
        }
    }

    public void shuffle(){
        Random rand = new Random();
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();
        cards.clear();

        //loop through all sorted cards
        cards.add(sortedCards.get(0));
        sortedCards.remove(0);
        while(sortedCards.size()>0){
            int randIndex = rand.nextInt(sortedCards.size());
            cards.add(rand.nextInt(cards.size()),sortedCards.get(randIndex));
            sortedCards.remove(randIndex);

        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    public Card getCard(int index) {
        return cards.get(index);
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
