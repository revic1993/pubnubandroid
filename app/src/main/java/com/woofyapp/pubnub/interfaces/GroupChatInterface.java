package com.woofyapp.pubnub.interfaces;

import com.woofyapp.pubnub.database.chat;

/**
 * Created by rujul on 1/25/2016.
 */
public interface GroupChatInterface {
    String getMessage();

    String getGroupId();

    void insertChat(chat c);
}
