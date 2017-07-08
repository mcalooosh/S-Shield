package com.hackathon.s_shield;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DoneActivity extends AppCompatActivity {

    TextView caseIdTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        caseIdTV=(TextView)findViewById(R.id.caseidTV);
        caseIdTV.setText("Your case id is: #"+id);
    }
}
