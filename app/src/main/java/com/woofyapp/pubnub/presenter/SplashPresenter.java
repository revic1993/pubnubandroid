package com.woofyapp.pubnub.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.woofyapp.pubnub.SplashActivity;
import com.woofyapp.pubnub.UserDetails;
import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.application.PubnubApp;
import com.woofyapp.pubnub.interfaces.SplashViewInterface;
import com.woofyapp.pubnub.services.NetworkService;
import com.woofyapp.pubnub.services.SharedPreferenceService;

/**
 * Created by rujul on 1/15/2016.
 */
public class SplashPresenter {

    private final NetworkService nws;
    private final SharedPreferenceService spfs;
    private final SplashViewInterface view;
    private Context context;
    private boolean userExist;
    public SplashPresenter(NetworkService nws,
                           SharedPreferenceService spfs,
                           SplashViewInterface view,Context context) {
        this.nws = nws;
        this.spfs = spfs;
        this.view = view;
        this.context = context;
        this.userExist = spfs.getBoolData(Constants.USER_EXIST);
        saveUser();
    }


    public void onTryClicked() {
        if(nws.isConnected(context)){
            view.startNewActivity(spfs.getBoolData(Constants.USER_EXIST));
        }else{
            view.changeButtonVisibility();
        }
    }

    public void startSplash(){
        if(nws.isConnected(context)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.startNewActivity(userExist);
                }
            }, Constants.SPLASH_TIME_OUT);
        }else{
            view.changeButtonVisibility();
        }
    }

    private void saveUser(){
        PubnubApp.mUser.isSet = userExist;
        if(userExist){
            PubnubApp.mUser.mobileNo = spfs.getStringData(Constants.MOBILE);
            PubnubApp.mUser.username = spfs.getStringData(Constants.NAME);
            PubnubApp.mUser.userId = spfs.getStringData(Constants.ID);
        }
    }
}
