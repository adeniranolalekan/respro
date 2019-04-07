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

}
