package com.woofyapp.pubnub.database;


import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.AbstractDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CHAT".
 */
public class chat {

    private Long id;
    private String message;
    private String from;
    private java.util.Date at;
    private String groupId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient chatDao myDao;

    private Group group;
    private String group__resolvedKey;


    public chat() {
    }

    public chat(Long id) {
        this.id = id;
    }

    public chat(Long id, String message, String from, java.util.Date at, String groupId) {
        this.id = id;
        this.message = message;
        this.from = from;
        this.at = at;
        this.groupId = groupId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChatDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public java.util.Date getAt() {
        return at;
    }

    public void setAt(java.util.Date at) {
        this.at = at;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /** To-one relationship, resolved on first access. */
    public Group getGroup() {
        String __key = this.groupId;
        if (group__resolvedKey == null || group__resolvedKey != __key) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GroupDao targetDao = daoSession.getGroupDao();
            Group groupNew = targetDao.load(__key);
            synchronized (this) {
                group = groupNew;
            	group__resolvedKey = __key;
            }
        }
        return group;
    }

    public void setGroup(Group group) {
        synchronized (this) {
            this.group = group;
            groupId = group == null ? null : group.getGroupId();
            group__resolvedKey = groupId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}