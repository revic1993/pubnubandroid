package com.woofyapp.pubnub.presenter;

import android.util.Log;

import com.android.volley.VolleyError;
import com.woofyapp.pubnub.R;
import com.woofyapp.pubnub.UserDetails;
import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.application.PubnubApp;
import com.woofyapp.pubnub.database.DaoSession;
import com.woofyapp.pubnub.database.Group;
import com.woofyapp.pubnub.interfaces.UserDetailView;
import com.woofyapp.pubnub.interfaces.VolleyInterface;
import com.woofyapp.pubnub.services.SharedPreferenceService;
import com.woofyapp.pubnub.services.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by rujul on 1/15/2016.
 */
public class UserDetailsPresenter implements VolleyInterface {

    private UserDetailView view;
    private SharedPreferenceService spfs;
    private Pattern pName,pNumber;
    private VolleyService volley;
    public UserDetailsPresenter(UserDetailView view,VolleyService volley
                                ,SharedPreferenceService spfs) {
        this.view = view;
        this.spfs = spfs;
        pName =  Pattern.compile("^[a-zA-Z]+$");
        pNumber = Pattern.compile("\\d{10}");
        this.volley = volley;
    }

    public void onClick() {
        String username = view.getUsername(),phone = view.getPhoneNumber();


        if(!pNumber.matcher(phone).matches()){
            view.errorPhone("Please enter 10 digit phone number");
            return;
        }

        if(!pName.matcher(username).matches()){
            view.errorUser("Please enter alphabets only");
            return;
        }

        Map<String,String> params = new HashMap<String,String>();
        params.put(Constants.USER,username);
        params.put(Constants.MOBILE,phone);
        volley.makePostRequest(Constants.BASE_URL+Constants.NEW_USER,params,this);
    }

    @Override
    public void onSuccess(JSONObject json) {

        try {
            JSONObject data = json.getJSONObject(Constants.DATA);
            JSONObject user = data.getJSONObject(Constants.USER);

            PubnubApp.mUser.username = user.getString(Constants.NAME);
            PubnubApp.mUser.mobileNo = user.getString(Constants.MOBILE);
            PubnubApp.mUser.userId = user.getString(Constants.ID);
            PubnubApp.mUser.isSet = true;

            spfs.putData(Constants.USER_EXIST,true);
            spfs.putData(Constants.ID,user.getString(Constants.ID));
            spfs.putData(Constants.NAME,user.getString(Constants.NAME));
            spfs.putData(Constants.MOBILE, user.getString(Constants.MOBILE) + "");

            if(data.getBoolean(Constants.HAS_GROUP)){
                JSONArray groupArray = data.getJSONArray(Constants.GROUP);
                Group [] groups = new Group[groupArray.length()];
                DaoSession session = PubnubApp.getApp().getDaoSession();
                for(int i=0;i<groupArray.length();i++){

                    Group grp = new Group();
                    JSONObject tempGroup = groupArray.getJSONObject(i);
                    grp.setGroupName(tempGroup.getString(Constants.NAME));
                    grp.setGroupId(tempGroup.getString(Constants.ID));
                    groups[i] = grp;
                }

                session.getGroupDao().insertInTx(groups);
            }

            view.startMainActivity(data.getBoolean(Constants.IS_USER_NEW));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(VolleyError error) {
        try {
            JSONObject err =new JSONObject(error.networkResponse.data+"");
            PubnubApp.makeToast(err.getString(Constants.MESSAGE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
