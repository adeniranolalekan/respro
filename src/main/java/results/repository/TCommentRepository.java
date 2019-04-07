package results.repository;

import org.springframework.data.repository.CrudRepository;
import results.model.TComment;

public interface TCommentRepository extends CrudRepository<TComment, Long> {

}
