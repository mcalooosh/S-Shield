package com.hackathon.s_shield.Admin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.hackathon.s_shield.R;
import com.hackathon.s_shield.VolleyStuff.APILinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ViewAdminReportActivity extends AppCompatActivity {

    private ArcProgress arcProgressAnger,arcProgressDisgust,arcProgressFear,arcProgressJoy,arcProgressSadness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_report);

        getToneAnalyzer();

        initArcProgresses();

    }

    private void initArcProgresses(){
        arcProgressAnger=(ArcProgress)findViewById(R.id.arc_progress1);
        arcProgressDisgust=(ArcProgress)findViewById(R.id.arc_progress2);
        arcProgressFear=(ArcProgress)findViewById(R.id.arc_progress3);
        arcProgressJoy=(ArcProgress)findViewById(R.id.arc_progress4);
        arcProgressSadness=(ArcProgress)findViewById(R.id.arc_progress5);
    }

    ProgressDialog progress;
    private void getToneAnalyzer(){

        //  progress = ProgressDialog.show(this, "Reporting","Sending your report safely, please wait..", true);
        progress = ProgressDialog.show(this, "Analyzing Report Emotions","please wait..", true);

        String studentTextPlain="I got abused by a guy called James, he is at grade 10. today morning" +
                                "he started to hit me on my head infront of my friends";
        String studentText=studentTextPlain.replaceAll(" ","%20");
        String finalLinkAPI= APILinks.ToneApi+"&text="+studentText+"&tones=emotion";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalLinkAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        Log.v("reje3",response);

                        // progress.dismiss();
                        Toasty.success(getApplicationContext(), "Text Analyzed Successfully", Toast.LENGTH_SHORT, true).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray emotionsArray = jsonObj.getJSONObject("document_tone").getJSONArray("tone_categories").getJSONObject(0).getJSONArray("tones");

                            Log.v("jsonArraySize",emotionsArray.length()+"");

                            double angerScore= Double.parseDouble(emotionsArray.getJSONObject(0).get("score").toString());
                            double finalAngerScore=angerScore*100.0;
                            arcProgressAnger.setProgress((int)finalAngerScore);

                            double disgustScore= Double.parseDouble(emotionsArray.getJSONObject(1).get("score").toString());
                            double finalDisgustScore=disgustScore*100.0;
                            arcProgressDisgust.setProgress((int)finalDisgustScore);

                            double fearScore= Double.parseDouble(emotionsArray.getJSONObject(2).get("score").toString());
                            double finalFearScore=fearScore*100.0;
                            arcProgressFear.setProgress((int)finalFearScore);

                            double joyScore= Double.parseDouble(emotionsArray.getJSONObject(3).get("score").toString());
                            double finalJoyScore=joyScore*100.0;
                            arcProgressJoy.setProgress((int)finalJoyScore);

                            double sadScore= Double.parseDouble(emotionsArray.getJSONObject(4).get("score").toString());
                            double finalSadScore=sadScore*100.0;
                            arcProgressSadness.setProgress((int)finalSadScore);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progress.dismiss();

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = "110080de-e820-48fe-88e2-5dce0c6bdd4e:disDF11tGfal";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
