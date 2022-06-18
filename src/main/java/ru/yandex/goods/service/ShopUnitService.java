package ru.yandex.goods.service;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.yandex.goods.dao.ShopUnitRepository;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitDB;
import ru.yandex.goods.models.ShopUnitPrice;

import javax.persistence.criteria.Join;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class ShopUnitService {

    private ShopUnitRepository jpa;

    @Autowired
    public ShopUnitService(ShopUnitRepository jpa) {
        this.jpa = jpa;
    }

    public ShopUnitDB getShopUnitWithChildren(UUID uuid) {
        ShopUnitDB unitDB = jpa.findAll(where(idLike(uuid).and(joinPrices()))).get(0);
        Set<ShopUnitDB> children = new HashSet<>(jpa.findAll(where(parentIdLike(uuid).and(joinPrices()))));

        if (!children.isEmpty()) {
            children.forEach(u ->
                    u.setChildren(getShopUnitWithChildren(u.getId()).getChildren())
            );
            unitDB.setChildren(children);
        }
        return unitDB;
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
