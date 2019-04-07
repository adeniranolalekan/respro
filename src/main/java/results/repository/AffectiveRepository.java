package results.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import results.model.Affective;

import java.io.Serializable;

public interface AffectiveRepository extends CrudRepository<Affective, Long> {


}
