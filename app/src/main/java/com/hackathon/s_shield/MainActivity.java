package com.hackathon.s_shield;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.s_shield.Admin.AdminLoginActivity;
import com.hackathon.s_shield.Review.ReviewCaseActivity;
import com.hackathon.s_shield.VolleyStuff.APILinks;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // showHints();

    }//end onCreate


    private void showHints(){
        new MaterialTapTargetPrompt.Builder(MainActivity.this)
                .setTarget(findViewById(R.id.reportBtn))
                .setPrimaryText("Report safely")
                .setSecondaryText("Feel safe to report an abuse")
                .setBackgroundColour(Color.parseColor("#3B177FD7"))
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSED)
                        {
                            // User has pressed the prompt target
                            new MaterialTapTargetPrompt.Builder(MainActivity.this)
                                    .setTarget(findViewById(R.id.counselingBtn))
                                    .setPrimaryText("Talk to a psychology counsel")
                                    .setSecondaryText("Feel free to say what you feel")
                                    .setBackgroundColour(Color.parseColor("#3B177FD7"))
                                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                    {
                                        @Override
                                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                        {
                                            if (state == MaterialTapTargetPrompt.STATE_DISMISSED)
                                            {
                                                // User has pressed the prompt target
                                                new MaterialTapTargetPrompt.Builder(MainActivity.this)
                                                        .setTarget(findViewById(R.id.reviewCaseBtn))
                                                        .setPrimaryText("Review your case")
                                                        .setSecondaryText("Check replies for your case id")
                                                        .setBackgroundColour(Color.parseColor("#3B177FD7"))
                                                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                                        {
                                                            @Override
                                                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                                            {
                                                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED)
                                                                {
                                                                    // User has pressed the prompt target
                                                                }
                                                            }
                                                        })
                                                        .show();

                                            }
                                        }
                                    })
                                    .show();


                        }
                    }
                })
                .show();

    }
    int click=0;
    Intent intent;

    public void onButtonClick(View v){
        switch (v.getId()){

                case R.id.reportBtn:
                startActivity(new Intent(MainActivity.this,ReportActivity.class));
                break;

            case R.id.counselingBtn:
                startActivity(new Intent(MainActivity.this,HelpActivity.class));
                break;

            case R.id.reviewCaseBtn:
                startActivity(new Intent(MainActivity.this,ReviewCaseActivity.class));
                break;

            case R.id.logoIV:
                click++;
                if(click==3){
                    intent = new Intent(MainActivity.this,AdminLoginActivity.class);
                    startActivity(intent);
                    click=0;
                }
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.info) {

            Intent infoIntent= new Intent(MainActivity.this, InfoActivity.class);
            startActivity(infoIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
