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

        deck.shuffle();

        Deck field = deck.drawCards(12);

        for(Card c : field.getCards()){
            Log.d("MainActivity",c.toString());

        }
        for(Set s :field.getSets()){
            Log.d("MainActivity",s.toString());
        }
    }
}
