package lk.ac.ucsc.oms.cache.api.beans;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.sql.Timestamp;



public abstract class CacheObject implements Serializable {
    @QuerySqlField(index = true)
    private boolean isDirty = false;   //denotes whether data in this object is updated but not updated in the DB
    @QuerySqlField(index = true)
    private boolean isNew = false;//Means this data is newly added to the cache so these have to be inserted to the DB (not updated)
    @QuerySqlField(index = true)
    private Timestamp lastChanged;
    @QuerySqlField(index = true)
    private Object primaryKeyObject = null;
    @QuerySqlField(index = true)
    private int retryCount = 0; // Used to persist the in memory cache objects to a  log file after several failed attempts


    public Timestamp getLastChanged() {
        return lastChanged;
    }


    public void setLastChanged(Timestamp lastChanged) {
        this.lastChanged = lastChanged;
    }


    public boolean isDirty() {
        return isDirty;
    }


    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }


    public boolean isNew() {
        return isNew;
    }


    public void setNew(boolean aNew) {
        isNew = aNew;
    }


    public int getRetryCount() {
        return retryCount;
    }


    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }


    public Object getPrimaryKeyObject() {
        return primaryKeyObject;
    }

    public void setPrimaryKeyObject(Object primaryKeyObject) {
        this.primaryKeyObject = primaryKeyObject;
    }


}
