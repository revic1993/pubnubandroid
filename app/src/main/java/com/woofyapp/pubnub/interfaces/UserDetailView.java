package com.woofyapp.pubnub.interfaces;

/**
 * Created by rujul on 1/15/2016.
 */
public interface UserDetailView {
    public String getUsername();
    public String getPhoneNumber();

    public void errorPhone(String message);

    public void errorUser(String message);

    public void startMainActivity(boolean aBoolean);
}
