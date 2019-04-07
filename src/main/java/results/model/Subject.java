package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int subjectId;
    private String subjectName;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "armId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassArm classArm;
    private double fClassAvg;
}
