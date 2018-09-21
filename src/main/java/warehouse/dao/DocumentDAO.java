package warehouse.dao;

import org.springframework.data.repository.CrudRepository;
import warehouse.entity.Document;

public interface DocumentDAO extends CrudRepository<Document,Long> {
}
