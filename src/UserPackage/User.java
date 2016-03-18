package UserPackage;

import UserPackage.UserInterface;

/**
 * Created by Henrik on 2016-03-17.
 */
public class User implements UserInterface {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String companyName;
    private double hourlyWage;
    private int userId;
    private int companyId;

    public User(String firstname, String lastname, String email, String password, String companyName, double hourlyWage, int userId, int companyId){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.hourlyWage = hourlyWage;
        this.userId = userId;
        this.companyId = companyId;
    }

    public User(String firstname, String lastname, String email, String password, String companyName, double hourlyWage){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.hourlyWage = hourlyWage;
        this.userId = 0;
        this.companyId = 0;

    }

    public User(String email, String password, int userId){
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.firstname = null;
        this.lastname = null;
        this.companyName = null;
        this.hourlyWage = 0;
        this.companyId = 0;
    }


    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public double getHourlyWage() {
        return hourlyWage;
    }

    @Override
    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public int getUserid() {
        return userId;
    }

    public void setUserid(int userid) {
        this.userId = userid;
    }

    public int getCompanyid() {
        return companyId;
    }

    public void setCompanyid(int companyid) {
        this.companyId = companyid;
    }

    @Override
    public Object[] toObjectArray() {
        Object[] arr = new Object[8];
        arr[0] = firstname;
        arr[1] = lastname;
        arr[2] = email;
        arr[3] = password;
        arr[4] = companyName;
        arr[5] = hourlyWage;
        arr[6] = userId;
        arr[7] = companyId;
        return arr;
    }
    public String toString(){
        String str = firstname + ", " + lastname + ", " + email + ", " + password + ", " + companyName + ", " + hourlyWage + ", " + userId + ", " + companyId;
        return str;
    }
}
