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
    private Long sPunctuality;
    private Long sNeatness;
    private Long sPoliteness;
    private Long sHonesty;
    private Long sCooperation;
    private Long sLeadership;
    private Long sHelping;
    private Long sEmotion;
    private Long sHealth;
    private Long sAttitude;
    private Long sAttentiveness;
    private Long sPerseverance;
    private Long sSpeaking;
    private Long tPunctuality;
    private Long tNeatness;
    private Long tPoliteness;
    private Long tHonesty;
    private Long tCooperation;
    private Long tLeadership;
    private Long tHelping;
    private Long tEmotion;
    private Long tHealth;
    private Long tAttitude;
    private Long tAttentiveness;
    private Long tPerseverance;
    private Long tSpeaking;



}
