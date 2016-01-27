package com.woofyapp.pubnub.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.woofyapp.pubnub.database.DaoMaster;
import com.woofyapp.pubnub.database.DaoSession;
import com.woofyapp.pubnub.models.PubNubInteractor;
import com.woofyapp.pubnub.models.User;

/**
 * Created by rujul on 1/16/2016.
 */
public class PubnubApp extends Application{
    static PubnubApp mPubnub;
    RequestQueue mRequestQueue;
    DaoSession daoSession;
    public static User mUser;


    @Override
    public void onCreate() {
        super.onCreate();
        mPubnub = this;
        mUser = new User();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static synchronized PubnubApp getApp(){
        return mPubnub;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag) {
        req.setTag(TextUtils.isEmpty(tag)? Constants.TAG : tag);
        getRequestQueue().add(req);
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }


    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static void makeToast(String message){
        Toast.makeText(mPubnub, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
