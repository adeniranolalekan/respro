package results.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Setting {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer settingId;
    private Integer Term;
    @Type(type="org.hibernate.type.NumericBooleanType")
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
