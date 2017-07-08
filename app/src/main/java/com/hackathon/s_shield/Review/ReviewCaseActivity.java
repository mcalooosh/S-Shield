package com.hackathon.s_shield.Review;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.s_shield.DoneActivity;
import com.hackathon.s_shield.R;
import com.hackathon.s_shield.VolleyStuff.APILinks;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ReviewCaseActivity extends AppCompatActivity {

    EditText caseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_case);

        caseId=(EditText)findViewById(R.id.caseIdET);
    }

    ProgressDialog progress;
    public void checkCaseId(View v){

        if(caseId.getText().toString().length()<1){
            Toasty.error(getApplicationContext(), "Enter case id", Toast.LENGTH_SHORT, true).show();
        }else {
          //  Intent intent = new Intent(ReviewCaseActivity.this, Chat_Room.class);
           // intent.putExtra("case_id", "sexy934");
           // startActivity(intent);
            sendViewCaseIdRequest();
        }
       //

    }


    private void sendViewCaseIdRequest(){
        progress = ProgressDialog.show(this, "Checking Case id",
                "Please wait..", true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinks.ReviewCaseAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        Log.v("reje3",response);

                        progress.dismiss();
                        Toasty.success(getApplicationContext(), "Got it!", Toast.LENGTH_SHORT, true).show();
                        Intent intent=new Intent(ReviewCaseActivity.this,Chat_Room.class);
                        intent.putExtra("case_id",response);
                        startActivity(intent);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        Log.v("reje3",error.toString());
                        progress.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){


                //   String deliveryInfo="I am feeling bad, I got abused by 10th grade student his name is James, blonde tall guy";

                Map<String,String> params = new HashMap<String, String>();

                params.put("id", caseId.getText().toString());
                params.put("role", "student");
                params.put("comment", "yes, I am good");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




}
