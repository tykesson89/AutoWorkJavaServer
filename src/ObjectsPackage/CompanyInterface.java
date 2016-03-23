package ObjectsPackage;

import java.io.Serializable;

/**
 * Created by Henrik on 2016-03-22.
 */
public interface CompanyInterface extends Serializable {

    public void setCompanyName(String companyName);
    public String getCompanyName();
    public void setHourlyWage(double hourlyWage);
    public double getHourlyWage();
    public void setCompanyId(int companyId);
    public int getCompanyId();
    public void setUserId(int userId);
    public int getUserId();

}
