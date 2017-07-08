package com.hackathon.s_shield.Review.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hackathon.s_shield.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aloush on 9/21/2016.
 */
public class MsgsAdapter extends RecyclerView.Adapter<MsgsAdapter.TasjeelViewHolder>  {
    List<String> urls=new ArrayList<String>();

    public static class TasjeelViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;

        List<String> urls;

        CardView cv;
        TextView name,msg;
        LinearLayout rl;

      //  LinearLayout audioLayoutOne,audioLayoutSecond;

        //        ImageView personPhoto;

        List<Msg> recs;
        TasjeelViewHolder(View itemView, List<Msg> recz) {
            super(itemView);

            recs=recz;

            cv = (CardView) itemView.findViewById(R.id.card_vieww);

            ////////// Initialize views //////////////////

            name = (TextView) itemView.findViewById(R.id.nametv);
            msg = (TextView) itemView.findViewById(R.id.tvmsg);
            rl= (LinearLayout) itemView.findViewById(R.id.layoutMsg);

        }


    }//end the


    List<Msg> recs;
    public static Activity myActivity;
    Context theContext;

    public MsgsAdapter(List<Msg> recs, Activity myActivity) {
        this.recs = recs;
        this.myActivity = myActivity;
    }


    public void resetList(List<Msg> recs){this.recs=recs;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TasjeelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        TasjeelViewHolder pvh = new TasjeelViewHolder(v,recs);
        return pvh;
    }



    @Override
    public void onBindViewHolder(final TasjeelViewHolder personViewHolder, int i) {

        personViewHolder.name.setText(recs.get(i).getUsername());
        personViewHolder.msg.setText(String.valueOf(recs.get(i).getMessage()));

                Resources res = myActivity.getResources(); //resource handle
        Drawable drawable = res.getDrawable(R.drawable.rounded_corner1); //new Image that was added to the res folder

        if(!recs.get(i).isMe()) {
            personViewHolder.rl.setBackground(drawable);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.FILL_PARENT);
          //  params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;

            personViewHolder.rl.setLayoutParams(params);

        }//if its NOT my Message
        else{

            drawable = res.getDrawable(R.drawable.rounded_corner); //new Image that was added to the res folder
            personViewHolder.rl.setBackground(drawable);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.FILL_PARENT);
            //  params.weight = 1.0f;
            params.gravity = Gravity.LEFT;

            personViewHolder.rl.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return recs.size();
    }


    public void clear() {
        final int size = getItemCount();
        recs.clear();
        notifyItemRangeRemoved(0, size);
}


    public void clearData() {
        int size = this.recs.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.recs.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

}
