package ru.yandex.goods.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.goods.models.ShopUnitType;

public interface ShopUnitTypeJPA extends JpaRepository<ShopUnitType, Integer> {
}
