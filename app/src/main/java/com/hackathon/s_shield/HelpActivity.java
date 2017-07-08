package com.hackathon.s_shield;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    RadioGroup identityRG;
    EditText identityET,reportText;
    TextView attachedTv;
    String userIdentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initViews();
    }
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
            sendReport();

    }

    private void sendReport(){

    }


    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private void pickPictures(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pictures"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                }else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        attachedTv.setText(mArrayUri.size()+" attached picture(s)");
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
