package ru.yandex.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.goods.dao.ShopUnitRepository;
import ru.yandex.goods.exceptions.NotFoundException;
import ru.yandex.goods.exceptions.ValidationFailedException;
import ru.yandex.goods.models.StatisticDates;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitDB;

import java.util.*;

@Service
public class ShopUnitService {

    private final ShopUnitRepository jpa;

    @Autowired
    public ShopUnitService(ShopUnitRepository jpa) {
        this.jpa = jpa;
    }

    public ShopUnit getShopUnitWithChildren(UUID uuid) {
        return combineChildren(uuid).convertToShopUnit();
    }

    private ShopUnitDB combineChildren(UUID uuid) {
        ShopUnitDB unitDB = getShopUnitWithoutChildren(uuid);
        Set<ShopUnitDB> children = new HashSet<>(jpa.findAllChildrenByParentId(uuid));

        if (!children.isEmpty()) {
            children.forEach(u ->
                    u.setChildren(combineChildren(u.getId()).getChildren())
            );
            unitDB.setChildren(children);
        }
        return unitDB;
    }

    public List<ShopUnit> getShopUnitStatistic(UUID uuid, StatisticDates dates) {
        ShopUnitDB unitDB;

        if (dates.isNull()) {
            unitDB = getShopUnitWithoutChildren(uuid);
        } else if (dates.isOneNull()) {
            throw new ValidationFailedException();
        } else if (dates.isCorrect()) {
            unitDB = Optional
                    .ofNullable(jpa.findByIdAndDate(uuid, dates.getStart(), dates.getEnd()))
                    .orElseThrow(NotFoundException::new);

        } else {
            throw new ValidationFailedException();
        }

        return unitDB.convertToShopUnitStatistic();
    }

    private ShopUnitDB getShopUnitWithoutChildren(UUID uuid) {
        return jpa
                .findById(uuid).orElseThrow(NotFoundException::new);
    }

    public ShopUnitDB save(ShopUnit unit) {
        return jpa.save(unit.convertToShopUnitDB());
    }
}