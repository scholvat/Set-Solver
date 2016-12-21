package uwaterloo.setgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import uwaterloo.setgame.cv.CardFinder;

public class MainActivity extends AppCompatActivity  implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG="MainActivity";

    Mat mRgba, imgGray, imgBlur,imgCanny, imgHSV,imgContours, imgDilate;

    CardFinder cardFinder;

    //debug stuff
    Spinner cameraView;
    EditText b1,b2,b3,t1,t2,t3;

    JavaCameraView javaCameraView;
    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch(status){
                case BaseLoaderCallback.SUCCESS:
                    javaCameraView.enableView();
                break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
            super.onManagerConnected(status);
        }
    };

    static{
        if(OpenCVLoader.initDebug()){
            Log.i(TAG,"OpenCV Successfully Loaded");
        }else{
            Log.i(TAG,"OpenCV Failed to Load");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        javaCameraView = (JavaCameraView) findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

        //debug spinner to change filters
        cameraView = (Spinner) findViewById(R.id.camera_view_spinner);
        String[] arraySpinner = new String[] {
                "Normal", "Black and White", "Canny", "Polygons","Blur","Dilate"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        cameraView.setAdapter(adapter);

        //debug to change HSV
        b1 = (EditText) findViewById(R.id.editText);
        t1 = (EditText) findViewById(R.id.editText2);

        b2 = (EditText) findViewById(R.id.editText3);
        t2 = (EditText) findViewById(R.id.editText4);

        b3 = (EditText) findViewById(R.id.editText5);
        t3 = (EditText) findViewById(R.id.editText6);


        /*Deck deck = new Deck();
        deck.shuffle();
        ArrayList<Card> cards = deck.getCards();
        for(Card c : cards){
            //Log.d(TAG,c.toString());
        }*/
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(javaCameraView!=null){
            javaCameraView.disableView();

        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(javaCameraView!=null){
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(OpenCVLoader.initDebug()){
            Log.i(TAG,"OpenCV Successfully Reloaded onResume");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else{
            Log.i(TAG,"OpenCV Failed to reload onResume");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

        cardFinder = new CardFinder(width,height);
        mRgba = new Mat(height,width, CvType.CV_8UC4);
        /*imgGray = new Mat(height,width, CvType.CV_8UC1);
        imgBlur = new Mat(height,width, CvType.CV_8UC1);
        imgCanny = new Mat(height,width, CvType.CV_8UC1);
        imgHSV = new Mat(height,width, CvType.CV_8UC3);
        imgContours  = new Mat(height,width, CvType.CV_8UC3);
        imgDilate = new Mat(height,width, CvType.CV_8UC1);*/
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        //Get RGBA Camera Image
        mRgba = inputFrame.rgba();

        try {
            return cardFinder.debugDisplay(mRgba);
            /*//Convert to Grayscale
            Imgproc.cvtColor(mRgba,imgGray,Imgproc.COLOR_RGB2GRAY);

            //Blur Image
            Imgproc.GaussianBlur(imgGray, imgBlur, new Size(Integer.valueOf(b1.getText().toString()),Integer.valueOf(b1.getText().toString())),0);

            //Canny Image
            Imgproc.Canny(imgBlur,imgCanny,50,150);

            //Find Contours
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(imgCanny,contours,new Mat(),Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            //approx polygons
            //Log.d(TAG,"Size of contours: " + String.valueOf(contours.size()));
            if(contours.size()>500){return mRgba;}

            MatOfPoint2f contours2f = new MatOfPoint2f();  //converts contours from MatOfPoints to MatOfPoints2f
            MatOfPoint2f approx = new MatOfPoint2f();      //resulting MatOfPoint2f from apporxPolyDP
            List<MatOfPoint> polygons = new ArrayList<>(); //Resulting polygonal contours, which hopefully consist of cards
            imgContours  = new Mat(imgContours.height(),imgContours.width(), CvType.CV_8UC3); //Clear imgContours

            //loop through all contours
            for(int i=0; i<contours.size();i++){
                //convert contour to MatOfPoint2f
                contours.get(i).convertTo(contours2f, CvType.CV_32FC2);

                //approximate polygons, using t1 as epsilon for debugging
                Imgproc.approxPolyDP(contours2f, approx, Integer.valueOf(t1.getText().toString()), true);

                //check if it is a rectangle
                if(approx.rows()==4){
                    //convert and add to polygons (type Contours)
                    MatOfPoint points = new MatOfPoint(approx.toArray());
                    polygons.add(points);
                }

                //Log.d(TAG, String.valueOf(approx.rows()));
            }

            //Draw contours
            Imgproc.drawContours(imgContours, contours, -1, new Scalar(0, 255, 0), -1);

            //Draw Polygons
            Imgproc.drawContours(imgGray,polygons,-1,new Scalar(0,0,255),4);

            //debug spinner to change filters
            String cameraFilter = cameraView.getSelectedItem().toString();
            if(cameraFilter.compareTo("Normal")==0){
                //Log.d(TAG,"RGBA");
                return mRgba;
            }else if(cameraFilter.compareTo("Black and White")==0){
                //Log.d(TAG,"Gray");
                return imgGray;
            }else if(cameraFilter.compareTo("Polygons")==0){
                //Log.d(TAG,"HSV");
                return imgContours;
            }else if(cameraFilter.compareTo("Blur")==0){
                //Log.d(TAG,"Blur");
                return imgBlur;
            }else if(cameraFilter.compareTo("Dilate")==0){
                //Log.d(TAG,"dilate");
            //return element;
            }
            else{
                return imgCanny;
            }*/
        }catch(NumberFormatException e){
            Log.e(TAG,e.toString());

        }
        catch(CvException e){
            Log.e(TAG,e.toString());
        }
        return mRgba;


    }


}
