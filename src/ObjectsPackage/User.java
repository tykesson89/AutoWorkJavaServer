package ObjectsPackage;

/**
 * Created by Henrik on 2016-03-17.
 */
public class User implements UserInterface {
    private static final long serialVersionUID = 1L;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int userId;


    public User(String firstname, String lastname, String email, String password, int userId){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.userId = userId;

    }

    public User(String email, String password){
        this.firstname = null;
        this.lastname = null;
        this.email = email;
        this.password = password;
        this.userId = -1;

    }
    public User(String firstname, String lastname, String email, int userId){
        this.firstname = null;
        this.lastname = null;
        this.email = email;
        this.password = null;
        this.userId = userId;
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
        arr[3] = password;
        arr[4] = userId;
        return arr;
    }
    public String toString(){
        String str = firstname + ", " + lastname + ", " + email + ", " + password + ", " + userId;
        return str;
    }
}
