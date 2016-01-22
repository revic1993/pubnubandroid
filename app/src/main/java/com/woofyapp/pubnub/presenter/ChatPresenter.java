package com.woofyapp.pubnub.presenter;

import android.util.Log;

import com.android.volley.VolleyError;
import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.database.DaoSession;
import com.woofyapp.pubnub.database.Group;
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

    ChatView view;
    String channelName;
    VolleyService volley;
    SharedPreferenceService spfs;
    DaoSession session;

    public ChatPresenter(ChatView view,SharedPreferenceService spfs,DaoSession session) {
        this.view = view;
        this.spfs = spfs;
        this.session = session;
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
            JSONObject responseGroup = json.getJSONObject(Constants.DATA);
            Group group = new Group();
            Log.d("greenDao",responseGroup.getString(Constants.ID));
            Log.d("greenDao",responseGroup.getString(Constants.NAME));

            group.setGroupId(responseGroup.getString(Constants.ID));
            group.setGroupName(responseGroup.getString(Constants.NAME));
            session.getGroupDao().insert(group);
            view.dataSetChanged(group);

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
