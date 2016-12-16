package uwaterloo.setgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.opencv.android.OpenCVLoader;

import java.util.ArrayList;

import uwaterloo.setgame.util.Card;
import uwaterloo.setgame.util.Deck;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";

    static{
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"OpenCV Successfully Loaded");
        }else{
            Log.d(TAG,"OpenCV Failed to Load");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Deck deck = new Deck();
        deck.shuffle();
        ArrayList<Card> cards = deck.getCards();
        for(Card c : cards){
            //Log.d(TAG,c.toString());
        }
    }
}
