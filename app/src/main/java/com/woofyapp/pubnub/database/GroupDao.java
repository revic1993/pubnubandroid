package com.woofyapp.pubnub.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.woofyapp.pubnub.database.Group;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GROUP".
*/
public class GroupDao extends AbstractDao<Group, String> {

    public static final String TABLENAME = "GROUP";

    /**
     * Properties of entity Group.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property GroupId = new Property(0, String.class, "groupId", true, "GROUP_ID");
        public final static Property GroupName = new Property(1, String.class, "groupName", false, "GROUP_NAME");
    };

    private DaoSession daoSession;


    public GroupDao(DaoConfig config) {
        super(config);
    }
    
    public GroupDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GROUP\" (" + //
                "\"GROUP_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: groupId
                "\"GROUP_NAME\" TEXT);"); // 1: groupName
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GROUP\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Group entity) {
        stmt.clearBindings();
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(1, groupId);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(2, groupName);
        }
    }

    @Override
    protected void attachEntity(Group entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Group readEntity(Cursor cursor, int offset) {
        Group entity = new Group( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // groupId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // groupName
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Group entity, int offset) {
        entity.setGroupId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setGroupName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Group entity, long rowId) {
        return entity.getGroupId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Group entity) {
        if(entity != null) {
            return entity.getGroupId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
