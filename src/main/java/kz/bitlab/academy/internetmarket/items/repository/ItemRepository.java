package kz.bitlab.academy.internetmarket.items.repository;

import kz.bitlab.academy.internetmarket.items.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
