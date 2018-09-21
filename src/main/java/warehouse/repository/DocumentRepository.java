package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import warehouse.dao.DocumentDAO;
import warehouse.entity.Document;

import java.util.List;
@Repository
public class DocumentRepository implements DocumentInterface {
    @Autowired
    private DocumentDAO documentDAO;
    @Override
    public List<Document> findall() {
        return (List<Document>)documentDAO.findAll();
    }
}
