package com.woofyapp.pubnub.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.woofyapp.pubnub.database.Group;
import com.woofyapp.pubnub.database.chat;

import com.woofyapp.pubnub.database.GroupDao;
import com.woofyapp.pubnub.database.chatDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig groupDaoConfig;
    private final DaoConfig chatDaoConfig;

    private final GroupDao groupDao;
    private final chatDao chatDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        groupDaoConfig = daoConfigMap.get(GroupDao.class).clone();
        groupDaoConfig.initIdentityScope(type);

        chatDaoConfig = daoConfigMap.get(chatDao.class).clone();
        chatDaoConfig.initIdentityScope(type);

        groupDao = new GroupDao(groupDaoConfig, this);
        chatDao = new chatDao(chatDaoConfig, this);

        registerDao(Group.class, groupDao);
        registerDao(chat.class, chatDao);
    }
    
    public void clear() {
        groupDaoConfig.getIdentityScope().clear();
        chatDaoConfig.getIdentityScope().clear();
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public chatDao getChatDao() {
        return chatDao;
    }

}