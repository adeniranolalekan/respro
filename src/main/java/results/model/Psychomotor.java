package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
@Data
@Entity
public class Psychomotor {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long pid;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId",nullable = false)
    private Student student;
    private Long fHandwriting;
    private Long fVFluency;
    private Long fGames;
    private Long fSports;
    private Long fHandlingTools;
    private Long fDrawPaint;
    private Long fSocialSkills;
    private Long sHandwriting;
    private Long sVFluency;
    private Long sGames;
    private Long sSports;
    private Long sHandlingTools;
    private Long sDrawPaint;
    private Long sSocialSkills;
    private Long tHandwriting;
    private Long tVFluency;
    private Long tGames;
    private Long tSports;
    private Long tHandlingTools;
    private Long tDrawPaint;
    private Long tSocialSkills;

}
