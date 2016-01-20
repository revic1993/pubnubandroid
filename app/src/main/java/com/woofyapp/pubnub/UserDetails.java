package com.woofyapp.pubnub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.UserDetailView;
import com.woofyapp.pubnub.presenter.UserDetailsPresenter;
import com.woofyapp.pubnub.services.SharedPreferenceService;
import com.woofyapp.pubnub.services.VolleyService;

public class UserDetails extends AppCompatActivity implements UserDetailView{

    private EditText etPhone,etUser;
    private Button bRegister;
    private UserDetailsPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        etPhone =   (EditText) findViewById(R.id.etPhone);
        etUser =    (EditText) findViewById(R.id.etUsername);
        bRegister = (Button) findViewById(R.id.bUser);
        userPresenter = new UserDetailsPresenter(this,new VolleyService(),new SharedPreferenceService(this));
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPresenter.onClick();
            }
        });

    }


    @Override
    public String getUsername() {
        return etUser.getText().toString();
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString();
    }

    @Override
    public void errorPhone(String message) {
        etPhone.setError(message);
    }

    @Override
    public void errorUser(String message) {
        etUser.setError(message);
    }

    @Override
    public void startMainActivity(boolean aBoolean) {
        Intent i = new Intent(this,ChatActivity.class);
        i.putExtra(Constants.IS_USER_NEW,aBoolean);
        startActivity(i);
        finish();
    }


}
