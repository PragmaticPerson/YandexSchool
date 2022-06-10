package ru.yandex.goods.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.goods.dao.ShopUnitJPA;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitImport;
import ru.yandex.goods.models.ShopUnitType;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ShopOrientedRESTController {
    private ShopUnitJPA shopUnitJPA;

    @Autowired
    public ShopOrientedRESTController(ShopUnitJPA shopUnitJPA) {
        this.shopUnitJPA = shopUnitJPA;
    }

    @GetMapping("/get")
    public ShopUnit getShopUnitMapping() {
        LocalDateTime date = LocalDateTime.now();
        return new ShopUnit("uuid", "name", date, null, ShopUnitType.CATEGORY, 0, null);
    }

    @PostMapping("/imports")
    @ResponseStatus(code = HttpStatus.OK)
    public void importShopUnits(@RequestBody List<ShopUnitImport> shopUnitImports) {
        shopUnitImports.parallelStream().forEach(imp -> {
            imp.setUpdateDateToItems();
            imp.getItems().parallelStream().forEach(shopUnitJPA::save);
        });
    }
}
