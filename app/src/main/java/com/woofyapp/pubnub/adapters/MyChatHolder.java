package com.woofyapp.pubnub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.woofyapp.pubnub.R;

import org.w3c.dom.Text;

/**
 * Created by rujul on 1/26/2016.
 */
public class MyChatHolder extends RecyclerView.ViewHolder {


    TextView myMessage;
    public MyChatHolder(View itemView) {
        super(itemView);
        myMessage = (TextView) itemView.findViewById(R.id.tvMyMessage);
    }

    public void setMyMessage(String message) {
        myMessage.setText(message);
    }

}
