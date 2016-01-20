package com.woofyapp.pubnub.presenter;

import android.util.Log;

import com.android.volley.VolleyError;
import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.ChatView;
import com.woofyapp.pubnub.interfaces.VolleyInterface;
import com.woofyapp.pubnub.services.SharedPreferenceService;
import com.woofyapp.pubnub.services.VolleyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by rujul on 1/17/2016.
 */
public class ChatPresenter implements VolleyInterface{

    private ChatView view;
    private String channelName;
    private VolleyService volley;
    private SharedPreferenceService spfs;

    public ChatPresenter(ChatView view,SharedPreferenceService spfs) {
        this.view = view;
        this.spfs = spfs;
    }


    public boolean onOkClicked(){
        channelName = view.getChannelName();
        Pattern pattern = Pattern.compile(Constants.ALPHA_NUM);
        if(!pattern.matcher(channelName).matches() || channelName.isEmpty()){
            view.showChannelNameError(Constants.ALPHA_NUM_ERROR);
            return false;
        }
        return true;
    }


    public void addOrJoinGroup() {
        if(volley==null)
            volley = new VolleyService();


        Map<String,String> params = new HashMap<String,String>();
        params.put(Constants.GROUP_NAME,channelName);
        params.put(Constants.NAME,spfs.getStringData(Constants.NAME));
        params.put(Constants.MOBILE,spfs.getStringData(Constants.MOBILE));
        volley.makePostRequest(Constants.BASE_URL+Constants.NEW_GROUP,params,this);
    }

    @Override
    public void onSuccess(JSONObject json) {
        try {
            Log.d("groupcontroller",json.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(VolleyError error) {
        try {
            JSONObject err =new JSONObject(error.networkResponse.data+"");
            Log.d("groupcontroller",err.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
