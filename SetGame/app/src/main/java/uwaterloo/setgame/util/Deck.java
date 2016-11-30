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

    public Deck(ArrayList<Card> cards){
        this.cards=cards;
    }

    public ArrayList<Set> getSets(){
        ArrayList<Set> sets = new ArrayList<Set>();
        try {
            for(int i=0;i<cards.size()-2;i++) {
                for (int j = i+1; j < cards.size()-1; j++) {
                    for (int k = j+1; k < cards.size(); k++) {

                        Set set = new Set(new Card[]{cards.get(i), cards.get(j), cards.get(k)});
                        if (set.isSet()) {
                            sets.add(set);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sets;
    }

    public Deck drawCards(int number){
        ArrayList<Card> drawnCards = new ArrayList<>();
        if(number>cards.size()){
            return null;
        }
        for(int i=0; i<number; i++){
            drawnCards.add(cards.remove(0));
        }
        return new Deck(drawnCards);
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
