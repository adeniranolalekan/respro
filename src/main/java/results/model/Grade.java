package results.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long gradeId;
    private Long rangeLow;
    private  Long rangeHigh;
    private Long gradeName;
    private String Remark;
}
