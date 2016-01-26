package com.woofyapp.pubnub.presenter;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.application.PubnubApp;
import com.woofyapp.pubnub.database.chat;
import com.woofyapp.pubnub.database.chatDao;
import com.woofyapp.pubnub.interfaces.GroupChatInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rujul on 1/25/2016.
 */
public class GroupChatPresenter {
    private GroupChatInterface view;
    private chatDao dao;


    public GroupChatPresenter(GroupChatInterface view,chatDao chatDao){
        this.view = view;
        this.dao = chatDao;
    }

    public void onSendClicked(){
        String message = view.getMessage();
        if(message.isEmpty())
            return;

        chat c = new chat();
        c.setAt(getCurrentDate());
        c.setGroupId(view.getGroupId());
        c.setFrom(PubnubApp.mUser.username);
        c.setMessage(message);
        dao.insert(c);
        view.insertChat(c);
    }

    private Date getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        return date;
    }
}
