package com.hackathon.s_shield;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.s_shield.VolleyStuff.APILinks;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class ReportActivity extends AppCompatActivity {


    RadioGroup identityRG;
    EditText identityET,reportText;
    TextView attachedTv;
    String userIdentity;

    boolean hasPicture=false;

    // Storage And Camera Permissions
    private static String[] PERMISSIONS_REQ = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initViews();

        boolean availbe_permission = true;
        // For API 23+ you need to request the read/write permissions even if they are already in your manifest.
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M ) {
            availbe_permission = verifyPermissions(this);
        }

    }



    private static boolean verifyPermissions(Activity activity) {
        // Check if we have write permission
        int write_permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read_persmission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera_permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (write_permission != PackageManager.PERMISSION_GRANTED ||
                read_persmission != PackageManager.PERMISSION_GRANTED ||
                camera_permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_REQ,
                    REQUEST_CODE_PERMISSION
            );
            return false;
        } else {
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            // Restart it after granting permission
            if (requestCode == REQUEST_CODE_PERMISSION) {
                finish();
                startActivity(getIntent());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong, try again with permissions", Toast.LENGTH_LONG).show();
        }
    }

    private static final int REQUEST_CODE_PERMISSION = 1;


    int index;
    private void initViews(){

        identityET=(EditText)findViewById(R.id.personalInfoET);
        reportText=(EditText)findViewById(R.id.reportET);
        identityRG=(RadioGroup)findViewById(R.id.identityRadioGrouo);
        attachedTv=(TextView)findViewById(R.id.attachedTV);

        identityRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(i);
                index = radioGroup.indexOfChild(checkedRadioButton)+1;

                if(index==2){// if provide info checked
                    identityET.setVisibility(View.VISIBLE);
                }else{
                    identityET.setVisibility(View.GONE);
                }//anonymous

            }
        });


    } //end initViews

    public void reportBtnClick(View v){
        switch (v.getId()){

            case R.id.reportAttachBtn:
                pickPictures();
                break;

            case R.id.reportSendBtn:
                checkFields();
                break;
        }
    }

    String reportTextString;
    private void checkFields(){

        reportTextString=reportText.getText().toString();
        if(reportText.length()<40){
            Toast.makeText(getApplicationContext(),"Please enter more information",Toast.LENGTH_SHORT).show();
        }else if(index==2  && identityET.getText().toString().length()<3){
            Toast.makeText(getApplicationContext(),"Please enter your personal info",Toast.LENGTH_SHORT).show();
        }
        else
           // updatepp();
            sendReport();

    }

    //UPDATED!
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null)
        {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }

    ProgressDialog progress;

    private void sendReport(){

        progress = ProgressDialog.show(this, "Reporting","Sending your report safely, please wait..", true);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinks.SendReportAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        Log.v("reje3",response);

                        progress.dismiss();
                        Toasty.success(getApplicationContext(), "Reported Successfully", Toast.LENGTH_SHORT, true).show();
                        Intent intent=new Intent(ReportActivity.this,DoneActivity.class);
                        intent.putExtra("id",response);
                        startActivity(intent);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        Log.v("reje3",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){


             //   String deliveryInfo="I am feeling bad, I got abused by 10th grade student his name is James, blonde tall guy";

                Map<String,String> params = new HashMap<String, String>();

                params.put("case", reportTextString);

                if(index==1)
                params.put("anon", 1+"");
                else {
                    params.put("anon", 0 + "");
                    params.put("studentid", identityET.getText().toString());
                }

                if(hasPicture) {
                    Bitmap bm = BitmapFactory.decodeFile(selectedImagePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] byteArrayImage = baos.toByteArray();
                    final String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                    params.put("picno", 1+"");
                    params.put("pic0", encodedImage);
                }else{
                    params.put("picno", 0+"");
                }

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }




    private static AsyncHttpClient client = new AsyncHttpClient();

    private void pickPictures(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }
    ArrayList<Uri> mArrayUri;

    //YOU CAN EDIT THIS TO WHATEVER YOU WANT
    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;
    //ADDED
    private String filemanagerstring;

    //UPDATED
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                //OI FILE Manager
                filemanagerstring = selectedImageUri.getPath();

                //MEDIA GALLERY
                selectedImagePath = getPath(selectedImageUri);
                attachedTv.setText(" 1 attached picture(s)");
                hasPicture=true;
                //DEBUG PURPOSE - you can delete this if you want
                if(selectedImagePath!=null)
                    System.out.println(selectedImagePath);
                else System.out.println("selectedImagePath is null");
                if(filemanagerstring!=null)
                    System.out.println(filemanagerstring);
                else System.out.println("filemanagerstring is null");

                //NOW WE HAVE OUR WANTED STRING
                if(selectedImagePath!=null)
                    System.out.println("selectedImagePath is the right one for you!");
                else
                    System.out.println("filemanagerstring is the right one for you!");
            }
        }else{
            //if the user canceled
            hasPicture=false;
        }
    }//end on ActivityResult

}
