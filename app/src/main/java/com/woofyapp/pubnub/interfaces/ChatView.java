package com.woofyapp.pubnub.interfaces;

import com.woofyapp.pubnub.database.Group;

/**
 * Created by rujul on 1/17/2016.
 */
public interface ChatView {
    public String getChannelName();
    public void showChannelNameError(String message);
    public void dataSetChanged(Group group);

}
