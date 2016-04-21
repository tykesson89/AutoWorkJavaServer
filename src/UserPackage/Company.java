package UserPackage;

import java.io.Serializable;

/**
 * Created by Henrik on 2016-03-22.
 */
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private long companyId;
    private int serverID;
    private int userId;
    private String companyName;
    private double hourlyWage;
    private int isSynced;
    private String actionTag;

    public Company(long companyId, int serverID, int userId, String companyName, double hourlyWage, int isSynced, String actionTag) {
        this.companyId = companyId;
        this.serverID = serverID;
        this.userId = userId;
        this.companyName = companyName;
        this.hourlyWage = hourlyWage;
        this.isSynced = isSynced;
        this.actionTag = actionTag;
    }

    public Company(long companyId, int serverID, int userId, String companyName, double hourlyWage) {
        this.companyId = companyId;
        this.serverID = serverID;
        this.userId = userId;
        this.companyName = companyName;
        this.hourlyWage = hourlyWage;
    }

    public Company(String companyName, double hourlyWage){
        this.companyName = companyName;
        this.hourlyWage = hourlyWage;
        this.userId = -1;
        this.companyId = -1;
    }
    public Company(String companyName, double hourlyWage, int userId, int companyId){
        this.companyName = companyName;
        this.hourlyWage = hourlyWage;
        this.userId = userId;
        this.companyId = companyId;
    }

    public Company() {

    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getCompanyName() {
        return companyName;
    }


    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }


    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return companyName;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }

    public String getActionTag() {
        return actionTag;
    }

    public void setActionTag(String actionTag) {
        this.actionTag = actionTag;
    }
}
