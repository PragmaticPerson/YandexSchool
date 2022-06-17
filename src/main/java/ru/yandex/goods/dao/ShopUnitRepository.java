package ru.yandex.goods.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.goods.models.ShopUnitDB;

import java.util.UUID;

@Repository
public interface ShopUnitRepository extends JpaRepository<ShopUnitDB, UUID>, ShopUnitRepositoryCustom {
}