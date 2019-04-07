package results.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import results.model.Scoresheet;
import results.model.Student;
import results.model.Subject;

public interface ScoresheetRepository extends CrudRepository<Scoresheet, Long> {
    Page<Scoresheet> findAll(Pageable pageable);
    @Query("FROM Scoresheet g where g.subject.subjectId= :subjectId")
    Iterable<Scoresheet> findAllBySubject(@Param("subjectId") Long subjectId);
}
