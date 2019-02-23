package com.example.juju.e_labvideoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;
import java.util.List;


public class UploadActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra("FilePath");

        Log.d("CSV PATH", message);
        ProgressBar uploadPB = (ProgressBar) findViewById(R.id.uploadPB);
        ImageView ResultImage1 = (ImageView) findViewById(R.id.ResultImage1) ;
        ImageView ResultImage2 = (ImageView) findViewById(R.id.ResultImage2) ;
        ImageView ResultImage3 = (ImageView) findViewById(R.id.ResultImage3) ;
        ImageView ResultImage4 = (ImageView) findViewById(R.id.ResultImage4) ;
        ImageView ResultImage5 = (ImageView) findViewById(R.id.ResultImage5) ;
        ImageView ResultImage6 = (ImageView) findViewById(R.id.ResultImage6) ;
        ImageView ResultImage7 = (ImageView) findViewById(R.id.ResultImage7) ;
        ImageView ResultImage8 = (ImageView) findViewById(R.id.ResultImage8) ;
        ImageView ResultImage9 = (ImageView) findViewById(R.id.ResultImage9) ;
        ImageView ResultImage10 = (ImageView) findViewById(R.id.ResultImage10) ;
        ImageView ResultImage11 = (ImageView) findViewById(R.id.ResultImage11) ;
        ImageView ResultImage12 = (ImageView) findViewById(R.id.ResultImage12) ;



        uploadPB.setVisibility(View.VISIBLE);

        try {
            String requestURL = "http://163.152.217.166:8888/post";
            String charset = "UTF-8";

            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addFormField("aimathlab", "KoreaUniv");
            multipart.addFilePart("csv", new File(message+".csv"));
            multipart.addFilePart("vid", new File(message+".mp4"));

            List<String> response = multipart.finish();
            Log.d("ServerResponse", response.toString());
            for (String line : response) {
                uploadPB.setVisibility(View.GONE);
                Toast.makeText(UploadActivity.this, "Success to Upload", Toast.LENGTH_SHORT).show();
                // http://163.152.217.166:8888/static/result/A_X.jpg
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
            uploadPB.setVisibility(View.GONE);
            Toast.makeText(UploadActivity.this, "Check the Server Status", Toast.LENGTH_SHORT).show();

        }
    }
}
