package com.woofyapp.pubnub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.SplashViewInterface;
import com.woofyapp.pubnub.presenter.SplashPresenter;
import com.woofyapp.pubnub.services.NetworkService;
import com.woofyapp.pubnub.services.SharedPreferenceService;

public class SplashActivity extends AppCompatActivity implements SplashViewInterface {

    private Button bTryAgain;
    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        presenter = new SplashPresenter(new NetworkService(),
                new SharedPreferenceService(this),this,this);
        bTryAgain = (Button) findViewById(R.id.bTryAgain);

        bTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTryClicked();
            }
        });

        presenter.startSplash();
    }


    @Override
    public void changeButtonVisibility() {
        bTryAgain.setVisibility(View.VISIBLE);
        Toast.makeText(SplashActivity.this,R.string.err_connection,Toast.LENGTH_LONG).show();
    }

    @Override
    public void startNewActivity(boolean isDefault) {
        Intent i;
        if(isDefault)
            i = new Intent(SplashActivity.this,UserDetails.class);
        else
            i = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
