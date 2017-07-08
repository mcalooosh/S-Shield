package com.hackathon.s_shield.Review;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hackathon.s_shield.R;
import com.hackathon.s_shield.Review.Adapters.Msg;
import com.hackathon.s_shield.Review.Adapters.MsgsAdapter;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat_Room extends AppCompatActivity {

    private Button btn_send_msg;
    private EditText input_msg;


    private String user_name, case_id;

    private String temp_key;

    ArrayList<Msg> messages;
    MsgsAdapter adapter;

    RecyclerView recList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        //UI
        initializeUI();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Info
        user_name = "Me";
        case_id = getIntent().getExtras().get("case_id").toString();

        try {
            JSONObject caseId=new JSONObject(case_id);
            setTitle("Reviewing case id #" + caseId.get("Id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        addChatMessage("Me","I got abused by a guy called James, he is at grade 10. today morning " +
                "he started to hit me on my head infront of my friends");
        addChatMessage("Admin","Hello dear, can you tell me what time was it? any teachers were around?");
        addChatMessage("Me","I duno, it was right after the second class and no teachers were around");
        addChatMessage("Admin","Thank you for reportin dear, we are handling this don't worry");

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Update the sender user
                String theMessage=input_msg.getText().toString();

                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", user_name);
                map2.put("msg", input_msg.getText().toString());

                addChatMessage("Me",theMessage);
                input_msg.setText("");
            }
        });


    }


    private void initializeUI() {

        btn_send_msg = (Button) findViewById(R.id.btn_send);
        input_msg = (EditText) findViewById(R.id.msg_input);


        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        messages = new ArrayList<Msg>();

//        messages.add(new Msg("Me","Hello", true));
//        messages.add(new Msg("Me","Kifak", true));
//        messages.add(new Msg("Sara","Mni7a", false));
//        messages.add(new Msg("Me","Ok bye!", true));


        adapter = new MsgsAdapter(messages, this);
        //  setListAdapter(adapter);

        recList.setAdapter(adapter);
    }

    private void addChatMessage(String chat_user, String chat_msg) {


        if (chat_user.equals(user_name))
            messages.add(new Msg("Me", chat_msg, true));
        else
            messages.add(new Msg(chat_user, chat_msg, false));


        //  adapter.notifyItemRangeChanged(messages.size()-1, messages.size());
        adapter.notifyDataSetChanged();
        //   recList.scrollToPosition(messages.size());
        recList.scrollToPosition(recList.getAdapter().getItemCount() - 1);


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}

