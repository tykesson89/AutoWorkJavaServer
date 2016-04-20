package UserPackage;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by oladahl on 16-03-28.
 */
public class WorkpassModel implements Serializable{
    private static final long serialVersionUID = 1L;
    private long workpassId;
    private int mySQLId;
    private int userId;
    private int mySQLcompanyId;
    private int companyId;
    private String title;
    private GregorianCalendar startDateTime;
    private GregorianCalendar endDateTime;
    private double breaktime;
    private double workingHours;
    private double salary;
    private String note;
    private String ACTION_TAG;


    @Override
    public String toString() {
        return "WorkpassModel{" +
                "workpassId=" + workpassId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", startDateTime=" + startDateTime.toString() +
                ", endDateTime=" + endDateTime.toString() +
                ", breaktime=" + breaktime +
                ", workingHours=" + workingHours +
                ", salary=" + salary +
                ", note='" + note + '\'' +
                '}';
    }

    public WorkpassModel(String title, double salary, Company company, GregorianCalendar startDateTime,
                         GregorianCalendar endDateTime, double breaktime, double workingHours, String note) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.breaktime = breaktime;
        this.workingHours = workingHours;
        this.salary = salary;
        this.note = note;
    }

    public WorkpassModel(){
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyId(){
        return companyId;
    }

    public void setMySQLcompanyId(int mySQLcompanyId) {
        this.mySQLcompanyId = mySQLcompanyId;
    }

    public int getMySQLcompanyId(){
        return mySQLcompanyId;
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

    public long getWorkpassId() {
        return workpassId;
    }

    public void setWorkpassId(long workpassId) {
        this.workpassId = workpassId;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public int getMySQLId() {
        return mySQLId;
    }

    public void setMySQLId(int mySQLId) {
        this.mySQLId = mySQLId;
    }

    public String getACTION_TAG() {
        return ACTION_TAG;
    }

    public void setACTION_TAG(String ACTION_TAG) {
        this.ACTION_TAG = ACTION_TAG;
    }
}
