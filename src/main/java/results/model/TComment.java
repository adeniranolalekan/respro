package results.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class TComment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long tCommentId;
    private Long rangeLow;
    private Long rangeHigh;
    private String comment;
    private  ClassArm classArm;
}
