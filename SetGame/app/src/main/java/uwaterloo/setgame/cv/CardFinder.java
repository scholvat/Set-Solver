package uwaterloo.setgame.cv;

import android.util.Log;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
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
    private static final String TAG="CardFinder";

    Card card;
    int w,h;

    public CardFinder(int w, int h){
        card = new Card(null,null,null,0);
        this.w=w;
        this.h=h;
    }

    public Deck findCards(Mat imgSrc){

        Mat img = applyFilters(imgSrc,7,50,150);
        List<MatOfPoint> rectangles = findRect(img, 15);
        Imgproc.drawContours(imgSrc,rectangles,-1,new Scalar(255,0,0),4);

        //Refilter Rectangle
        //Verify Card
        //Detect Card attributes
        //Return Card
        return null;
    }

    private Mat applyFilters(Mat imgSrc, int blur,int canny1, int canny2){
        Mat img = imgSrc.clone();
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

    private List<MatOfPoint> findRect(Mat imgSrc, int polygonThreshold){
        Mat img = imgSrc.clone();
        //Find Contours
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(img,contours,new Mat(),Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //approx polygons
        //Log.d(TAG,"Size of contours: " + String.valueOf(contours.size()));
        //if(contours.size()>500){return mRgba;}

        MatOfPoint2f contours2f = new MatOfPoint2f();  //converts contours from MatOfPoints to MatOfPoints2f
        MatOfPoint2f approx = new MatOfPoint2f();      //resulting MatOfPoint2f from apporxPolyDP
        List<MatOfPoint> polygons = new ArrayList<>(); //Resulting polygonal contours, which hopefully consist of cards

        //loop through all contours
        for(int i=0; i<contours.size();i++){
            //convert contour to MatOfPoint2f
            contours.get(i).convertTo(contours2f, CvType.CV_32FC2);

            //approximate polygons, using t1 as epsilon for debugging
            Imgproc.approxPolyDP(contours2f, approx, polygonThreshold, true);

            //check if it is a rectangle
            if(approx.rows()==4){
                //convert and add to polygons (type Contours)
                MatOfPoint points = new MatOfPoint(approx.toArray());
                polygons.add(points);
            }

            //Log.d(TAG, String.valueOf(approx.rows()));
        }
        return polygons;
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
