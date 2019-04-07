package results.model;

import lombok.Data;

import javax.persistence.Entity;
@Data
@Entity
public class Setting {
    private Long Term;
    private boolean resultApproved;
    private boolean currentSession;
    private boolean serverLock;
    private String session;
    private String firstResumptionDate;
    private String secongResumptionDate;
    private String thirdResumptionDate;
    private String firstOpened;
    private String secondOpened;
    private String thirdOpened;
    private int firstObtainableCA;
    private int getSecondObtainableCA;
    private int getThirdObtainableCA;
    private String username;
    private String password;
    private String adminRole;
    private String adminProfilename;


}
