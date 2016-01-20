package com.woofyapp.pubnub.presenter;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.ChatView;

import java.util.regex.Pattern;

/**
 * Created by rujul on 1/17/2016.
 */
public class ChatPresenter {

    private ChatView view;

    public ChatPresenter(ChatView view) {
        this.view = view;
    }

    public boolean onOkClicked(){
        String groupName = view.getChannelName();
        Pattern pattern = Pattern.compile(Constants.ALPHA_NUM);
        if(!pattern.matcher(groupName).matches() || groupName.isEmpty()){
            view.showChannelNameError(Constants.ALPHA_NUM_ERROR);
            return false;
        }
        return true;
    }
}
