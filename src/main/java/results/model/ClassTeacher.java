package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
public class ClassTeacher {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long classTeacherId;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userId",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassArm classArm;

}
