package com.woofyapp.pubnub;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.woofyapp.pubnub.interfaces.SplashViewInterface;
import com.woofyapp.pubnub.presenter.SplashPresenter;
import com.woofyapp.pubnub.services.NetworkService;
import com.woofyapp.pubnub.services.SharedPreferenceService;

import java.lang.reflect.Type;

public class SplashActivity extends AppCompatActivity implements SplashViewInterface {

    private Button bTryAgain;
    private SplashPresenter presenter;
    private TextView tvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        presenter = new SplashPresenter(new NetworkService(),
                new SharedPreferenceService(this),this,this);
        bTryAgain = (Button) findViewById(R.id.bTryAgain);
        tvSplash = (TextView) findViewById(R.id.tvSplash);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        tvSplash.setTypeface(tf);
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
    public void startNewActivity(boolean userExist) {
        Intent i;
        if(!userExist)
            i = new Intent(SplashActivity.this,UserDetails.class);
        else
            i = new Intent(SplashActivity.this,ChatActivity.class);
        startActivity(i);
        finish();
    }
}
