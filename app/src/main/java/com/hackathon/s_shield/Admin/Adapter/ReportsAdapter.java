package com.hackathon.s_shield.Admin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hackathon.s_shield.R;

import java.util.List;

/**
 * Created by Aloush on 11/20/2016.
 */
public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ScoreViewHolder> {

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {


        CardView cv;
        TextView caseId,byTV;
        LinearLayout cardlay;
        List<Report> recs;
        ScoreViewHolder(View itemView, List<Report> recz) {
            super(itemView);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            recs=recz;


            cv = (CardView) itemView.findViewById(R.id.cv);

            ////////// Initialize views //////////////////

            caseId = (TextView) itemView.findViewById(R.id.caseIdTvResult);
            byTV = (TextView) itemView.findViewById(R.id.byTv);

            cardlay=(LinearLayout)itemView.findViewById(R.id.cardlay);
        }



    }//end the



    List<Report> recs;
    public static Activity myActivity;

    public ReportsAdapter(List<Report> recs, Activity myActivity) {
        this.recs = recs;
        this.myActivity = myActivity;
    }


    public void resetList(List<Report> recs){this.recs=recs;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowcard, viewGroup, false);
        ScoreViewHolder pvh = new ScoreViewHolder(v,recs);
        return pvh;
    }


    // setting the card UI components setText w osas

    @Override
    public void onBindViewHolder(final ScoreViewHolder personViewHolder, int i) {

        final Context context=myActivity.getApplicationContext();

        personViewHolder.caseId.setText("Case: #"+recs.get(i).getcaseId());
        personViewHolder.byTV.setText("By: "+String.valueOf(recs.get(i).getbyWho()));


    }
        @Override
    public int getItemCount() {
        return recs.size();
    }

    }
