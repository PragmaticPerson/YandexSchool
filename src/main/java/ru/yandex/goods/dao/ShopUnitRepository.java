package ru.yandex.goods.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.goods.models.ShopUnitDB;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShopUnitRepository extends JpaRepository<ShopUnitDB, UUID> {

    @Query("SELECT unit " +
            "FROM ShopUnitDB unit JOIN FETCH unit.prices price " +
            "WHERE unit.id = ?1 AND price.date BETWEEN ?2 AND ?3")
    ShopUnitDB findByIdAndDate(UUID uuid, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("SELECT unit " +
            "FROM ShopUnitDB unit JOIN FETCH unit.prices price " +
            "WHERE unit.parentId = ?1")
    List<ShopUnitDB> findAllChildrenByParentId(UUID id);

    @Query("SELECT DISTINCT unit " +
            "FROM ShopUnitDB unit JOIN FETCH unit.prices price " +
            "WHERE price.date BETWEEN ?1 and ?2")
    List<ShopUnitDB> findAllSales(LocalDateTime dateStart, LocalDateTime dateEnd);
}
