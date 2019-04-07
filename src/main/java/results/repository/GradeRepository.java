package results.repository;

import org.springframework.data.repository.CrudRepository;
import results.model.Grade;

public interface GradeRepository extends CrudRepository<Grade, Long> {

}
