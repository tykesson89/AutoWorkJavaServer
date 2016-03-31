package UserPackage;

import java.io.Serializable;
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
    private Company company;
    private Date startDateTime;
    private Date endDateTime;
    private int braketime;
    private String note;

    public WorkpassModel(String title, Date startDateTime,
                         Date endDateTime, int braketime, String note) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.braketime = braketime;
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

    public void setStartDateTime(Date startDateTime){
        this.startDateTime = startDateTime;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public int getUserId() {
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

    public int getBraketime() {
        return braketime;
    }

    public void setBraketime(int braketime) {
        this.braketime = braketime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
