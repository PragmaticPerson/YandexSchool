package ru.yandex.goods.dao;

import ru.yandex.goods.models.ShopUnitDB;

import java.util.Set;
import java.util.UUID;

public interface ShopUnitRepositoryCustom {

    ShopUnitDB getOneWithLastPrice(UUID uuid);
    Set<ShopUnitDB> getChildrenByParentId(UUID uuid);
}
