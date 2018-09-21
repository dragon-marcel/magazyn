package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import warehouse.dao.WarehouseDAO;
import warehouse.entity.Warehouse;

@Repository
public class WarehouseRepository implements WarehouseInterface{
@Autowired
    private WarehouseDAO warehouseDAO;
    @Override
    public Warehouse findOnebyId(Long id) {
        return  warehouseDAO.findById(id).orElse(null);
    }
}
