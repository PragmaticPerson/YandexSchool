package ru.yandex.goods.models;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "shop_unit")
public class ShopUnitDB {

    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    @Nullable
    private UUID parentId;

    @Enumerated(EnumType.STRING)
    private ShopUnitType type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private List<ShopUnitPrice> prices;

    @Transient
    private Set<ShopUnitDB> children;

    public ShopUnitDB() {
        prices = new ArrayList<>();
        prices.add(new ShopUnitPrice());
    }

    public ShopUnitDB(UUID id, String name, UUID parentId, ShopUnitType type, List<ShopUnitPrice> prices, Set<ShopUnitDB> children) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
        this.prices = prices;
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

    public List<ShopUnitPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<ShopUnitPrice> prices) {
        this.prices = prices;
    }

    public Set<ShopUnitDB> getChildren() {
        return children;
    }

    public void setChildren(Set<ShopUnitDB> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopUnitDB shopUnitDB = (ShopUnitDB) o;

        if (!id.equals(shopUnitDB.id)) return false;
        if (!name.equals(shopUnitDB.name)) return false;
        if (!Objects.equals(parentId, shopUnitDB.parentId)) return false;
        if (type != shopUnitDB.type) return false;
        if (!Objects.equals(prices, shopUnitDB.prices)) return false;
        return Objects.equals(children, shopUnitDB.children);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopUnitDB{" +
                "id='" + id.toString() + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", type=" + type +
                ", prices=" + prices +
                ", children=" + children +
                '}';
    }

    public ShopUnit convertToShopUnit() {
        if (!prices.isEmpty()) {
            ShopUnitPrice price = prices.stream()
                    .sorted(Comparator.comparing(ShopUnitPrice::getDate).reversed())
                    .limit(1)
                    .collect(Collectors.toList()).get(0);
            ShopUnit unit = new ShopUnit(id, name, price.getDate(), parentId, type, price.getPrice());
            if (children != null && !children.isEmpty()) {
                unit.setChildren(
                        children.stream().map(ShopUnitDB::convertToShopUnit).collect(Collectors.toSet()));
            }
            return unit;
        }
        return new ShopUnit();
    }

    public List<ShopUnit> convertToShopUnitStatistic() {
        return prices.stream()
                .map(price -> new ShopUnit(id, name, price.getDate(), parentId, type, price.getPrice()))
                .collect(Collectors.toList());
    }
}
