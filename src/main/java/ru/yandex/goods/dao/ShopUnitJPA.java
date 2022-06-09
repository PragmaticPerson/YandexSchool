package ru.yandex.goods.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.goods.models.ShopUnit;

@Repository
public interface ShopUnitJPA extends JpaRepository<ShopUnit, String> {
}
