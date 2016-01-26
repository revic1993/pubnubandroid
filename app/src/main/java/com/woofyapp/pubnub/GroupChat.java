package com.woofyapp.pubnub;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.woofyapp.pubnub.adapters.ChatAdapter;
import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.application.PubnubApp;
import com.woofyapp.pubnub.database.DaoSession;
import com.woofyapp.pubnub.database.Group;
import com.woofyapp.pubnub.database.GroupDao;
import com.woofyapp.pubnub.database.chat;
import com.woofyapp.pubnub.database.chatDao;
import com.woofyapp.pubnub.interfaces.GroupChatInterface;
import com.woofyapp.pubnub.interfaces.PubNubInterface;
import com.woofyapp.pubnub.models.PubNubInteractor;
import com.woofyapp.pubnub.presenter.GroupChatPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupChat extends AppCompatActivity implements GroupChatInterface,PubNubInterface{


    TextView tvTitle;
    DaoSession session;
    List<chat> chats;
    ChatAdapter adapter;
    RecyclerView rvChats;
    EditText etChatBox;
    ImageButton ibSend;
    PubNubInteractor interactor;
    GroupDao groupDao;
    List<Group> groups;
    GroupChatPresenter presenter;
    Group mGroup;
    chatDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        rvChats = (RecyclerView) findViewById(R.id.rvChat);
        etChatBox = (EditText) findViewById(R.id.etChatBox);
        ibSend = (ImageButton) findViewById(R.id.ibSend);
        setSupportActionBar(toolbar);

        initChat();
        initPubnub();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (interactor!=null ){
            if( interactor.isPubNubNull())
                initPubnub();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(!interactor.isPubNubNull())
            interactor.unsubscribeChannels();
    }

    private void initPubnub() {
        interactor = PubnubApp.getApp().getInteractor();
        interactor.setChatView(this);
        if(groups.size()!=0){
            String[] channelName = new String[groups.size()];
            for(int i=0;i<groups.size();i++){
                channelName[i] = groups.get(i).getGroupName();
            }
            interactor.subscribePubNub(channelName);
        }

    }

    private void initChat() {
        String grpId = getIntent().getExtras().getString(Constants.ID, "");
        session = PubnubApp.getApp().getDaoSession();
        groupDao = session.getGroupDao();
        groups = groupDao.loadAll();
        mGroup = new Group();
        for (Group tempGroup:groups) {
            if(tempGroup.getGroupId().equalsIgnoreCase(grpId)){
                mGroup = tempGroup;
                break;
            }
        }
        tvTitle.setText(mGroup.getGroupName());
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf");
        tvTitle.setTypeface(tf);
        dao = session.getChatDao();
        chats = dao._queryGroup_Chats(mGroup.getGroupId());


        if(chats.size() == 0){
            chats = new ArrayList<chat>();
        }


        adapter = new ChatAdapter(chats);
        rvChats.setAdapter(adapter);
        presenter = new GroupChatPresenter(this,dao);
        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSendClicked();
                Log.d("checkingCalls", "onclick");
            }
        });
    }

    @Override
    public String getMessage() {
        return etChatBox.getText().toString();
    }

    @Override
    public String getGroupId() {
        return mGroup.getGroupId();
    }

    @Override
    public void insertChat(chat c) {
        Log.d("checkingCalls", "insertChat");
        chats.add(c);
        adapter.notifyDataSetChanged();

        Map<String,String> params = new HashMap<String,String>();

        params.put(Constants.FROM,c.getFrom());
        params.put(Constants.AT,c.getAt()+"");
        params.put(Constants.MESSAGE,c.getMessage());
        params.put(Constants.ID,c.getGroupId());

        JSONObject data = new JSONObject(params);

        interactor.publishPubNub(mGroup.getGroupName(),data);
        etChatBox.setText("");
    }


    @Override
    public void Success(String channelName, Object data) {
        Log.d("checkingCalls", "success "+channelName);
        if(data instanceof JSONObject && channelName.equalsIgnoreCase(mGroup.getGroupName()) ){
            JSONObject chatData = (JSONObject) data;
            try {
                String username = chatData.getString(Constants.FROM);
                if(!username.equalsIgnoreCase(PubnubApp.mUser.username)){
                    chat newChat = new chat();
                    newChat.setFrom(username);
                    newChat.setAt(convertStringToDate(chatData.getString(Constants.AT)));
                    newChat.setMessage(chatData.getString(Constants.MESSAGE));
                    newChat.setGroupId(chatData.getString(Constants.ID));
                    dao.insert(newChat);
                    chats.add(newChat);
                    adapter.notifyDataSetChanged();
                    Log.d("checkingCalls", "successPublish");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Date convertStringToDate(String dateString){
        DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date=null;
        try{
            date = df.parse(dateString);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return date;
    }
}
