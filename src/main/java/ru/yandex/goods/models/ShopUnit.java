package ru.yandex.goods.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "shopUnit")
@Entity
public class ShopUnit {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ShopUnit parent;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ShopUnitType type;

    @Column(name = "price")
    private Integer price;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ShopUnit> children;

    public ShopUnit() {
    }

    public ShopUnit(String id, String name, LocalDateTime date, ShopUnit parent, ShopUnitType type, Integer price, Set<ShopUnit> children) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.parent = parent;
        this.type = type;
        this.price = price;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public ShopUnit getParent() {
        return parent;
    }

    public void setParent(ShopUnit parent) {
        this.parent = parent;
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
        if (parent != null ? !parent.equals(shopUnit.parent) : shopUnit.parent != null) return false;
        if (!type.equals(shopUnit.type)) return false;
        if (price != null ? !price.equals(shopUnit.price) : shopUnit.price != null) return false;
        return children != null ? children.equals(shopUnit.children) : shopUnit.children == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopUnit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", parent=" + parent +
                ", type=" + type +
                ", price=" + price +
                ", children=" + children +
                '}';
    }
}
