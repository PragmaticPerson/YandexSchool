package ru.yandex.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.goods.dao.ShopUnitRepository;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitDB;

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
        ShopUnitDB unitDB = jpa.getOneWithLastPrice(uuid);
        Set<ShopUnitDB> children = jpa.getChildrenByParentId(uuid);

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
}
