package UserPackage;

/**
 * Created by Henrik on 2016-03-22.
 */
public class Company implements CompanyInterface {
    private static final long serialVersionUID = 1L;
    private String companyName;
    private double hourlyWage;
    private int userId;
    private int companyId;

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


    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    @Override
    public double getHourlyWage() {
        return hourlyWage;
    }

    @Override
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public int getCompanyId() {
        return companyId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getUserId() {
        return userId;
    }
}
