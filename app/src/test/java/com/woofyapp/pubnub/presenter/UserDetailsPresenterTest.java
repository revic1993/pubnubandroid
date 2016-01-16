package com.woofyapp.pubnub.presenter;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.UserDetailView;
import com.woofyapp.pubnub.services.SharedPreferenceService;
import com.woofyapp.pubnub.services.VolleyService;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by rujul on 1/15/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsPresenterTest extends TestCase {

    private UserDetailsPresenter presenter;
    private String UserString = "Please enter alphabets only",
                   PhoneString = "Please enter 10 digit phone number";
    @Mock
    private UserDetailView view;
    @Mock
    private VolleyService volley;

    @Mock
    private SharedPreferenceService spfs;

    @Before
    public void setUp(){
        presenter = new UserDetailsPresenter(view,volley,spfs);
    }


    @Test
    public void testEmptyUser(){
        when(view.getUsername()).thenReturn("");
        when(view.getPhoneNumber()).thenReturn("");

        presenter.onClick();

        verify(view).errorUser(UserString);
    }

    @Test
    public void testUsernameError(){
        when(view.getUsername()).thenReturn("123123");
        when(view.getPhoneNumber()).thenReturn("9099918588");

        presenter.onClick();

        verify(view).errorUser(UserString);
        verify(view,never()).errorPhone(PhoneString);
    }

    @Test
    public void testPhoneNumber() {
        when(view.getUsername()).thenReturn("asdf");
        when(view.getPhoneNumber()).thenReturn("*****");

        presenter.onClick();

        verify(view).errorPhone(PhoneString);
        verify(view,never()).errorUser(UserString);

    }

    @Test
    public void testVolleyAttempt() {
        when(view.getUsername()).thenReturn("Rujul");
        when(view.getPhoneNumber()).thenReturn("9099918588");

        presenter.onClick();
        Map<String,String> params = new HashMap<String,String>();
        params.put(Constants.TOKEN_NAME, Constants.TOKEN);
        params.put(Constants.USER, "Rujul");
        params.put(Constants.MOBILE, "9099918588");
        verify(volley).makePostRequest(Constants.BASE_URL+Constants.NEW_USER,
                                      params,presenter);


    }
}