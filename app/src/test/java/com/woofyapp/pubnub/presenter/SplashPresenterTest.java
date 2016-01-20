package com.woofyapp.pubnub.presenter;

import android.content.Context;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.SplashViewInterface;
import com.woofyapp.pubnub.services.NetworkService;
import com.woofyapp.pubnub.services.SharedPreferenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by rujul on 1/15/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest  {

    private SplashPresenter pSplash;

    @Mock
    private NetworkService nws;
    @Mock
    private SharedPreferenceService spfs;
    @Mock
    private Context context;
    @Mock
    private SplashViewInterface view;

    @Before
    public void setUp(){
        pSplash = new SplashPresenter(nws,spfs,view,context);
    }

    @Test
    public void shouldEnableTryButton() throws Exception{
        when(nws.isConnected(context)).thenReturn(false);
        pSplash.startSplash();
        verify(view).changeButtonVisibility();
    }

    @Test
    public void shouldStartUserActivity() throws Exception{
        when(nws.isConnected(context)).thenReturn(true);
        when(spfs.getBoolData(Constants.USER_EXIST)).thenReturn(false);
        pSplash.onTryClicked();
        verify(view).startNewActivity(true);
    }

    @Test
    public void shouldStartMainActivity() throws Exception{
        when(nws.isConnected(context)).thenReturn(true);
        when(spfs.getBoolData(Constants.USER_EXIST)).thenReturn(true);
        pSplash.onTryClicked();
        verify(view).startNewActivity(false);
    }
}