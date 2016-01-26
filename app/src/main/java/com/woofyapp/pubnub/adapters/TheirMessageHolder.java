package com.woofyapp.pubnub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.woofyapp.pubnub.R;

/**
 * Created by rujul on 1/26/2016.
 */
public class TheirMessageHolder extends RecyclerView.ViewHolder {

    TextView username,theirMessage;
    public TheirMessageHolder(View itemView) {
        super(itemView);
        username = (TextView) itemView.findViewById(R.id.tvTheirName);
        theirMessage = (TextView) itemView.findViewById(R.id.tvTheirMessage);
    }

    public void setTheirChat(String user,String message){
        username.setText(user);
        theirMessage.setText(message);
    }
}
