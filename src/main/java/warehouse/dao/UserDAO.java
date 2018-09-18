package warehouse.dao;

import org.springframework.data.repository.CrudRepository;
import warehouse.entity.User;

public interface UserDAO extends CrudRepository<User,Long> {

}
