package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
@Data
@Entity
public class Scoresheet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long sheetId;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "subjectId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "studentId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;
    private double fca1;
    private double fca2;
    private double fca3;
    private double fExam;
    private double fTotal;
    private int fPosition;
    private String fGrade;
    private String fRemark;
    private double cumAvg;
    private int position;
    private double sca1;
    private double sca2;
    private double sca3;
    private double sExam;
    private double sTotal;
    private int sPosition;
    private String sGrade;
    private String sRemark;
    private double tca1;
    private double tca2;
    private double tca3;
    private double tExam;
    private double tTotal;
    private String tGrade;
    private String tRemark;

}
