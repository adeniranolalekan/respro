package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
public class TComment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long tCommentId;
    private Long rangeLow;
    private Long rangeHigh;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "armId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private  ClassArm classArm;
}
