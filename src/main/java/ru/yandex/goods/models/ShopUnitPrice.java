package ru.yandex.goods.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shop_unit_price")
public class ShopUnitPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "unit_id")
    private UUID unitId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "price")
    private Integer price;

    public ShopUnitPrice() {
    }

    public ShopUnitPrice(UUID unitId, LocalDateTime date) {
        this.unitId = unitId;
        this.date = date;
    }

    public ShopUnitPrice(int id, UUID unitId, LocalDateTime date, Integer price) {
        this.id = id;
        this.unitId = unitId;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

        ShopUnitPrice that = (ShopUnitPrice) o;

        if (id != that.id) return false;
        if (!unitId.equals(that.unitId)) return false;
        if (!date.equals(that.date)) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + unitId.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopUnitPrice{" +
                "id=" + id +
                ", unitId='" + unitId.toString() + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}


