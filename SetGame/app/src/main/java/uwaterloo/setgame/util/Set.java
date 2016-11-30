package uwaterloo.setgame.util;

import java.util.Arrays;

/**
 * Holds information for a set of 3 cards
 */
public class Set {
    Card[] cards = new Card[3];
    public Set(Card[] cards) throws Exception {
        if(cards.length!=3){
            throw new Exception("Not the right amount of cards. There should be 3 cards, but there are " + cards.length);
        }
        this.cards=cards;
    }

    public boolean isSet(){
        //color
        if(!((    cards[0].getColor().getID() == cards[1].getColor().getID()
                && cards[1].getColor().getID()== cards[2].getColor().getID())
            ||(cards[0].getColor().getID() != cards[1].getColor().getID()
                && cards[1].getColor().getID()!= cards[2].getColor().getID()
                && cards[0].getColor().getID()!= cards[2].getColor().getID()))){
            return false;
        }
        //fill
        if(!((    cards[0].getFill().getID() == cards[1].getFill().getID()
                && cards[1].getFill().getID()== cards[2].getFill().getID())
                ||(cards[0].getFill().getID() != cards[1].getFill().getID()
                && cards[1].getFill().getID()!= cards[2].getFill().getID()
                && cards[0].getFill().getID()!= cards[2].getFill().getID()))){
            return false;
        }
        //number
        if(!((    cards[0].getNumber() == cards[1].getNumber()
                && cards[1].getNumber()== cards[2].getNumber())
                ||(cards[0].getNumber() != cards[1].getNumber()
                && cards[1].getNumber()!= cards[2].getNumber()
                && cards[0].getNumber()!= cards[2].getNumber()))){
            return false;
        }
        //shape
        //noinspection RedundantIfStatement
        if(!((    cards[0].getShape().getID() == cards[1].getShape().getID()
                && cards[1].getShape().getID()== cards[2].getShape().getID())
                ||(cards[0].getShape().getID() != cards[1].getShape().getID()
                && cards[1].getShape().getID()!= cards[2].getShape().getID()
                && cards[0].getShape().getID()!= cards[2].getShape().getID()))){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Set{" +
                "cards=" + Arrays.toString(cards) +
                '}';
    }
}
