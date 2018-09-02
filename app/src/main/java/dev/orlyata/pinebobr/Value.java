package dev.orlyata.pinebobr;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.List;


@Entity(active = true, nameInDb = "VALUES")
public class Value {
    @Id
    private Long id;
    /**
     * null - without devices
     * kettle
     * bulb
     * phone
     * laptop
     */
    private String types;
    private String values;
    @NotNull
    private String timestamp;
    private int val;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 765159173)
    private transient ValueDao myDao;

    @Generated(hash = 702652588)
    public Value(Long id, String types, String values, @NotNull String timestamp, int val) {
        this.id = id;
        this.types = types;
        this.values = values;
        this.timestamp = timestamp;
        this.val = val;
    }

    @Generated(hash = 318810525)
    public Value() {
    }

    public Value(String types, String values, String timestamp, int val){
        this.types = types;
        this.values = values;
        this.timestamp = timestamp;
        this.val = val;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2122403398)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getValueDao() : null;
    }

    public long getVal() {
        return this.val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
