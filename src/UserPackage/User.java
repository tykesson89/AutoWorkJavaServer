package UserPackage;



/**
 * Created by Henrik on 2016-03-17.
 */
public class User implements UserInterface {
    private static final long serialVersionUID = 1L;
    private String firstname;
    private String lastname;
    private String email;
    private String newPassword;
    private String oldPassword;
    private int userId;


    public User(String firstname, String lastname, String email, String oldPassword, int userId, String newPassword){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.oldPassword = oldPassword;
        this.userId = userId;
        this.newPassword = newPassword;

    }


    public User(String firstname, String lastname, String email, String oldPassword, int userId){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.oldPassword = oldPassword;
        this.userId = userId;
        this.newPassword = null;

    }

    public User(String email, String oldPassword){
        this.firstname = null;
        this.lastname = null;
        this.email = email;
        this.oldPassword = oldPassword;
        this.userId = -1;
        this.newPassword = null;
    }
    public User(String firstname, String lastname, String email, int userId){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.oldPassword = null;
        this.userId = userId;
        this.newPassword = null;
    }
    public User(String firstname, String lastname, String email, String oldPassword){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.oldPassword = oldPassword;
        this.userId = -1;
        this.newPassword = null;
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
    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public void setNewPassword(String newPassword) {
this.newPassword = newPassword;
    }

    @Override
    public String getOldPassword() {
        return oldPassword;
    }

    @Override
    public void setOldPassword(String password) {
        this.oldPassword = oldPassword;
    }


    public int getUserid() {
        return userId;
    }

    public void setUserid(int userid) {
        this.userId = userid;
    }



    @Override
    public Object[] toObjectArray() {
        Object[] arr = new Object[8];
        arr[0] = firstname;
        arr[1] = lastname;
        arr[2] = email;
        arr[3] = oldPassword;
        arr[4] = userId;
        return arr;
    }
    public String toString(){
        String str = firstname + ", " + lastname + ", " + email + ", " + oldPassword + ", " + userId;
        return str;
    }
}
