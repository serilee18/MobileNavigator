package com.example.juju.e_labvideoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class UploadActivity extends AppCompatActivity {


    Handler handler = new Handler();

    ArrayList<Bitmap> BitmapArr = new ArrayList<Bitmap>();

    ArrayList<ImageView> ImageArr = new ArrayList<ImageView>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        final String message = intent.getStringExtra("FilePath");

        Log.d("CSV PATH", message);
        final ProgressBar uploadPB = (ProgressBar) findViewById(R.id.uploadPB);
        final Button RestartButton = (Button) findViewById(R.id.RestartButton);

        ImageArr.add((ImageView) findViewById(R.id.ResultImage1)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage2)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage3)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage4)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage5)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage6)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage7)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage8)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage9)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage10)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage11)) ;
        ImageArr.add((ImageView) findViewById(R.id.ResultImage12)) ;
        ImageArr.add((ImageView) findViewById(R.id.VideoFrame)) ;


        findViewById(R.id.RestartButton).setOnClickListener(mClickListener);


        uploadPB.setVisibility(View.VISIBLE);
        RestartButton.setVisibility(View.GONE);
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String requestURL = "http://163.152.217.166:8888/post";
                    String charset = "UTF-8";

                    MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                    multipart.addFormField("aimathlab", "KoreaUniv");
                    multipart.addFilePart("csv", new File(message + ".csv"));
                    multipart.addFilePart("vid", new File(message + ".mp4"));

                    List<String> response = multipart.finish();
                    Log.d("ServerResponse", response.get(0));
                    String[] ResultImage = response.get(0).split(",");

                    for(int i=1;i<ResultImage.length;i++) {
                        URL url;
                        if(ResultImage.length-1 == i){
                            url = new URL("http://163.152.217.166:8888/static/images/"+ResultImage[i]+".jpg");
                        }
                        else {
                            url = new URL("http://163.152.217.166:8888/static/result/" + ResultImage[i] + ".jpg");
                        }
                        InputStream is = url.openStream();
                        BitmapArr.add(BitmapFactory.decodeStream(is));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            uploadPB.setVisibility(View.GONE);
                            RestartButton.setVisibility(View.VISIBLE);
                            for(int i=0;i<BitmapArr.size();i++) {
                                Bitmap bitmap_tmp = BitmapArr.get(i);
                                ImageArr.get(i).setImageBitmap(bitmap_tmp);
                            }
                        }
                    });


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        mThread.start();

    }


    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };


}
