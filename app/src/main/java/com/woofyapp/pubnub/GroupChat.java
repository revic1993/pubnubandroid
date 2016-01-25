package com.woofyapp.pubnub;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.application.PubnubApp;
import com.woofyapp.pubnub.database.DaoSession;
import com.woofyapp.pubnub.database.Group;
import com.woofyapp.pubnub.database.GroupDao;
import com.woofyapp.pubnub.database.chatDao;

public class GroupChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        setSupportActionBar(toolbar);

        String grpId = getIntent().getExtras().getString(Constants.ID, "");
        DaoSession session = PubnubApp.getApp().getDaoSession();
        GroupDao groupDao = session.getGroupDao();
        Group group = groupDao.queryBuilder().where(GroupDao.Properties.GroupId.eq(grpId)).unique();
        tvTitle.setText(group.getGroupName());
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Bold.ttf");
        tvTitle.setTypeface(tf);
        TextView tvChatBubble = (TextView) findViewById(R.id.tvTheirName);
        tvTitle.setTypeface(tf);
    }
}
