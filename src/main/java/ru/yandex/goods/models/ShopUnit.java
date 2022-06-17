package ru.yandex.goods.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ShopUnit {
    private UUID id;
    private String name;
    private LocalDateTime date;
    private UUID parentId;
    private ShopUnitType type;
    private Integer price;
    private Set<ShopUnit> children;

    public ShopUnit() {
    }

    public ShopUnit(UUID id, String name, LocalDateTime date, UUID parentId, ShopUnitType type, Integer price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.parentId = parentId;
        this.type = type;
        this.price = price;
    }

    public ShopUnit(UUID id, String name, LocalDateTime date, UUID parentId, ShopUnitType type, Integer price, Set<ShopUnit> children) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.parentId = parentId;
        this.type = type;
        this.price = price;
        this.children = children;
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

    public Set<ShopUnit> getChildren() {
        return children;
    }

    public void setChildren(Set<ShopUnit> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopUnit shopUnit = (ShopUnit) o;

        if (!id.equals(shopUnit.id)) return false;
        if (!name.equals(shopUnit.name)) return false;
        if (!date.equals(shopUnit.date)) return false;
        if (parentId != null ? !parentId.equals(shopUnit.parentId) : shopUnit.parentId != null) return false;
        if (type != shopUnit.type) return false;
        if (price != null ? !price.equals(shopUnit.price) : shopUnit.price != null) return false;
        return children != null ? children.equals(shopUnit.children) : shopUnit.children == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", parentId=" + parentId +
                ", type=" + type +
                ", price=" + price +
                ", children=" + children +
                '}';
    }

    public ShopUnitDB convertToShopUnitDB() {
        return new ShopUnitDB(id, name, parentId, type, List.of(new ShopUnitPrice(0, id, date, price)), null);
    }
}
