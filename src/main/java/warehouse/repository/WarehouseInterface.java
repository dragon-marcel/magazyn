package warehouse.repository;

import warehouse.entity.Warehouse;

public interface WarehouseInterface {
    Warehouse findOnebyId(Long id);
}
