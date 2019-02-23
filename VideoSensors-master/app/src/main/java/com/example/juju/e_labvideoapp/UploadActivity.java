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
import java.util.List;


public class UploadActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        final Handler handler = new Handler();

        Intent intent = getIntent();
        final String message = intent.getStringExtra("FilePath");

        Log.d("CSV PATH", message);
        final ProgressBar uploadPB = (ProgressBar) findViewById(R.id.uploadPB);
//        final ImageView ResultImage1 = (ImageView) findViewById(R.id.ResultImage1) ;
//        final ImageView ResultImage2 = (ImageView) findViewById(R.id.ResultImage2) ;
//        final ImageView ResultImage3 = (ImageView) findViewById(R.id.ResultImage3) ;
//        final ImageView ResultImage4 = (ImageView) findViewById(R.id.ResultImage4) ;
//        final ImageView ResultImage5 = (ImageView) findViewById(R.id.ResultImage5) ;
//        final ImageView ResultImage6 = (ImageView) findViewById(R.id.ResultImage6) ;
//        final ImageView ResultImage7 = (ImageView) findViewById(R.id.ResultImage7) ;
//        final ImageView ResultImage8 = (ImageView) findViewById(R.id.ResultImage8) ;
//        final ImageView ResultImage9 = (ImageView) findViewById(R.id.ResultImage9) ;
//        final ImageView ResultImage10 = (ImageView) findViewById(R.id.ResultImage10) ;
//        final ImageView ResultImage11 = (ImageView) findViewById(R.id.ResultImage11) ;
//        final ImageView ResultImage12 = (ImageView) findViewById(R.id.ResultImage12) ;



        uploadPB.setVisibility(View.VISIBLE);

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
                        Log.d("ResultImage", ResultImage[i]+" + "+i );
                        int resID = getResources().getIdentifier("ResultImage"+i, "id", getPackageName());
                        final ImageView iv = (ImageView) findViewById(resID);
                        URL url = new URL("http://163.152.217.166:8888/static/result/"+ResultImage[i]+".jpg");
                        InputStream is = url.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {  // 화면에 그려줄 작업
                                iv.setImageBitmap(bm);
                            }
                        });
                        iv.setImageBitmap(bm); //비트맵 객체로 보여주기
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        mThread.start();

    }
}
