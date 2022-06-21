package ru.yandex.goods.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ShopUnitStatistic {

    private UUID id;
    private String name;
    private LocalDateTime date;
    private UUID parentId;
    private ShopUnitType type;
    private Integer price;

    public ShopUnitStatistic(UUID id, String name, LocalDateTime date, UUID parentId, ShopUnitType type, Integer price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.parentId = parentId;
        this.type = type;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopUnitStatistic that = (ShopUnitStatistic) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!date.equals(that.date)) return false;
        if (!Objects.equals(parentId, that.parentId)) return false;
        if (type != that.type) return false;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopUnitStatistic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", parentId=" + parentId +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
