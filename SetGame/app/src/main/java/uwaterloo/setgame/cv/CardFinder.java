package uwaterloo.setgame.cv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.List;
import java.util.Map;

import uwaterloo.setgame.util.Card;
import uwaterloo.setgame.util.cardfeatures.Color;
import uwaterloo.setgame.util.cardfeatures.Fill;
import uwaterloo.setgame.util.cardfeatures.Shape;

/**
 * Created by Stephen on 12/20/2016.
 */
public class CardFinder {
    Card card;

    public CardFinder(){
        card = new Card(null,null,null,0);
    }

    public Card findCard(Mat img){
        //apply filters
        //findRect
        //Refilter Rectangle
        //Verify Card
        //Detect Card attributes
        //Return Card
        return card;
    }

    private Mat applyFilters(Map filters){
        //Convert to Grayscale
        //Blur
        //Canny
        return null;
    }

    private List<MatOfPoint> findRect(){
        //Find Contours
        //Find Rectangles
        return null;
    }

    private Card detectAttributes(){
        return null;
    }
    private int detectNumber(){
        return -1;
    }

    private Shape detectShape(){
        return null;
    }

    private Color detectColor(){
        return null;
    }

    private Fill detectFill(){
        return null;
    }

}
