package results.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
public class ClassArm {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long armId;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "classname",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ResultClass resultClass;
    private String armname;
}
