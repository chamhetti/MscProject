package lk.ac.ucsc.oms.cache.api.beans;


import java.io.Serializable;
import java.util.Date;


public class ActiveNode extends CacheObject implements Serializable {

    private String nodeID = "";
    Date lastUsedTime = null;

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(Date lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    @Override
    public String toString() {
        return "ActiveNode{" +
                "nodeID='" + nodeID + '\'' +
                ", lastUsedTime=" + lastUsedTime +
                '}';
    }
}
