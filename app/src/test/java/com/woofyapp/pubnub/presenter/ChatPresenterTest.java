package com.woofyapp.pubnub.presenter;

import com.woofyapp.pubnub.application.Constants;
import com.woofyapp.pubnub.interfaces.ChatView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rujul on 1/17/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class ChatPresenterTest {

    private ChatPresenter presenter;

    @Mock
    private ChatView view;

    @Before
    public void setUp(){
        presenter = new ChatPresenter(view);
    }

    @Test
    public void testEmptyGroupName() throws Exception {
        when(view.getChannelName()).thenReturn("");
        presenter.onOkClicked();
        verify(view).showChannelNameError(Constants.ALPHA_NUM_ERROR);
    }

    @Test
    public void testInvalidString() throws Exception {
        when(view.getChannelName()).thenReturn("123@@31@!@#");
        presenter.onOkClicked();
        verify(view).showChannelNameError(Constants.ALPHA_NUM_ERROR);
    }
}