package results.repository;

import org.springframework.data.repository.CrudRepository;
import results.model.PComment;

public interface PCommentRepository extends CrudRepository<PComment, Long> {

}