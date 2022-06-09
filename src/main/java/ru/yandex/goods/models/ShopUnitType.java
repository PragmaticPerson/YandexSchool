package ru.yandex.goods.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

public enum ShopUnitType {
    OFFER, CATEGORY;
}
