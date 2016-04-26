package UserPackage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by oladahl on 16-03-28.
 */
public class Workpass implements Serializable{
    private static final long serialVersionUID = 1L;
    private long workpassID;
    private int serverID;
    private int userId;
    private String title;
    private long companyId;
    private int companyServerID;
    private GregorianCalendar startDateTime;
    private GregorianCalendar endDateTime;
    private double breaktime;
    private double salary;
    private String note;
    private double workingHours;
    private int isSynced;
    private String actionTag;

    @Override
    public String toString() {
        return "Workpass{" +
                "workpassID=" + workpassID +
                ", serverID=" + serverID +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", companyId=" + companyId +
                ", companyServerID=" + companyServerID +
                ", startDateTime=" + startDateTime.get(Calendar.DATE) +
                ", endDateTime=" + endDateTime.get(Calendar.DATE) +
                ", breaktime=" + breaktime +
                ", salary=" + salary +
                ", note='" + note + '\'' +
                ", workingHours=" + workingHours +
                ", isSynced=" + isSynced +
                ", actionTag='" + actionTag + '\'' +
                '}';
    }

    public Workpass(String title, double salary, long companyId, GregorianCalendar startDateTime,
                    GregorianCalendar endDateTime, double breaktime, double workingHours, String note) {
        this.title = title;
        this.companyId = companyId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.breaktime = breaktime;
        this.workingHours = workingHours;
        this.salary = salary;
        this.note = note;
    }

    public Workpass(){
    }

    public void setCompanyID(long companyId) {
        this.companyId = companyId;
    }

    public long getCompanyID(){
        return companyId;
    }

    public void setStartDateTime(GregorianCalendar startDateTime){
        this.startDateTime = startDateTime;
    }

    public GregorianCalendar getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(GregorianCalendar endDateTime) {
        this.endDateTime = endDateTime;
    }

    public GregorianCalendar getEndDateTime() {
        return endDateTime;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }

    public double getSalary(){
        return salary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBreaktime() {
        return breaktime;
    }

    public void setBreaktime(double breaktime) {
        this.breaktime = breaktime;
    }

    public long getWorkpassID() {
        return workpassID;
    }

    public void setWorkpassID(long workpassID) {
        this.workpassID = workpassID;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }


    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public String getActionTag() {
        return actionTag;
    }

    public void setActionTag(String actionTag) {
        this.actionTag = actionTag;
    }

    public int getCompanyServerID() {
        return companyServerID;
    }

    public void setCompanyServerID(int companyServerID) {
        this.companyServerID = companyServerID;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }
}
