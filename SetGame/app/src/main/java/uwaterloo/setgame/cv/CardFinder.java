package uwaterloo.setgame.cv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uwaterloo.setgame.util.Card;
import uwaterloo.setgame.util.Deck;
import uwaterloo.setgame.util.cardfeatures.Color;
import uwaterloo.setgame.util.cardfeatures.Fill;
import uwaterloo.setgame.util.cardfeatures.Shape;

/**
 * Created by Stephen on 12/20/2016.
 */
public class CardFinder {
    Card card;
    int w,h;

    public CardFinder(int w, int h){
        card = new Card(null,null,null,0);
        this.w=w;
        this.h=h;
    }

    public Deck findCards(Mat img){
        //apply filters
        applyFilters(img,7,50,150);
        //findRect
        //Refilter Rectangle
        //Verify Card
        //Detect Card attributes
        //Return Card
        return null;
    }

    public Mat debugDisplay(Mat img){
        //This method should only be used for debug purposes to display information to screen
        applyFilters(img,7,50,150);
        return img;
    }

    private Mat applyFilters(Mat img, int blur,int canny1, int canny2){
        //Convert to Grayscale
        Imgproc.cvtColor(img,img,Imgproc.COLOR_RGB2GRAY);

        //Blur
        if(blur%2!=1){//blur must be odd
            blur++;
        }
        Imgproc.GaussianBlur(img, img, new Size(blur,blur),0);

        //Canny
        Imgproc.Canny(img,img,canny1,canny2); //50,150
        return img;
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
