package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
public class Affective {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long aid;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId",nullable = false)
    private Student student;
   private Long fPunctuality;
   private Long fNeatness;
   private Long fPoliteness;
   private Long fHonesty;
   private Long fCooperation;
   private Long fLeadership;
   private Long fHelping;
   private Long fEmotion;
    private Long fHealth;
    private Long fAttitude;
    private Long fAttentiveness;
    private Long fPerseverance;
    private Long fSpeaking;



}
