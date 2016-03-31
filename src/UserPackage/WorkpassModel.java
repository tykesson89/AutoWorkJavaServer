package UserPackage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import UserPackage.Company;

/**
 * Created by oladahl on 16-03-28.
 */
public class WorkpassModel implements Serializable{
    private static final long serialVersionUID = 1L;
    private long id;
    private int userID;
    private String title;
    private double salary;
    private Company company;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int breaktime;
    private String note;

    public WorkpassModel(String title, double salary, Company company, Timestamp startDateTime,
                         Timestamp endDateTime, int breaktime, String note) {
        this.title = title;
        this.salary = salary;
        this.company = company;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.breaktime = breaktime;
        this.note = note;
    }
    public WorkpassModel(long id, int userID, String title, double salary, Company company, Timestamp startDateTime,
                         Timestamp endDateTime, int breaktime, String note){
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.salary = salary;
        this.company = company;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.breaktime = breaktime;
        this.note = note;
    }


    public WorkpassModel(){
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany(){
        return company;
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

    public long getUserId() {
        return userID;
    }

    public void setUserId(int userID) {
        this.userID = userID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBreaktime() {
        return breaktime;
    }

    public void setBreaktime(int breaktime) {
        this.breaktime = breaktime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
