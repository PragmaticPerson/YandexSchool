package ru.yandex.goods.service;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.yandex.goods.dao.ShopUnitRepository;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitDB;
import ru.yandex.goods.models.ShopUnitPrice;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ShopUnitService {

    private ShopUnitRepository jpa;

    @Autowired
    public ShopUnitService(ShopUnitRepository jpa) {
        this.jpa = jpa;
    }

    public ShopUnit getShopUnitWithChildren(UUID uuid) {
        return combineChildren(uuid).convertToShopUnit();
    }

    private ShopUnitDB combineChildren(UUID uuid) {
        ShopUnitDB unitDB = getShopUnitWithoutChildren(uuid);
        Set<ShopUnitDB> children = new HashSet<>(jpa.findAll(where(parentIdLike(uuid).and(joinPrices()))));

        if (!children.isEmpty()) {
            children.forEach(u ->
                    u.setChildren(combineChildren(u.getId()).getChildren())
            );
            unitDB.setChildren(children);
        }
        return unitDB;
    }

    public List<ShopUnit> getShopUnitStatistic(UUID uuid, LocalDateTime start, LocalDateTime end) {
        ShopUnitDB unitDB;
        if (start == null || end == null) {
            unitDB = getShopUnitWithoutChildren(uuid);
        } else {
            unitDB = jpa.findByIdAndDate(uuid, start, end);
        }
        return unitDB.convertToShopUnitStatistic();
    }

    private ShopUnitDB getShopUnitWithoutChildren(UUID uuid) {
        return jpa.findOne(where(idLike(uuid).and(joinPrices()))).get();
    }

    public ShopUnitDB save(ShopUnit unit) {
        return jpa.save(unit.convertToShopUnitDB());
    }

    private Specification<ShopUnitDB> idLike(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    private Specification<ShopUnitDB> parentIdLike(UUID id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("parentId"), id);
    }

    private Specification<ShopUnitDB> joinPrices() {
        return (root, query, criteriaBuilder) -> {
            Join<ShopUnitDB, ShopUnitPrice> join = root.join("prices");
            return criteriaBuilder.equal(join.get("unitId"), root.get("id"));
        };
    }
}
