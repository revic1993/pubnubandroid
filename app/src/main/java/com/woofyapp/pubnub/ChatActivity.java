package com.woofyapp.pubnub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.woofyapp.pubnub.presenter.ChatPresenter;
import com.woofyapp.pubnub.interfaces.ChatView;
import com.woofyapp.pubnub.services.SharedPreferenceService;


public class ChatActivity extends AppCompatActivity implements ChatView{

     ChatPresenter presenter;
     View channelDialog;
     EditText etAddChannel;
     AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        channelDialog =  LayoutInflater.from(this).inflate(R.layout.dialog_add_channel, null);
        etAddChannel = (EditText) channelDialog.findViewById(R.id.etChannelName);


        presenter = new ChatPresenter(this,new SharedPreferenceService(this));
        createChannelDialog();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddOrJoin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChannelDialog();
            }
        });
    }

    private void showChannelDialog() {
        dialog.show();
    }

    private void createChannelDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(channelDialog);

        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Add/Join group");

        dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener(){

            @Override
            public void onShow(DialogInterface d) {
                Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(presenter.onOkClicked()){
                            dialog.dismiss();
                            presenter.addOrJoinGroup();
                        }
                    }
                });
            }
        });

    }

    @Override
    public String getChannelName() {
        return etAddChannel.getText().toString();
    }

    @Override
    public void showChannelNameError(String message) {
        etAddChannel.setError(message);
    }
}
