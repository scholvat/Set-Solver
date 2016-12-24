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
import java.util.List;

import uwaterloo.setgame.util.Card;
import uwaterloo.setgame.util.cardfeatures.Color;
import uwaterloo.setgame.util.cardfeatures.Fill;
import uwaterloo.setgame.util.cardfeatures.Shape;


public class CardFinder {
    private static final String TAG="CardFinder";

    int w,h;

    public CardFinder(int w, int h){
        this.w=w;
        this.h=h;
    }

    public Mat findCards(Mat imgSrc){

            //Mat imgOriginal = imgSrc.clone();
            //Mat imgRectangle = imgSrc.clone();
            //Filter image
            Mat img = applyFilters(imgSrc, 9, 50, 150);

            //Find Rectangles
            List<MatOfPoint> rectangles = findRect(img, 15);

            //Mat mask = new Mat(imgOriginal.width(),imgOriginal.height(),imgOriginal.type());
            //mask = imgOriginal.clone();


            //Imgproc.fillPoly(mask,rectangles,new Scalar(255,0,0));
            //refilter Rectangle

            //Draw Rectangles to Screen in Red
            Mat mask = imgSrc.clone();
            //img.setTo(new Scalar(255, 255, 255));
            mask.setTo(new Scalar(0, 0, 0));
            Imgproc.fillPoly(mask, rectangles, new Scalar(255, 255, 255));
            //Imgproc.drawContours(imgSrc,rectangles,-1,new Scalar(255,0,0),4);
            //mask.copyTo(imgSrc);
            //imgRectangle = Mat.zeros(w,h,imgSrc.type());
            imgSrc.copyTo(img, mask);


            //Refilter Rectangle
            //Verify Card
            //Detect Card attributes
            //Return Card
            //return img;

            return img;
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
        Imgproc.Canny(img,img,canny1,canny2);
        return img;
    }

    private List<MatOfPoint> findRect(Mat imgSrc, int polygonThreshold){
        Mat img = imgSrc.clone();
        //Find Contours
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(img,contours,new Mat(),Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //approx polygons
        //Log.d(TAG,"Size of contours: " + String.valueOf(contours.size()));
        if(contours.size()>200){return new ArrayList<>();}

        MatOfPoint2f contours2f = new MatOfPoint2f();  //converts contours from MatOfPoints to MatOfPoints2f
        MatOfPoint2f approx = new MatOfPoint2f();      //resulting MatOfPoint2f from approxPolyDP
        List<MatOfPoint> polygons = new ArrayList<>(); //Resulting polygonal contours, which hopefully consist of cards

        //loop through all contours
        for(int i=0; i<contours.size();i++){
            //convert contour to MatOfPoint2f
            contours.get(i).convertTo(contours2f, CvType.CV_32FC2);

            //approximate polygons, using t1 as epsilon for debugging
            Imgproc.approxPolyDP(contours2f, approx, polygonThreshold, true);

            //check if it is a rectangle
            if(approx.rows()==4 && Imgproc.contourArea(approx)>1000){
                //convert and add to polygons (type Contours)
                MatOfPoint points = new MatOfPoint(approx.toArray());
                polygons.add(points);
            }

            //Log.d(TAG, String.valueOf(approx.rows()));
        }
        return polygons;
    }

    private Card detectAttributes(MatOfPoint2f rect, Mat imgSrc){
        List<MatOfPoint> cardShapes = findRect(imgSrc,5);
        for(int i=0; i<cardShapes.size();i++){
            cardShapes.get(i);
            Log.d(TAG, String.valueOf(cardShapes.size()));
        }

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
