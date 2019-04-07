package results.repository;

import org.springframework.data.repository.CrudRepository;
import results.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}