package UserPackage;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Henrik on 2016-04-05.
 */
public class BufferModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int workpassId;
    private int userId;
    private String title;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private double breaktime;
    private double workingHours;
    private double salary;
    private String note;
    private String companyName;
    private double hourlyWage;
    private int companyId;
    private String tag;

    public BufferModel(){

    }

    public void setStartDateTime(Timestamp startDateTime){
        this.startDateTime = startDateTime;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Timestamp getEndDateTime() {
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

    public int getWorkpassId() {
        return workpassId;
    }

    public void setWorkpassId(int id) {
        this.workpassId = workpassId;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
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

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public String getTag(){
        return this.tag;
    }

}
