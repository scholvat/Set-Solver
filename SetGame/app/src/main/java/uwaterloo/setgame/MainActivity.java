package uwaterloo.setgame;

import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import uwaterloo.setgame.util.Card;
import uwaterloo.setgame.util.Deck;

public class MainActivity extends AppCompatActivity  implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG="MainActivity";

    Mat mRgba, imgGray, imgBlur,imgCanny, imgHSV,imgHSVThreshold, imgDilate;

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
                "Normal", "Black and White", "Canny", "HSV","Blur","Dilate"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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
        mRgba = new Mat(height,width, CvType.CV_8UC4);
        imgGray = new Mat(height,width, CvType.CV_8UC1);
        imgBlur = new Mat(height,width, CvType.CV_8UC1);
        imgCanny = new Mat(height,width, CvType.CV_8UC1);
        imgHSV = new Mat(height,width, CvType.CV_8UC3);
        imgHSVThreshold = new Mat(height,width, CvType.CV_8UC3);
        imgDilate = new Mat(height,width, CvType.CV_8UC1);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();

        Imgproc.cvtColor(mRgba,imgGray,Imgproc.COLOR_RGB2GRAY);

        Imgproc.cvtColor(mRgba,imgHSV,Imgproc.COLOR_RGB2HSV);

        try {
            Core.inRange(imgHSV, new Scalar(Integer.valueOf(b1.getText().toString()), Integer.valueOf(b2.getText().toString()), Integer.valueOf(b3.getText().toString()))
                    , new Scalar(Integer.valueOf(t1.getText().toString()), Integer.valueOf(t2.getText().toString()), Integer.valueOf(t3.getText().toString())), imgHSVThreshold);
            Imgproc.GaussianBlur(imgGray, imgBlur, new Size(Integer.valueOf(b1.getText().toString()),Integer.valueOf(b1.getText().toString())),0);
            Imgproc.Canny(imgBlur,imgCanny,50,150);

            Imgproc.dilate(imgCanny,imgDilate,new Mat());

            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Imgproc.findContours(imgCanny,contours,new Mat(),Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);


            //debug spinner to change filters
            String cameraFilter = cameraView.getSelectedItem().toString();
            if(cameraFilter.compareTo("Normal")==0){
                return mRgba;
            }else if(cameraFilter.compareTo("Black and White")==0){
                return imgGray;
            }else if(cameraFilter.compareTo("HSV")==0){
                return imgHSVThreshold;
            }else if(cameraFilter.compareTo("Blur")==0){
                return imgBlur;
            }else if(cameraFilter.compareTo("Dilate")==0){
            return imgDilate;
            }
            else{
                return imgCanny;
            }
        }catch(Exception e){
            Log.e(TAG,e.toString());
            return mRgba;
        }


    }


}
