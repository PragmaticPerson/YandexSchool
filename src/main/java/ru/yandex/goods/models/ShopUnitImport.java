package ru.yandex.goods.models;

import java.time.LocalDateTime;
import java.util.List;

public class ShopUnitImport {
    private List<ShopUnit> items;
    private LocalDateTime updateDate;

    public ShopUnitImport() {
    }

    public ShopUnitImport(List<ShopUnit> items, LocalDateTime updateDate) {
        this.items = items;
        this.updateDate = updateDate;
    }

    public void setUpdateDateToItems() {
        items.parallelStream().forEach(shopUnit -> shopUnit.setDate(updateDate));
    }

    public List<ShopUnit> getItems() {
        return items;
    }

    public void setItems(List<ShopUnit> items) {
        this.items = items;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopUnitImport that = (ShopUnitImport) o;

        if (!items.equals(that.items)) return false;
        return updateDate.equals(that.updateDate);
    }

    @Override
    public int hashCode() {
        int result = items.hashCode();
        result = 31 * result + updateDate.hashCode();
        return result;
    }
}
