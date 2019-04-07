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
    private Long fca1;
    private Long fca2;
    private Long fca3;
    private Long fExam;
    private Long fTotal;
    private Long fPosition;
    private String fGrade;
    private String fRemark;
    private Long cumAvg;
    private Long position;

}
