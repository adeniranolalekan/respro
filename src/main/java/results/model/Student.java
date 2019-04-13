package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long studentId;
    private double fTotal;
    private int fPosition;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "armId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassArm classArm;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    private Psychomotor psychomotor;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    private Affective affective;
    private String sex;
    private String fTeacherComment;
    private  String fPrincipalComment;
    private double fPercentage;
    private  int fAttendance;
    private double sTotal;
    private int sPosition;
    private String sTeacherComment;
    private  String sPrincipalComment;
    private double sPercentage;
    private  int sAttendance;
    private double tTotal;
    private int tPosition;
    private String tTeacherComment;
    private  String tPrincipalComment;
    private double tPercentage;
    private  int tAttendance;
    private int noOfSubjectOffered;
}
