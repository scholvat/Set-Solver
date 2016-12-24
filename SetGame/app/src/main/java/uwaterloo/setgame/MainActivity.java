package uwaterloo.setgame;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.EditText;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import uwaterloo.setgame.cv.CardFinder;

public class MainActivity extends AppCompatActivity  implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG="MainActivity";

    Mat mRgba;

    CardFinder cardFinder;

    //debug stuff
    EditText b1,b2,b3,t1,t2,t3;

    ActivityManager.MemoryInfo mi;

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

        //memory monitor
        mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        //init Camera View
        javaCameraView = (JavaCameraView) findViewById(R.id.java_camera_view);
        assert javaCameraView != null;
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

        //debug to set values
        b1 = (EditText) findViewById(R.id.editText);
        t1 = (EditText) findViewById(R.id.editText2);

        b2 = (EditText) findViewById(R.id.editText3);
        t2 = (EditText) findViewById(R.id.editText4);

        b3 = (EditText) findViewById(R.id.editText5);
        t3 = (EditText) findViewById(R.id.editText6);

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

        //imgDisplay = new Mat(width,height, CvType.CV_8UC4);
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
            return cardFinder.findCards(mRgba);
        }catch(NumberFormatException | CvException e){
            Log.e(TAG,e.toString());
        }
        return null;


    }


}
