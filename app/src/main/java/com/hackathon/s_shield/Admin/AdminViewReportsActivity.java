package com.hackathon.s_shield.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.hackathon.s_shield.Admin.Adapter.RecyclerItemClickListener;
import com.hackathon.s_shield.Admin.Adapter.Report;
import com.hackathon.s_shield.Admin.Adapter.ReportsAdapter;
import com.hackathon.s_shield.R;

import java.util.ArrayList;
import java.util.List;

public class AdminViewReportsActivity extends AppCompatActivity {

    List<Report> result;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_reports);

        recyclerView=(RecyclerView)findViewById(R.id.list);
        recyclerView.setNestedScrollingEnabled(false);
        result= new ArrayList<>();

        setUpList();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(AdminViewReportsActivity.this,ViewAdminReportActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
    }//end onCreate


    ReportsAdapter ca;
    private void setUpList(){


        Report one = new Report(
                "6yi5w",
                "Anonymous"
    );

        Report two = new Report(
                "bdo2",
                "Jimmy B."
     );
        Report three = new Report(
                "clas2e",
                "Anonymous"
        );
        Report four = new Report(
                "bondo4",
                "Anonymous"
      );
        Report five = new Report(
                "karme8",
                "Anonymous"
          );
        Report six = new Report(
                "skiw1",
                "Sara K."
        );
        Report seven = new Report(
                "bos6ye",
                "Moh. Abdul"
        );

        result.add(one);
        result.add(six);
        result.add(seven);
        result.add(two);
        result.add(three);
        result.add(four);
        result.add(five);

        LinearLayoutManager llm = new LinearLayoutManager(getApplication());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        ca = new ReportsAdapter(result,AdminViewReportsActivity.this);
        recyclerView.setAdapter(ca);
    }



}
