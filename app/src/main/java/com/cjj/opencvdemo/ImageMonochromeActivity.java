package com.cjj.opencvdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImageMonochromeActivity extends Activity {
    private ImageView ivOriginal,ivGrayscale;
    private Bitmap originalBitmap,grayBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cv);
        ivOriginal=this.findViewById(R.id.iv_original);
        ivGrayscale=this.findViewById(R.id.iv_grayscale);
    }

    private BaseLoaderCallback mLoaderCallback=new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case  BaseLoaderCallback.SUCCESS:
                    break;
              default:
                  super.onManagerConnected(status);
                  break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        }else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onGrayScale(View view){
        originalBitmap= BitmapFactory.decodeResource(getResources(),
                R.mipmap.image);
        ivOriginal.setScaleType(ImageView.ScaleType.FIT_XY);
        ivOriginal.setImageBitmap(originalBitmap);
        grayBitmap=Bitmap.createBitmap(originalBitmap.getWidth(),
                originalBitmap.getHeight(),Bitmap.Config.RGB_565);
        Mat rgbMat=new Mat();
        Mat grayMat=new Mat();
        Utils.bitmapToMat(originalBitmap,rgbMat);
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
        ivGrayscale.setImageBitmap(grayBitmap);
        ivGrayscale.setScaleType(ImageView.ScaleType.FIT_XY);


    }
}
