package results.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import results.model.Student;
import results.model.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
    Page<Subject> findAll(Pageable pageable);
    @Query("FROM Subject g where g.classArm.armId= :armId")
   Iterable<Subject> findAllByClassArm(@Param("armId") Long armId);
}
