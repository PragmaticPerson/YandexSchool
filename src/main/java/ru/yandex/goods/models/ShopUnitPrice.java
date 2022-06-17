package ru.yandex.goods.models;

import javax.persistence.*;
import java.io.Serializable;
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
    private int price;

    public ShopUnitPrice() {
    }

    public ShopUnitPrice(UUID unitId, LocalDateTime date) {
        this.unitId = unitId;
        this.date = date;
    }

    public ShopUnitPrice(int id, UUID unitId, LocalDateTime date, int price) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopUnitPrice that = (ShopUnitPrice) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (!unitId.equals(that.unitId)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + unitId.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + price;
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


