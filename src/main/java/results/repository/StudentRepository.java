package results.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import results.model.Affective;
import results.model.Student;
import results.model.Subject;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);
    @Query("FROM Student g where g.classArm.armId= :armId")
    Iterable<Student> findAllByClassArm(@Param("armId") Long armId);
}