package uwaterloo.setgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import uwaterloo.setgame.util.Card;
import uwaterloo.setgame.util.Deck;
import uwaterloo.setgame.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Deck deck = new Deck();
        try {
            Set set = new Set(new Card[]{deck.getCard(0),deck.getCard(1),deck.getCard(4)});
            Log.d("MainActivity", "Set?:" + String.valueOf(set.checkSet()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        deck.shuffle();
        ArrayList<Card> cards = deck.getCards();
        for(Card c : cards){
            Log.d("MainActivity",c.toString());

        }
    }
}
