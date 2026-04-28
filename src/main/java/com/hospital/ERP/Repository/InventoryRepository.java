package com.hospital.ERP.Repository;

import com.hospital.ERP.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    List<Inventory> findByQuantityLessThan(int threshold);
}
