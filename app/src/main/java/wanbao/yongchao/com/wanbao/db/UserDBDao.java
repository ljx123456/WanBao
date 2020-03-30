package wanbao.yongchao.com.wanbao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import wanbao.yongchao.com.wanbao.db.db.UserDB;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_DB".
*/
public class UserDBDao extends AbstractDao<UserDB, Long> {

    public static final String TABLENAME = "USER_DB";

    /**
     * Properties of entity UserDB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Token = new Property(1, String.class, "token", false, "TOKEN");
        public final static Property Avatar = new Property(2, String.class, "avatar", false, "AVATAR");
        public final static Property UserId = new Property(3, String.class, "userId", false, "USER_ID");
        public final static Property NickName = new Property(4, String.class, "nickName", false, "NICK_NAME");
        public final static Property Signature = new Property(5, String.class, "signature", false, "SIGNATURE");
        public final static Property Role = new Property(6, int.class, "role", false, "ROLE");
        public final static Property AccountState = new Property(7, int.class, "accountState", false, "ACCOUNT_STATE");
        public final static Property AuthState = new Property(8, int.class, "authState", false, "AUTH_STATE");
    }


    public UserDBDao(DaoConfig config) {
        super(config);
    }
    
    public UserDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_DB\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TOKEN\" TEXT," + // 1: token
                "\"AVATAR\" TEXT," + // 2: avatar
                "\"USER_ID\" TEXT," + // 3: userId
                "\"NICK_NAME\" TEXT," + // 4: nickName
                "\"SIGNATURE\" TEXT," + // 5: signature
                "\"ROLE\" INTEGER NOT NULL ," + // 6: role
                "\"ACCOUNT_STATE\" INTEGER NOT NULL ," + // 7: accountState
                "\"AUTH_STATE\" INTEGER NOT NULL );"); // 8: authState
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_DB\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserDB entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(2, token);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(3, avatar);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(4, userId);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(5, nickName);
        }
 
        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(6, signature);
        }
        stmt.bindLong(7, entity.getRole());
        stmt.bindLong(8, entity.getAccountState());
        stmt.bindLong(9, entity.getAuthState());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserDB entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(2, token);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(3, avatar);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(4, userId);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(5, nickName);
        }
 
        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(6, signature);
        }
        stmt.bindLong(7, entity.getRole());
        stmt.bindLong(8, entity.getAccountState());
        stmt.bindLong(9, entity.getAuthState());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserDB readEntity(Cursor cursor, int offset) {
        UserDB entity = new UserDB( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // token
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // avatar
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // nickName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // signature
            cursor.getInt(offset + 6), // role
            cursor.getInt(offset + 7), // accountState
            cursor.getInt(offset + 8) // authState
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserDB entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setToken(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAvatar(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNickName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSignature(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRole(cursor.getInt(offset + 6));
        entity.setAccountState(cursor.getInt(offset + 7));
        entity.setAuthState(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserDB entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserDB entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserDB entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}