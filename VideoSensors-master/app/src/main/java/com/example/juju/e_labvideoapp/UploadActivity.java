package com.example.juju.e_labvideoapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.content.ContentValues.TAG;

public class UploadActivity extends AppCompatActivity {


    final private String upLoadServerUri = "http://163.152.217.166:8888/upload";
    private int serverResponseCode = 0;
    private String SelectedStyle;
    private String UID;
    private String count;
    private String mPhoto;
    private File file;
    private ProgressBar uploadPB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        String sourceFileUri = ""; //GET INTENT
        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        Message msg = new Message();

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        sourceFileUri = file.getAbsolutePath();
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist");
            //return 0;
        } else {
            try {
                uploadPB.setVisibility(View.VISIBLE);
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                long unixTime = System.currentTimeMillis() / 1000L;

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                Log.d("uploadFileJ", conn.toString());
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                Log.d("uploadFileJ", dos.toString());

                String tmp_filename = SelectedStyle + "_" + UID + "_" + unixTime + ".jpg";
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + tmp_filename + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                if (serverResponseCode == 200) {
                    Log.d("uploadFile", "File Upload Complete.");
                    msg.obj = "We will send your Picture in an hour";
                }
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (MalformedURLException ex) {
                Log.e("uploadFile", "error: " + ex.getMessage(), ex);
                msg.obj = ex.getMessage();
            } catch (Exception e) {
                Log.e("uploadFile", "Exception : " + e.getMessage(), e);

                msg.obj = e.getMessage();
            }
            //uploadPB.setVisibility(View.VISIBLE);
            mHandler2.sendMessage(msg);
            //return serverResponseCode;

        } // End else block
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            Toast.makeText(UploadActivity.this, text, Toast.LENGTH_LONG).show();
            uploadPB.setVisibility(View.GONE);
            finish();
        }
    };
}
